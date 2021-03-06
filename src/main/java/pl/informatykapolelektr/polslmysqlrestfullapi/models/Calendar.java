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
@Table(name = "calendar")
public class Calendar extends AuditModel {

    @Id
    @GenericGenerator(
        name = "calendar_id",
        strategy = "pl.informatykapolelektr.polslmysqlrestfullapi.utils.CustomIDGenerator"
    )
    @GeneratedValue(generator = "calendar_id")
    @Column(name = "calendar_id")
    private String _id;

    @NotNull(message = "Brak/puste pole odpowiadające za dzień aktywności kalendarza")
    @Column(name = "calendar_day", nullable = false)
    private int day;

    @NotNull(message = "Brak/puste pole odpowiadające za miesiąc aktywności kalendarza")
    @Column(name = "calendar_moth", nullable = false)
    private int month;

    @NotNull(message = "Brak/puste pole odpowiadające za rok aktywności kalendarza")
    @Column(name = "calendar_year", nullable = false)
    private int year;

    @Column(name = "reverse_date", nullable = false)
    private String dateString;

    @OneToMany(targetEntity = CalendarItems.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "item_bind", referencedColumnName = "calendar_id")
    @NotNull(message = "Wydarzenie musi mieć przynajmniej jednen obiekt z aktywnością")
    private List<CalendarItems> items;

}