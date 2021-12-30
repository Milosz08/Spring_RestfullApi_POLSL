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

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.parameters.*;
import org.springframework.stereotype.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.exceptions.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.models.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.repository.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.service.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.utils.*;

import java.util.*;

@Service
public class ScheduleServiceImplementation implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private IconRepository iconRepository;
    @Autowired
    private ClassesItemRepository classesItemRepository;

    private static final String ALL = "wszystkie zajęcia";

    private void checkDayIsValid(Schedule schedule) {
        if (schedule.getDay() > 6 || schedule.getDay() < 0) {
            throw new ApiRequestException("Błędny numer dnia: '" + schedule.getDay() + "'");
        }
    }

    private void insertAdditionalIcon(Schedule schedule, Icon icon, Subject subject) {
        List<Icon> findIcons = iconRepository.getIconsByNameAndFamily(icon.getFamily(), icon.getName());
        schedule.setIcon(subject.getIcon());
        if (findIcons.isEmpty()) {
            schedule.getIcon().set_id(new RandomHexGenerator().generateSequence());
            iconRepository.save(schedule.getIcon());
        } else {
            schedule.getIcon().set_id(findIcons.get(0).get_id());
        }
    }

    private ClassesItem insertAdditionalClassesInfo(ClassesItem fI, ClassesItem sI, String type) {
        sI.setType(fI.getType().toLowerCase().equals(ALL) ? type : fI.getType());
        sI.setPlace(fI.getPlace());
        sI.setLink(fI.getLink());
        List<ClassesItem> findClItem = classesItemRepository.getClassesItemBy(sI.getType(), sI.getPlace(), sI.getLink());
        if (findClItem.isEmpty()) {
            sI.set_id(new RandomHexGenerator().generateSequence());
            classesItemRepository.save(sI);
        } else {
            sI.set_id(findClItem.get(0).get_id());
        }
        return sI;
    }

    private Schedule fillObjectWithAdditionalValues(Schedule schedule, String type) {
        Optional<Subject> findSubject = subjectRepository.findSubjectByTitle(schedule.getTitle());
        if (findSubject.isPresent()) {
            Optional<ClassesItem> findItem = findSubject.get().getClassesPlatforms().stream().filter(item -> (
                    item.getType().toLowerCase().equals(type.toLowerCase()) || item.getType().toLowerCase().equals(ALL))
            ).findFirst();
            if (findItem.isPresent()) {
                ClassesItem saveItem = insertAdditionalClassesInfo(findItem.get(), schedule.getClassesInfo(), type);
                insertAdditionalIcon(schedule, findSubject.get().getIcon(), findSubject.get());
                schedule.setClassesInfo(saveItem);
                scheduleRepository.save(schedule);
                return schedule;
            }
            throw new ApiRequestException("Typ zajęć: '" + type + "' nie istnieje. Brak odwołania w tabeli nadrzędnej");
        }
        throw new ApiRequestException("Przedmiot o tytule '" + schedule.getTitle() + "' nie znajduje się w bazie danych");
    }

    private Schedule addOrUpdate(Schedule schedule, String type) {
        schedule.setRoom(schedule.getRoom().toUpperCase(Locale.ROOT));
        checkDayIsValid(schedule);
        return fillObjectWithAdditionalValues(schedule, type);
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
        if (findScheduleSubject.isPresent()) {
            return findScheduleSubject.get();
        }
        throw new ApiRequestException("Przedmiot o ID: '" + id + "' nie znajduje się w bazie danych");
    }

    @Override
    public Schedule addScheduleSubject(Schedule schedule) {
        Optional<Subject> findSubject = subjectRepository.findSubjectByTitle(schedule.getTitle());
        if (findSubject.isPresent()) {
            return addOrUpdate(schedule, schedule.getType());
        }
        throw new ApiRequestException("Przedmiot o tytule '" + schedule.getTitle() + "' nie istenieje w tabeli nadrzędnej");
    }

    @Override
    public Schedule editScheduleSubject(String id, Schedule schedule) {
        Optional<Schedule> findScheduleSubject = scheduleRepository.findById(id);
        if (findScheduleSubject.isPresent()) {
            schedule.set_id(id);
            return addOrUpdate(schedule, schedule.getType());
        }
        throw new ApiRequestException("Przedmiot o ID: '" + id + "' nie znajduje się w bazie danych");
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