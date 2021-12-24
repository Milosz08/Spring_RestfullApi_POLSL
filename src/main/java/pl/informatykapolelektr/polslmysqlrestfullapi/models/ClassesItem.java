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
@Table(name = "classes_items")
public class ClassesItem extends AuditModel {

    @Id
    @GenericGenerator(
        name = "classes_id",
        strategy = "pl.informatykapolelektr.polslmysqlrestfullapi.utils.CustomIDGenerator"
    )
    @GeneratedValue(generator = "classes_id")
    @Column(name = "classes_id")
    private String _id;

    @NotNull(message = "Classes type field shouldn't be null type!")
    @Size(min = 3, max = 50, message = "Classes type field should have from 3 to 50 characters!")
    @Column(name = "classes_type", nullable = false)
    private String type;

    @NotNull(message = "Classes place field shouldn't be null type!")
    @Size(min = 3, max = 20, message = "Classes place field should have at least of 3 characters!")
    @Column(name = "classes_place", nullable = false)
    private String place;

    @NotNull(message = "Classes type field shouldn't be null type!")
    @Size(min = 3, max = 500, message = "Classes link field should have at least of 3 characters!")
    @Column(name = "classes_link", nullable = false)
    private String link;

    @OneToMany(mappedBy = "classesPlatform")
    private Set<Subject> subjects = new HashSet<>();

}