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
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "icon_entity")
public class Icon extends AuditModel {

    @Id
    @Column(name = "icon_id")
    @JsonIgnore
    private String _id;

    @NotNull(message = "Icon family field shouldn't be null type!")
    @NotBlank(message = "Icon family must have content!")
    @Column(name = "icon_family", nullable = false)
    private String family;

    @NotNull(message = "Icon name field shouldn't be null type!")
    @NotBlank(message = "Icon name must have content!")
    @Column(name = "icon_name", nullable = false)
    private String name;

}
