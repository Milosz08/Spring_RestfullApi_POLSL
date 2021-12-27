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

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.models.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.service.*;

import javax.validation.*;
import java.util.*;

import static pl.informatykapolelektr.polslmysqlrestfullapi.utils.ServletConfig.*;

@RestController
@RequestMapping(DEF_PREFIX + SCHEDULE)
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public ResponseEntity<List<Schedule>> getAllScheduleSubjects() {
        return new ResponseEntity<>(scheduleService.getAllScheduleSubjects(), HttpStatus.OK);
    }

    @GetMapping("/day/{day}")
    public ResponseEntity<List<Schedule>> getAllSchedulesByDay(@PathVariable int day) {
        return new ResponseEntity<>(scheduleService.getAllScheduleByDay(day), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getSingleScheduleSubject(@PathVariable String id) {
        return new ResponseEntity<>(scheduleService.getSingleScheduleSubject(id), HttpStatus.OK);
    }

    @PostMapping("/{type}")
    public ResponseEntity<Schedule> addScheduleSubject(@Valid @RequestBody Schedule schedule, @PathVariable String type) {
        return new ResponseEntity<>(scheduleService.addScheduleSubject(schedule, type), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Schedule> editScheduleSubject(@PathVariable String id, @Valid @RequestBody Schedule schedule) {
        return new ResponseEntity<>(scheduleService.editScheduleSubject(id, schedule), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSingleScheduleSubject(@PathVariable String id) {
        scheduleService.deleteSingleScheduleSubject(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllScheduleSubjects() {
        scheduleService.deleteAllScheduleSubjects();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}