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

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;

import java.util.*;

public class ReturnedNoContent {

    private static Map<String, Object> generateBasicInfo() {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", System.currentTimeMillis());
        body.put("status", HttpStatus.NO_CONTENT);
        return body;
    }

    private static Map<String, Object> generateMessage(String message) {
        Map<String, Object> messageProp = new LinkedHashMap<>();
        messageProp.put("value", message);
        return messageProp;
    }

    public static Map<String, Object> returnedNoContent(String message) {
        Map<String, Object> body = generateBasicInfo();
        body.put("message", generateMessage(message));
        return body;
    }

}