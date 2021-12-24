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

package pl.informatykapolelektr.polslmysqlrestfullapi.models;

import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.util.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subjects")
public class Subject extends AuditModel {

    @Id
    @GenericGenerator(
        name = "subject_id",
        strategy = "pl.informatykapolelektr.polslmysqlrestfullapi.utils.CustomIDGenerator"
    )
    @GeneratedValue(generator = "subject_id")
    @Column(name = "subject_id")
    private String _id;

    @NotNull(message = "Subject title field shouldn't be null type!")
    @Size(min = 3, max = 50, message = "Subject title should have from 3 to 50 characters!")
    @Column(name = "subject_title", nullable = false)
    private String title;

    @NotNull(message = "Subject status flag field shouldn't be null type!")
    @Column(name = "subject_status", nullable = false)
    private boolean ifEnd;

    @ManyToMany()
    @JoinTable(
        name = "subject_semesters",
        joinColumns = @JoinColumn(name = "subject_id"),
        inverseJoinColumns = @JoinColumn(name = "semester_id", referencedColumnName = "sem_id")
    )
    private Set<Semester> semesters;

    @ManyToMany()
    @JoinTable(
        name = "subject_deptartments",
        joinColumns = @JoinColumn(name = "subject_id"),
        inverseJoinColumns = @JoinColumn(name = "department_id", referencedColumnName = "dept_id")
    )
    private Set<Department> departments;

    @OneToOne(targetEntity = Icon.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "icon_bind", referencedColumnName = "icon_id")
    @NotNull(message = "Subject must have single icon field properties!")
    private Icon icon;

    @ManyToMany()
    @JoinTable(
        name = "subject_classes",
        joinColumns = @JoinColumn(name = "subject_id"),
        inverseJoinColumns = @JoinColumn(name = "classes_id", referencedColumnName = "classes_id")
    )
    private Set<ClassesItem> classesPlatform;

}