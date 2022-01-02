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

import pl.informatykapolelektr.polslmysqlrestfullapi.exceptions.*;

import java.util.*;

public class ReturnedJsonContent {

    private static Map<String, Object> generateUpdatedBasicInfo(Enums.AllUpdateTypes type) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("_id", new RandomHexGenerator().generateSequence());
        body.put("updateDateFor", type);
        return body;
    }

    private static Map<String, Object> generateServletTimeInfo(String date) {
        Map<String, Object> servletTime = new LinkedHashMap<>();
        String[] convert = date.split(",");
        servletTime.put("fullDate", convert[0]);
        servletTime.put("fullTime", convert[1].replace(" ", ""));
        return servletTime;
    }

    private static Map<String, Object> generateCredentialsFields(List<Boolean> valid) {
        Map<String, Object> credentialsFields = new LinkedHashMap<>();
        String[] allKeys = { "username", "password", "token" };
        if(valid.isEmpty() || valid.size() > 3) {
            throw new ApiRequestException("Credentials data fields have bad size");
        }
        for(int i = 0; i < allKeys.length; i++) {
            credentialsFields.put(allKeys[i], !valid.get(i));
        }
        return credentialsFields;
    }

    public static Map<String, Object> returnedUpdatedDateContent(Enums.AllUpdateTypes type, String date) {
        Map<String, Object> body = generateUpdatedBasicInfo(type);
        body.put("servletTime", generateServletTimeInfo(date));
        return body;
    }

    public static Map<String, Object> returnedCredentials(List<Boolean> valid, int role, boolean error, String jwt) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("valid", error ? "Brak autoryzacji" : "Autoryzacja uzyskana");
        body.put("authLevel", role);
        body.put("jwtToken", jwt);
        body.put("fieldsErrors", generateCredentialsFields(valid));
        return body;
    }

    public static Map<String, Object> returnedCredentialsNotFound() {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("valid", "Nie znaleziono użytkownika");
        body.put("authLevel", null);
        body.put("fieldsErrors", generateCredentialsFields(Arrays.asList(false, false, false)));
        return body;
    }

}