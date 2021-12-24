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

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.hibernate.annotations.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.utils.*;

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
@Table(name = "departments")
public class Department extends AuditModel {

    @Id
    @GenericGenerator(
        name = "dept_id",
        strategy = "pl.informatykapolelektr.polslmysqlrestfullapi.utils.CustomIDGenerator"
    )
    @GeneratedValue(generator = "dept_id")
    @Column(name = "dept_id")
    private String _id = new RandomHexGenerator().generateSequence();

    @NotNull(message = "Department name field shouldn't be null type!")
    @Size(min = 3, max = 50, message = "Department name should have from 3 to 50 characters!")
    @Column(name = "dept_name", nullable = false)
    private String title;

    @NotNull(message = "Department short name field shouldn't be null type!")
    @Size(min = 2, message = "Department name should have at least of 2 characters!")
    @Column(name = "dept_short", nullable = false)
    private String shortName;

    @NotNull(message = "Department link field shouldn't be null type!")
    @Size(min = 10, message = "Department link should have at least of 10 characters!")
    @Column(name = "dept_link", nullable = false)
    private String link;

    @OneToMany(mappedBy = "departments")
    private Set<Subject> subjects = new HashSet<>();

}
