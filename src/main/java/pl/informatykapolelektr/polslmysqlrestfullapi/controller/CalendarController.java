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

package pl.informatykapolelektr.polslmysqlrestfullapi.controller;

import java.util.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.models.Calendar;
import pl.informatykapolelektr.polslmysqlrestfullapi.service.*;

import javax.validation.*;

import static pl.informatykapolelektr.polslmysqlrestfullapi.utils.ServletConfig.*;

@RestController
@RequestMapping(DEF_PREFIX + CALENDAR)
public class CalendarController {

    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping
    public ResponseEntity<List<Calendar>> getAllCalendars() {
        return new ResponseEntity<>(calendarService.getAllCalendars(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Calendar> getSingleCalendar(@PathVariable String id) {
        return new ResponseEntity<>(calendarService.getSingleCalendar(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Calendar> addCalendar(@Valid @RequestBody Calendar calendar) {
        return new ResponseEntity<>(calendarService.addCalendar(calendar), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Calendar> editCalendar(@PathVariable String id, @Valid @RequestBody Calendar calendar) {
        return new ResponseEntity<>(calendarService.editCalendar(id, calendar), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSingleCalendar(@PathVariable String id) {
        calendarService.deleteSingleCalendar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteCalendars() {
        calendarService.deleteCalendars();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}