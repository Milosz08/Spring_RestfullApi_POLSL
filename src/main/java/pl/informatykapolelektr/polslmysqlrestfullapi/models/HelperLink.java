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
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "helper_links")
public class HelperLink extends AuditModel {

    @Id
    @GenericGenerator(
        name = "helper_id",
        strategy = "pl.informatykapolelektr.polslmysqlrestfullapi.utils.CustomIDGenerator"
    )
    @GeneratedValue(generator = "helper_id")
    @Column(name = "helper_id")
    private String _id;

    @NotEmpty(message = "Brak/puste pole odpowiadające za tytuł linku pomocy")
    @Size(
        min = 3, max = 50,
        message = "Pole opisujące tytuł linku powinno zawierać od 3 do 50 znaków"
    )
    @Column(name = "helper_title", nullable = false)
    private String helperTitle;

    @NotEmpty(message = "Brak/puste pole odpowiadające za link pomocy")
    @Column(name = "helper_link", nullable = false)
    private String helperLink;

    @OneToOne(targetEntity = Icon.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "icon_bind", referencedColumnName = "icon_id")
    @NotNull(message = "Link pomocy musi mieć jednen obiekt opisujący ikonę")
    private Icon helperIcon;

}