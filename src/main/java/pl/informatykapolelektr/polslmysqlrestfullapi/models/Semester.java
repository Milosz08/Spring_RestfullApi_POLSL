/*
 * Copyright (c) 2021-2021, by Miłosz Gilga <https://miloszgilga.pl>
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

import com.fasterxml.jackson.annotation.*;
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
@Table(name = "semesters")
public class Semester extends AuditModel {

    @Id
    @GenericGenerator(
        name = "sem_id",
        strategy = "pl.informatykapolelektr.polslmysqlrestfullapi.utils.CustomIDGenerator"
    )
    @GeneratedValue(generator = "sem_id")
    @Column(name = "sem_id")
    private String _id;

    @NotNull(message = "Semester number field shouldn't be null type!")
    @Column(name = "sem_number", nullable = false)
    private int identity;

    @NotNull(message = "Semester name field shouldn't be null type!")
    @Size(min = 3, max = 20, message = "Semester name field should have from 3 to 20 characters!")
    @Column(name = "sem_name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "semesters")
    private Set<Subject> subjects = new HashSet<>();

}