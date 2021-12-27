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
import org.springframework.beans.factory.annotation.*;

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

    @NotEmpty(message = "Brak/puste pole odpowiadające za tytuł przedmiotu")
    @Size(
        min = 3, max = 50,
        message = "Pole opisujące tytuł przedmiotu nie mieści się w zakresie od 3 do 50 znaków"
    )
    @Column(name = "subject_title", nullable = false)
    private String title;

    @Column(name = "subject_status", nullable = false)
    private boolean ifEnd = false;

    @OneToOne(targetEntity = Icon.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "icon_bind", referencedColumnName = "icon_id")
    @NotNull(message = "Przedmiot musi mieć obiekt opisujący ikonę")
    private Icon icon;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
        name = "subject_semesters",
        joinColumns = @JoinColumn(name = "subject_id"),
        inverseJoinColumns = @JoinColumn(name = "semester_id", referencedColumnName = "sem_id")
    )
    @NotNull(message = "Przedmiot musi mieć przynajmniej jeden obiekt opisujący semestr")
    private List<Semester> semesters = new LinkedList<>();

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
        name = "subject_departments",
        joinColumns = @JoinColumn(name = "subject_id"),
        inverseJoinColumns = @JoinColumn(name = "department_id", referencedColumnName = "dept_id")
    )
    @NotNull(message = "Przedmiot musi mieć przynajmniej jeden obiekt opisujący wydział")
    private List<Department> departments = new LinkedList<>();

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
        name = "subject_classes",
        joinColumns = @JoinColumn(name = "subject_id"),
        inverseJoinColumns = @JoinColumn(name = "classes_id")
    )
    @NotNull(message = "Przedmiot musi mieć przynajmniej jeden obiekt opisujący miejsce i typ zajęć")
    private List<ClassesItem> classesPlatforms = new LinkedList<>();

}