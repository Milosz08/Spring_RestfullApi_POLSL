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
    @Column(name = "dept_id")
    @JsonIgnore
    private String _id;

    @NotEmpty(message = "Brak/puste pole odpowiadające za nazwę wydziału")
    @Size(
        min = 3, max = 50,
        message = "Pole opisujące tytuł wydziału powinno mieć od 3 do 50 znaków"
    )
    @Column(name = "dept_name", nullable = false)
    private String title;

    @NotEmpty(message = "Brak/puste pole odpowiadające za skrót nazwy wydziału")
    @Column(name = "dept_short", nullable = false)
    private String shortName;

    @NotEmpty(message = "Brak/puste pole odpowiadające za link wydziału do platformy")
    @Size(
        min = 10,
        message = "Pole zawierające link do platformy powinno mieć co najmniej 10 znaków"
    )
    @Column(name = "dept_link", nullable = false)
    private String link;

}
