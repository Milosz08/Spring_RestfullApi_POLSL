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
    @Column(name = "classes_id")
    @JsonIgnore
    private String _id;

    @NotEmpty(message = "Brak/puste pole odpowiadające za typ zajęć")
    @Size(
        min = 3, max = 50,
        message = "Pole typu zajęć nie mieści się w przedziale od 3 do 50 znaków"
    )
    @Column(name = "classes_type", nullable = false)
    private String type;

    @NotEmpty(message = "Brak/puste pole odpowiadające za miejsce odbywania zajęć")
    @Size(
        min = 3, max = 20,
        message = "Pole miejsca odbywania zajęć nie mieści się w przedziale od 3 do 20 znaków"
    )
    @Column(name = "classes_place", nullable = false)
    private String place;

    @NotEmpty(message = "Brak/puste pole odpowiadające za link do platformy zajęć")
    @Size(
        min = 3, max = 500,
        message = "Pole linku platformy powinno zawierać przynajmniej 3 znaki"
    )
    @Column(name = "classes_link", nullable = false)
    private String link;

}