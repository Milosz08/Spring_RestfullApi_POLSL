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
@Table(name = "covid_data")
public class Covid extends AuditModel {

    @Id
    @GenericGenerator(
        name = "covid_id",
        strategy = "pl.informatykapolelektr.polslmysqlrestfullapi.utils.CustomIDGenerator"
    )
    @GeneratedValue(generator = "covid_id")
    @Column(name = "covid_id")
    private String _id;

    @NotNull(message = "Covid type field shouldn't be null type!")
    @Column(name = "covid_dir", nullable = false)
    private Enums.CovidDataEnums type;

    @NotNull(message = "Covid description field shouldn't be null type!")
    @Column(name = "covid_descr", nullable = false)
    private String description;

    @Column(name = "covid_risk", nullable = false)
    private int actualRiskNumber = 0;

}