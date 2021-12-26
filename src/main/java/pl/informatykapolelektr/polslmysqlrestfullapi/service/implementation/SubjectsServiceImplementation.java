/*
 * Copyright (c) 2021, by Miłosz Gilga <https://miloszgilga.pl>
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 *
 *     <http://www.apache.org/license/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software distributed under
 * the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the license.
 */

package pl.informatykapolelektr.polslmysqlrestfullapi.service.implementation;

import org.springframework.stereotype.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.exceptions.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.models.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.repository.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.service.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.utils.*;

import java.util.*;

@Service
public class SubjectsServiceImplementation implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final SemesterRepository semesterRepository;
    private final DepartmentRepository departmentRepository;
    private final ClassesItemRepository classesItemRepository;

    public SubjectsServiceImplementation(SubjectRepository subjectRepository,
        SemesterRepository semesterRepository, DepartmentRepository departmentRepository,
        ClassesItemRepository classesItemRepository
    ) {
        this.subjectRepository = subjectRepository;
        this.semesterRepository = semesterRepository;
        this.departmentRepository = departmentRepository;
        this.classesItemRepository = classesItemRepository;
    }

    private Subject addOrUpdate(Subject subject) {
        subject.getIcon().set_id(new RandomHexGenerator().generateSequence());
        ManyToManyManageEntities.addOrUpdateSemesters(subject, semesterRepository);
        ManyToManyManageEntities.addOrUpdateDepartments(subject, departmentRepository);
        ManyToManyManageEntities.addOrUpdateClassesItems(subject, classesItemRepository);
        subjectRepository.save(subject);
        return subject;
    }

    @Override
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    @Override
    public Subject getSingleSubject(String id) {
        Optional<Subject> subjectFind = subjectRepository.findById(id);
        if (subjectFind.isPresent()) {
            return subjectFind.get();
        }
        throw new ApiRequestException("Przedmiot o ID: " + id + " nie znajduje się w bazie danych!");
    }

    @Override
    public Subject addSubject(Subject subject) {
        return addOrUpdate(subject);
    }

    @Override
    public Subject editSubject(String id, Subject subject) {
        Optional<Subject> subjectFind = subjectRepository.findById(id);
        if (subjectFind.isPresent()) {
            subject.set_id(id);
            return addOrUpdate(subject);
        }
        throw new ApiRequestException("Przedmiot o ID: " + id + " nie znajduje się w bazie danych!");
    }

    @Override
    public void deleteSingleSubject(String id) {

        subjectRepository.deleteById(id);
    }

    @Override
    public void deleteAllSubject() {
        semesterRepository.deleteAll();
        departmentRepository.deleteAll();
        classesItemRepository.deleteAll();
        subjectRepository.deleteAll();
    }

}