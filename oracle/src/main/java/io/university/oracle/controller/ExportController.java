package io.university.oracle.controller;

import io.swagger.annotations.ApiOperation;
import io.university.api.error.NotUpdatedException;
import io.university.oracle.exporter.*;
import io.university.oracle.model.dao.*;
import io.university.oracle.storage.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ! NO DESCRIPTION !
 *
 * @author GoodforGod
 * @since 06.04.2019
 */
@ApiOperation(value = "Updates model and exports if export is enabled")
@RestController
@RequestMapping("/postgres/export")
public class ExportController {

    private static final Logger logger = LoggerFactory.getLogger(ExportController.class);

    @Autowired private OWorkExporter workExporter;
    @Autowired private OGradeExporter gradeExporter;
    @Autowired private OStudyExporter studyExporter;
    @Autowired private OPersonExporter personExporter;
    @Autowired private OSubjectExporter subjectExporter;
    @Autowired private OScheduleExporter scheduleExporter;
    @Autowired private OSpecialityExporter specialityExporter;
    @Autowired private ODepartmentExporter departmentExporter;

    @Autowired private ODepartmentStorage departmentStorage;
    @Autowired private OSpecialityStorage specialityStorage;
    @Autowired private OScheduleStorage scheduleStorage;
    @Autowired private OSubjectStorage subjectStorage;
    @Autowired private OPersonStorage peopleStorage;
    @Autowired private OStudyStorage studyStorage;
    @Autowired private OGradeStorage gradeStorage;
    @Autowired private OWorkStorage workStorage;

    @PostMapping("/department")
    public ODepartment postOrUpdateSpeciality(@RequestBody ODepartment department) {
        ODepartment modelToUpdate = departmentStorage.find(department.getId()).orElse(department);
        modelToUpdate.update(department);
        if (!departmentStorage.save(modelToUpdate).isPresent()) {
            logger.warn("Model not updated!");
            throw new NotUpdatedException();
        }

        departmentExporter.exportIfPossible(modelToUpdate);
        return modelToUpdate;
    }

    @PostMapping("/schedule")
    public OSchedule postOrUpdateSchedule(@RequestBody OSchedule schedule) {
        OSchedule modelToUpdate = scheduleStorage.find(schedule.getId()).orElse(schedule);
        modelToUpdate.update(schedule);
        if (!scheduleStorage.save(modelToUpdate).isPresent()) {
            logger.warn("Model not updated!");
            throw new NotUpdatedException();
        }

        scheduleExporter.exportIfPossible(modelToUpdate);
        return modelToUpdate;
    }


    @PostMapping("/work")
    public OWorkHistory postOrUpdateStudy(@RequestBody OWorkHistory workHistory) {
        OWorkHistory modelToUpdate = workStorage.find(workHistory.getId()).orElse(workHistory);
        modelToUpdate.update(workHistory);
        if (!workStorage.save(modelToUpdate).isPresent()) {
            logger.warn("Model not updated!");
            throw new NotUpdatedException();
        }

        workExporter.exportIfPossible(modelToUpdate);
        return modelToUpdate;
    }




    @PostMapping("/grade")
    public OGrade postOrUpdateGrade(@RequestBody OGrade grade) {
        OGrade modelToUpdate = gradeStorage.find(grade.getId()).orElse(grade);
        modelToUpdate.update(grade);
        if (!gradeStorage.save(modelToUpdate).isPresent()) {
            logger.warn("Model not updated!");
            throw new NotUpdatedException();
        }

        gradeExporter.exportIfPossible(modelToUpdate);
        return modelToUpdate;
    }

    @PostMapping("/speciality")
    public OSpeciality postOrUpdateSpeciality(@RequestBody OSpeciality speciality) {
        OSpeciality modelToUpdate = specialityStorage.find(speciality.getCode()).orElse(speciality);
        modelToUpdate.update(speciality);
        if (!specialityStorage.save(modelToUpdate).isPresent()) {
            logger.warn("Model not updated!");
            throw new NotUpdatedException();
        }

        specialityExporter.exportIfPossible(modelToUpdate);
        return modelToUpdate;
    }

    @PostMapping("/subject")
    public OSubject postOrUpdateSubject(@RequestBody OSubject subject) {
        OSubject modelToUpdate = subjectStorage.find(subject.getCode()).orElse(subject);
        modelToUpdate.update(subject);
        if (!subjectStorage.save(modelToUpdate).isPresent()) {
            logger.warn("Model not updated!");
            throw new NotUpdatedException();
        }

        subjectExporter.exportIfPossible(modelToUpdate);
        return modelToUpdate;
    }

    @PostMapping("/person")
    public OPerson postOrUpdatePerson(@RequestBody OPerson p) {
        OPerson modelToUpdate = peopleStorage.findByFullNameAndBirth(p.getName(), p.getMiddleName(), p.getSurname(), p.getBirthPlace(), p.getBirthTimestamp())
                .orElse(p);
        modelToUpdate.update(p);
        if (!peopleStorage.save(modelToUpdate).isPresent()) {
            logger.warn("Model not updated!");
            throw new NotUpdatedException();
        }

        personExporter.exportIfPossible(modelToUpdate);
        return modelToUpdate;
    }

    @PostMapping("/study")
    public OStudy postOrUpdateStudy(@RequestBody OStudy study) {
        OStudy modelToUpdate = studyStorage.find(study.getId()).orElse(study);
        modelToUpdate.update(study);
        if (!studyStorage.save(modelToUpdate).isPresent()) {
            logger.warn("Model not updated!");
            throw new NotUpdatedException();
        }

        studyExporter.exportIfPossible(modelToUpdate);
        return modelToUpdate;
    }
}
