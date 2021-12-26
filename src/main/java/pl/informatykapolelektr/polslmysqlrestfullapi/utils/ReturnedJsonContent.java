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

import java.util.*;

public class ReturnedJsonContent {

    private static Map<String, Object> generateUpdatedBasicInfo(Enums.AllUpdateTypes type) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("_id", new RandomHexGenerator().generateSequence());
        body.put("updatedDateFor", type);
        return body;
    }

    private static Map<String, Object> generateServletTimeInfo(String date) {
        Map<String, Object> servletTime = new LinkedHashMap<>();
        String[] convert = date.split(",");
        servletTime.put("fullDate", convert[0]);
        servletTime.put("fullTime", convert[1].replace(" ", ""));
        return servletTime;
    }

    public static Map<String, Object> returnedUpdatedDateContent(Enums.AllUpdateTypes type, String date) {
        Map<String, Object> body = generateUpdatedBasicInfo(type);
        body.put("servletTime", generateServletTimeInfo(date));
        return body;
    }

}