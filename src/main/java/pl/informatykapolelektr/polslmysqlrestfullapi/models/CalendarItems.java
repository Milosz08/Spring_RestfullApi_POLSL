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
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.util.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "calendar_items")
public class CalendarItems extends AuditModel {

    @Id
    @JsonIgnore
    @Column(name = "calendar_item_id")
    private String _id;

    @NotEmpty(message = "Brak/puste pole odpowiadające za rozpoczęcie aktywności wydarzenia kalendarza")
    @Column(name = "start_time")
    @Pattern(
        regexp = "/^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/",
        message = "Pole czasu rozpoczęcia aktywności nie jest w formacie HH:mm"
    )
    private String start;

    @NotNull(message = "Brak/puste pole odpowiadające za wiadomość o aktywności wydarzenia kalendarza")
    @Size(
        min = 10, max = 80,
        message = "Pole wiadomości o aktywności wydarzenia nie mieści się w przedziale od 10 do 80 znaków"
    )
    @Column(name = "calendar_item_message")
    private String message;

    @Column(name = "calendar_item_level")
    private Enums.CalendarImportantLevels importantLevel = Enums.CalendarImportantLevels.LOW;

}