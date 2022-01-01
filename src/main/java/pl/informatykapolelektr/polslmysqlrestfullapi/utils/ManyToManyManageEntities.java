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

public class ManyToManyManageEntities {

    public static void addOrUpdateSemesters(Subject subject, SemesterRepository repo) {
        for (Semester semester : subject.getSemesters()) {
            List<Semester> findElement = repo.getSemesterBy(semester.getIdentity(), semester.getName());
            if (findElement.isEmpty()) {
                semester.set_id(new RandomHexGenerator().generateSequence());
                repo.save(semester);
            } else {
                semester.set_id(findElement.get(0).get_id());
            }
        }
    }

    public static void addOrUpdateDepartments(Subject subject, DepartmentRepository repo) {
        for (Department department : subject.getDepartments()) {
            List<Department> findElement = repo.getDepartmentBy(
                    department.getTitle(), department.getShortName(), department.getLink()
            );
            if (findElement.isEmpty()) {
                department.set_id(new RandomHexGenerator().generateSequence());
                repo.save(department);
            } else {
                department.set_id(findElement.get(0).get_id());
            }
        }
    }

    public static void addOrUpdateClassesItems(Subject subject, ClassesItemRepository repo) {
        for (ClassesItem classesItem : subject.getClassesPlatforms()) {
            List<ClassesItem> findElement = repo.getClassesItemBy(
                    classesItem.getType(), classesItem.getPlace(), classesItem.getLink()
            );
            if (findElement.isEmpty()) {
                classesItem.set_id(new RandomHexGenerator().generateSequence());
                repo.save(classesItem);
            } else {
                classesItem.set_id(findElement.get(0).get_id());
            }
        }
    }

}