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
import pl.informatykapolelektr.polslmysqlrestfullapi.models.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.repository.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.service.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.utils.*;

import java.util.*;
import java.util.stream.*;

@Service
public class SubjectsServiceImplementation implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final SemesterRepository semesterRepository;
    private final IconRepository iconRepository;
    private final DepartmentRepository departmentRepository;
    private final ClassesItemRepository classesItemRepository;

    public SubjectsServiceImplementation(SubjectRepository subjectRepository, SemesterRepository semesterRepository,
        IconRepository iconRepository, DepartmentRepository departmentRepository,
        ClassesItemRepository classesItemRepository
    ) {
        this.subjectRepository = subjectRepository;
        this.semesterRepository = semesterRepository;
        this.iconRepository = iconRepository;
        this.departmentRepository = departmentRepository;
        this.classesItemRepository = classesItemRepository;
    }

    private Subject addOrUpdate(Subject subject) {
        subject.getIcon().set_id(new RandomHexGenerator().generateSequence());
        //for(Semester item : subject.getSemesters()) {
        //    item.set_id(new RandomHexGenerator().generateSequence());
        //}
        return subject;
    }

    @Override
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    @Override
    public Subject getSingleSubject(String id) {
        Optional<Subject> subjectFind = subjectRepository.findById(id);
        if(subjectFind.isPresent()) {
            return addOrUpdate(subjectFind.get());
        }
        throw new RuntimeException("Subject not found for the id: " + id);
    }

    @Override
    public Subject addSubject(Subject subject) {
        subject.getIcon().set_id(new RandomHexGenerator().generateSequence());
        //for(Semester item : subject.getSemesters()) {
        //    item.set_id(new RandomHexGenerator().generateSequence());
        //}
        //for(Department item : subject.getDepartments()) {
        //   item.set_id(new RandomHexGenerator().generateSequence());
        //}
        //for(ClassesItem item : subject.getClassesPlatform()) {
        //    item.set_id(new RandomHexGenerator().generateSequence());
       //     System.out.println(item.get_id());
       // }
        semesterRepository.saveAll(subject.getSemesters());
        departmentRepository.saveAll(subject.getDepartments());
        classesItemRepository.saveAll(subject.getClassesPlatform());
        subjectRepository.save(subject);
        return subject;
    }

    @Override
    public Subject editSubject(String id, Subject subject) {
        Optional<Subject> subjectFind = subjectRepository.findById(id);
        if(subjectFind.isPresent()) {
            subject.set_id(id);
            return addOrUpdate(subject);
        }
        throw new RuntimeException("Subject not found for the id: " + id);
    }

    @Override
    public void deleteSingleSubject(String id) {
        subjectRepository.deleteById(id);
    }

    @Override
    public void deleteAllSubject() {
        subjectRepository.deleteAll();
    }

}