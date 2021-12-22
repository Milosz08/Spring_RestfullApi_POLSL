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

package pl.informatykapolelektr.polslmysqlrestfullapi.utils;

public class Enums {

    /**
     * Possible types of calendar important levels indicators (closely related to the ReactJS front-end
     * part -> see, ReactJS repository: https://github.com/Milosz08/ReactJS_Web_Application_POLSL).
     */
    public enum CalendarImportantLevels {
        LOW, MEDIUM, HIGH
    }

    /**
     * Possible Covid-19 information tile types (closely related to the ReactJS front-end
     * part -> see, ReactJS repository: https://github.com/Milosz08/ReactJS_Web_Application_POLSL).
     */
    public enum CovidDataEnums {
        LEFT_TILE, CENTER_TILE, RIGHT_TILE
    }
}