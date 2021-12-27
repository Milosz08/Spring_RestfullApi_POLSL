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

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "schedule_subjects")
public class Schedule extends AuditModel {

    @Id
    @GenericGenerator(
        name = "schedule_subject_id",
        strategy = "pl.informatykapolelektr.polslmysqlrestfullapi.utils.CustomIDGenerator"
    )
    @GeneratedValue(generator = "schedule_subject_id")
    @Column(name = "schedule_subject_id")
    private String _id;

    @NotEmpty(message = "Brak/puste pole odpowiadające za tytuł przedmiotu w planie zajęć")
    @Size(
        min = 3, max = 50,
        message = "Pole odpowiadające za tytuł przedmiotu w planie zajęć nie mieści się w zakresie od 3 do 50 znaków"
    )
    @Column(name = "schedule_subject_title", nullable = false)
    private String title;

    @NotNull(message = "Brak/puste pole odpowiadające za numer dnia (0-6) przedmiotu w planie zajęć")
    @Column(name = "schedule_subject_day", nullable = false)
    private int day;

    @NotEmpty(message = "Brak/puste pole odpowiadające za grupę zajęciową przedmiotu w planie zajęć")
    @Size(
        min = 3, max = 20,
        message = "Pole odpowiadające za grupę zajęciową nie mieści się w zakresie od 3 do 20 znaków"
    )
    @Column(name = "schedule_subject_group", nullable = false)
    private String group;

    @NotEmpty(message = "Brak/puste pole odpowiadające za godzinę rozpoczęcia zajęć")
    @Pattern(
        regexp = "^([0-1]?[0-9]|[2][0-3]):([0-5][0-9])(:[0-5][0-9])?$",
        message = "Pole czasu rozpoczęcia zajęć nie jest w formacie HH:mm"
    )
    @Column(name = "schedule_subject_start", nullable = false)
    private String startHour;

    @NotEmpty(message = "Brak/puste pole odpowiadające za godzinę zakończenia zajęć")
    @Pattern(
        regexp = "^([0-1]?[0-9]|[2][0-3]):([0-5][0-9])(:[0-5][0-9])?$",
        message = "Pole czasu zakończenia zajęć nie jest w formacie HH:mm"
    )
    @Column(name = "schedule_subject_end", nullable = false)
    private String endHour;

    @Column(name = "schedule_subject_type", nullable = false)
    private String type;

    @NotEmpty(message = "Brak/puste pole odpowiadające za pokój odbywanych zajęć")
    @Size(
        min = 2, max = 10,
        message = "Pole odpowiadające za pokój odbywania zajęć nie mieści się w zakresie od 2 do 10 znaków"
    )
    @Column(name = "schedule_subject_room", nullable = false)
    private String room;

    @Column(name = "schedule_subject_place", nullable = false)
    private String place;

    @Column(name = "schedule_subject_link", nullable = false)
    private String link;

}