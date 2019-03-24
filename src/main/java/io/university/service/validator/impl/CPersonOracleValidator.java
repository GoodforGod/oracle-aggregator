package io.university.service.validator.impl;

import io.university.model.dao.common.*;
import io.university.storage.impl.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ! NO DESCRIPTION !
 *
 * @author GoodforGod
 * @since 13.03.2019
 */
@Service
public class CPersonOracleValidator extends BasicCPersonValidator {

    @Autowired private CDepartmentStorage departmentStorage;
    @Autowired private CSpecialityStorage specialityStorage;
    @Autowired private CScheduleStorage scheduleStorage;
    @Autowired private CSubjectStorage subjectStorage;
    @Autowired private CPersonStorage peopleStorage;
    @Autowired private CStudyStorage studyStorage;
    @Autowired private CGradeStorage gradeStorage;

    @Override
    public List<CPerson> validate(List<CPerson> people) {
        if (CollectionUtils.isEmpty(people))
            return Collections.emptyList();

        final Map<Integer, CDepartment> departmentMap = new HashMap<>();
        final Map<Integer, CSpeciality> specialityMap = new HashMap<>();
        final Map<Integer, CSubject> subjectMap = new HashMap<>();
        final Map<Integer, CStudy> studyMap = new HashMap<>();

        final List<CPerson> validPeople = new ArrayList<>(people.size());

        for (CPerson p : people) {
            final CPerson validPerson = peopleStorage.findByFullNameAndBirth(
                    p.getName(),
                    p.getMiddleName(),
                    p.getSurname(),
                    p.getBirthPlace(),
                    p.getBirthTimestamp()).orElse(p);

            fillPersonStudy(p, departmentMap, specialityMap, studyMap, studyStorage, departmentStorage, specialityStorage);

            CWorkHistory history = p.getWorkHistory();
            if (history != null) {
                if (history.getDepartment() != null) {
                    CDepartment department = departmentMap.computeIfAbsent(history.getDepartment().hashCode(),
                            (k) -> departmentStorage.find(history.getDepartment().getId()).orElse(history.getDepartment()));
                    history.setDepartment(department);
                }

                validPerson.setWorkHistory(history);
            }

            if (!CollectionUtils.isEmpty(p.getSchedules())) {
                for (CSchedule schedule : p.getSchedules()) {
                    if (schedule.getSubject() == null)
                        continue;

                    CSubject subject = subjectMap.computeIfAbsent(schedule.getSubject().hashCode(),
                            (k) -> subjectStorage.find(schedule.getSubject().getCode()).orElse(schedule.getSubject()));
                    if (subject != schedule.getSubject()) {
                        final List<CGrade> grades = schedule.getSubject().getGrades().stream()
                                .filter(g -> !gradeStorage.exist(g.getId()))
                                .collect(Collectors.toList());
                        subject.getGrades().addAll(grades);
                    }

                    if (subject.getSpeciality() != null) {
                        CSpeciality speciality = getSpeciality(subject.getSpeciality(), specialityMap, specialityStorage);
                        fillSpecialityStudy(speciality, subject.getSpeciality().getStudy(), studyMap, studyStorage);
                        subject.setSpeciality(speciality);
                    }

//                    if(subject.getCode() != null && subjectStorage.exist(subject.getCode())) {
//                        subjectStorage.save(subject);
//                    }

                    schedule.setSubject(subject);
                }
                p.getSchedules().forEach(validPerson::addSchedule);
            }

            validPeople.add(validPerson);
        }

        return validPeople;
    }
}
