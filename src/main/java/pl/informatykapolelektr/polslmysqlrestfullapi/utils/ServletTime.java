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

import java.time.*;
import java.time.format.*;
import java.util.*;

public class ServletTime {

    private final String date;

    public ServletTime(LocalDateTime date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.date = dtf.format(date);
    }

    public ServletTime(LocalDateTime date, String pattern) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        this.date = dtf.format(date);
    }

    private static LocalDateTime convertToLocalDateTimeViaMillisecond(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static String formattingDate(List<Date> getFromSqlQuery) {
        getFromSqlQuery.sort(Date::compareTo);
        LocalDateTime convert = convertToLocalDateTimeViaMillisecond(getFromSqlQuery.get(getFromSqlQuery.size() - 1));
        return convert.format(DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm:ss"));
    }

    public static String insertZeros(Integer numberValue) {
        return numberValue < 10 ? "0" + numberValue : numberValue.toString();
    }

    public String getOnlyDate() {
        return date.substring(0, date.indexOf(' '));
    }

    public String getOnlyTime() {
        return date.substring(date.indexOf(' ') + 1);
    }

    public String getFullDate() {
        return getOnlyDate() + ", " + getOnlyTime();
    }

}