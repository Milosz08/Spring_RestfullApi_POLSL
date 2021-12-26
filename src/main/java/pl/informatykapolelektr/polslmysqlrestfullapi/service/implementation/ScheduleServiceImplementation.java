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

package pl.informatykapolelektr.polslmysqlrestfullapi.service.implementation;

import org.springframework.stereotype.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.exceptions.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.models.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.repository.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.service.*;

import java.util.*;

@Service
public class ScheduleServiceImplementation implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final SubjectRepository subjectRepository;

    public ScheduleServiceImplementation(ScheduleRepository scheduleRepository, SubjectRepository subjectRepository) {
        this.scheduleRepository = scheduleRepository;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<Schedule> getAllScheduleSubjects() {
        return scheduleRepository.findAll();
    }

    @Override
    public List<Schedule> getAllScheduleByDay(int day) {
        return scheduleRepository.getScheduleSubjectsByDayOfWeek(day);
    }

    @Override
    public Schedule getSingleScheduleSubject(String id) {
        Optional<Schedule> findScheduleSubject = scheduleRepository.findById(id);
        if(findScheduleSubject.isPresent()) {
            return findScheduleSubject.get();
        }
        throw new ApiRequestException("Przedmiot o ID: " + id + " nie znajduje się w bazie danych!");
    }

    @Override
    public Schedule addScheduleSubject(Schedule schedule) {
        Optional<Subject> findSubject = subjectRepository.findSubjectByTitle(schedule.getTitle());
        if(findSubject.isPresent()) {
            return scheduleRepository.save(schedule);
        }
        throw new ApiRequestException("Próba dodania przedmiotu nieznajdującego się w bazie danych!");
    }

    @Override
    public Schedule editScheduleSubject(String id, Schedule schedule) {
        Optional<Schedule> findScheduleSubject = scheduleRepository.findById(id);
        if(findScheduleSubject.isPresent()) {
            schedule.set_id(id);
            scheduleRepository.save(schedule);
            return schedule;
        }
        throw new ApiRequestException("Przedmiot o ID: " + id + " nie znajduje się w bazie danych!");
    }

    @Override
    public void deleteSingleScheduleSubject(String id) {
        scheduleRepository.deleteById(id);
    }

    @Override
    public void deleteAllScheduleSubjects() {
        scheduleRepository.deleteAll();
    }

}