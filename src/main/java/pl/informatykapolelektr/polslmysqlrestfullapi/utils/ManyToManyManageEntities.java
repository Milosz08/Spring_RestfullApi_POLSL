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

package pl.informatykapolelektr.polslmysqlrestfullapi.utils;

import pl.informatykapolelektr.polslmysqlrestfullapi.models.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.repository.*;

import java.util.*;

public class ManyToManyManageEntities<T> {

    private final List<T> elementToFind;
    private final Callback<T> callback;

    public ManyToManyManageEntities(List<T> elementToFind, Callback<T> callback) {
        this.elementToFind = elementToFind;
        this.callback = callback;
    }

    public void manageEntity() {
        for (T entity : elementToFind) {
            List<T> findElement = callback.findElement(entity);
            if (findElement.isEmpty()) {
                callback.actionOnEmpty(entity);
            } else {
                callback.actionOnNonEmpty(entity, findElement);
            }
        }
    }

    public static void addOrUpdateSemesters(Subject subject, SemesterRepository repo) {
        new ManyToManyManageEntities<>(subject.getSemesters(), new Callback<>() {
            @Override
            public List<Semester> findElement(Semester entity) {
                return repo.getSemesterBy(entity.getIdentity(), entity.getName());
            }
            @Override
            public void actionOnEmpty(Semester entity) {
                entity.set_id(new RandomHexGenerator().generateSequence());
                repo.save(entity);
            }
            @Override
            public void actionOnNonEmpty(Semester entity, List<Semester> findElms) {
                entity.set_id(findElms.get(0).get_id());
            }
        }).manageEntity();
    }

    public static void addOrUpdateDepartments(Subject subject, DepartmentRepository repo) {
        new ManyToManyManageEntities<>(subject.getDepartments(), new Callback<>() {
            @Override
            public List<Department> findElement(Department entity) {
                return repo.getDepartmentBy(entity.getTitle(), entity.getShortName(), entity.getLink());
            }
            @Override
            public void actionOnEmpty(Department entity) {
                entity.set_id(new RandomHexGenerator().generateSequence());
                repo.save(entity);
            }
            @Override
            public void actionOnNonEmpty(Department entity, List<Department> findElms) {
                entity.set_id(findElms.get(0).get_id());
            }
        }).manageEntity();
    }

    public static void addOrUpdateClassesItems(Subject subject, ClassesItemRepository repo) {
        new ManyToManyManageEntities<>(subject.getClassesPlatforms(), new Callback<>() {
            @Override
            public List<ClassesItem> findElement(ClassesItem entity) {
                return repo.getClassesItemBy(entity.getType(), entity.getPlace(), entity.getLink());
            }
            @Override
            public void actionOnEmpty(ClassesItem entity) {
                entity.set_id(new RandomHexGenerator().generateSequence());
                repo.save(entity);
            }
            @Override
            public void actionOnNonEmpty(ClassesItem entity, List<ClassesItem> findElms) {
                entity.set_id(findElms.get(0).get_id());
            }
        }).manageEntity();
    }

}