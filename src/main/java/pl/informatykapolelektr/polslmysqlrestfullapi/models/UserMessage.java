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
import pl.informatykapolelektr.polslmysqlrestfullapi.utils.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_messages")
public class UserMessage extends AuditModel {

    @Id
    @GenericGenerator(
        name = "message_id",
        strategy = "pl.informatykapolelektr.polslmysqlrestfullapi.utils.CustomIDGenerator"
    )
    @GeneratedValue(generator = "message_id")
    @Column(name = "message_id")
    private String _id;

    @NotNull(message = "Username field shouldn't be null type!")
    @Column(name = "user_name", nullable = false)
    private String userIdentity;

    @NotNull(message = "User choice field shouldn't be null type!")
    @Column(name = "user_choice", nullable = false)
    private Enums.UserMessChoices userChoice;

    @NotNull(message = "User message field shouldn't be null type!")
    @Column(name = "user_message", nullable = false)
    private String userMessage;

    @Column(name = "if_clicked", nullable = false)
    private boolean ifClicked = false;

}