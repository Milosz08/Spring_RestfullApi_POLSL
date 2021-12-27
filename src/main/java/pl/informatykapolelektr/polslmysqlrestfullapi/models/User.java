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
import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.utils.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.util.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "credentials")
public class User extends AuditModel {

    @Id
    @GenericGenerator(
        name = "user_id",
        strategy = "pl.informatykapolelektr.polslmysqlrestfullapi.utils.CustomIDGenerator"
    )
    @GeneratedValue(generator = "user_id")
    @Column(name = "user_id")
    private String _id;

    @Column(name = "user_role", unique = true)
    private int role = 0;

    @NotEmpty(message = "Brak/puste pole odpowiadające za nazwę użytkownika")
    @Column(name = "user_name", nullable = false)
    private String username;

    @NotEmpty(message = "Brak/puste pole odpowiadające za hasło użytkownika")
    @Column(name = "user_password", nullable = false)
    private String password;

    @NotNull(message = "Brak/puste pole odpowiadające za token administratora")
    @Column(name = "user_token", nullable = false)
    private String token;

    @Column(name = "last_logged", nullable = false)
    private String lastLogged;

}