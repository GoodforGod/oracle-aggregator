package io.university.postgres.controller;

import io.swagger.annotations.ApiOperation;
import io.university.api.error.NotUpdatedException;
import io.university.postgres.exporter.*;
import io.university.postgres.model.dao.*;
import io.university.postgres.storage.impl.*;
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

    @Autowired private SpecialityExporter specialityExporter;
    @Autowired private SubjectExporter subjectExporter;
    @Autowired private PersonExporter personExporter;
    @Autowired private GradeExporter gradeExporter;
    @Autowired private StudyExporter studyExporter;

    @Autowired private SpecialityStorage specialityStorage;
    @Autowired private SubjectStorage subjectStorage;
    @Autowired private PersonStorage peopleStorage;
    @Autowired private StudyStorage studyStorage;
    @Autowired private GradeStorage gradeStorage;

    @PostMapping("/grade")
    public Grade postOrUpdateGrade(@RequestBody Grade grade) {
        Grade modelToUpdate = gradeStorage.find(grade.getId()).orElse(grade);
        modelToUpdate.update(grade);
        if (!gradeStorage.save(modelToUpdate).isPresent()) {
            logger.warn("Model not updated!");
            throw new NotUpdatedException();
        }

        gradeExporter.exportIfPossible(modelToUpdate);
        return modelToUpdate;
    }

    @PostMapping("/speciality")
    public Speciality postOrUpdateSpeciality(@RequestBody Speciality speciality) {
        Speciality modelToUpdate = specialityStorage.find(speciality.getCode()).orElse(speciality);
        modelToUpdate.update(speciality);
        if (!specialityStorage.save(modelToUpdate).isPresent()) {
            logger.warn("Model not updated!");
            throw new NotUpdatedException();
        }

        specialityExporter.exportIfPossible(modelToUpdate);
        return modelToUpdate;
    }

    @PostMapping("/subject")
    public Subject postOrUpdateSubject(@RequestBody Subject subject) {
        Subject modelToUpdate = subjectStorage.find(subject.getCode()).orElse(subject);
        modelToUpdate.update(subject);
        if (!subjectStorage.save(modelToUpdate).isPresent()) {
            logger.warn("Model not updated!");
            throw new NotUpdatedException();
        }

        subjectExporter.exportIfPossible(modelToUpdate);
        return modelToUpdate;
    }

    @PostMapping("/person")
    public Person postOrUpdatePerson(@RequestBody Person p) {
        Person modelToUpdate = peopleStorage.findByFullNameAndBirth(p.getName(), p.getMiddleName(), p.getSurname(), p.getBirthPlace(), p.getBirthTimestamp())
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
    public Study postOrUpdateStudy(@RequestBody Study study) {
        Study modelToUpdate = studyStorage.find(study.getId()).orElse(study);
        modelToUpdate.update(study);
        if (!studyStorage.save(modelToUpdate).isPresent()) {
            logger.warn("Model not updated!");
            throw new NotUpdatedException();
        }

        studyExporter.exportIfPossible(modelToUpdate);
        return modelToUpdate;
    }
}
