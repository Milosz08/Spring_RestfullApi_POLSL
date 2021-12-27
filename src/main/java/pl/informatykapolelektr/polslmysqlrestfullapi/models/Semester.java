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
    @Column(name = "sem_id")
    @JsonIgnore
    private String _id;

    @NotNull(message = "Brak/puste pole odpowiadające za numer semestru")
    @Column(name = "sem_number", nullable = false)
    private int identity;

    @NotEmpty(message = "Brak/puste pole odpowiadające za nazwę semestru")
    @Size(
        min = 3, max = 20,
        message = "Pole odpowiadające za nazwę semestru nie mieści się w zakresie od 3 do 20 znaków"
    )
    @Column(name = "sem_name", nullable = false)
    private String name;

}