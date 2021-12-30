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

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import pl.informatykapolelektr.polslmysqlrestfullapi.exceptions.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.models.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.models.Calendar;
import pl.informatykapolelektr.polslmysqlrestfullapi.repository.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.service.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.utils.*;

@Service
public class CalendarServiceImplementation implements CalendarService {

    @Autowired
    private CalendarRepository calendarRepository;
    @Autowired
    private CalendarItemsRepository calendarItemsRepository;
    @Autowired
    private LastUpdateService lastUpdateService;

    private Calendar addOrUpdate(Calendar calendar) {
        for (CalendarItems item : calendar.getItems()) {
            item.set_id(new RandomHexGenerator().generateSequence());
        }
        calendar.setDateString(
            calendar.getYear() + "-" +
            ServletTime.insertZeros(calendar.getMonth()) + "-" + ServletTime.insertZeros(calendar.getDay())
        );
        calendarRepository.save(calendar);
        calendarItemsRepository.saveAll(calendar.getItems());
        return calendar;
    }

    @Override
    public List<Calendar> getAllCalendars() {
        List<Calendar> beforeSorting = calendarRepository.findAll();
        Collections.sort(beforeSorting, new Comparator<Calendar>() {
            @Override
            public int compare(Calendar o1, Calendar o2) {
                Date firstDate = new GregorianCalendar(o1.getYear(), o1.getMonth() - 1, o1.getDay()).getTime();
                Date secondDate = new GregorianCalendar(o2.getYear(), o2.getMonth() - 1, o2.getDay()).getTime();
                return firstDate.compareTo(secondDate);
            }
        });
        return beforeSorting;
    }

    @Override
    public Calendar getSingleCalendar(String id) {
        Optional<Calendar> calendar = calendarRepository.findById(id);
        if (calendar.isPresent()) {
            return calendar.get();
        }
        throw new ApiRequestException("Zawartość kalendarza o ID: '" + id + "' nie znajduje się w bazie danych");
    }

    @Override
    public Calendar addCalendar(Calendar calendar) {
        lastUpdateService.updateSelectedSection(Enums.AllUpdateTypes.CALENDAR);
        return addOrUpdate(calendar);
    }

    @Override
    public Calendar editCalendar(String id, Calendar calendar) {
        Optional<Calendar> calendarFind = calendarRepository.findById(id);
        calendar.set_id(id);
        if (calendarFind.isPresent()) {
            lastUpdateService.updateSelectedSection(Enums.AllUpdateTypes.CALENDAR);
            return addOrUpdate(calendar);
        }
        throw new ApiRequestException("Zawartość kalendarza o ID: '" + id + "' nie znajduje się w bazie danych");
    }

    @Override
    public void deleteSingleCalendar(String id) {
        lastUpdateService.updateSelectedSection(Enums.AllUpdateTypes.CALENDAR);
        calendarRepository.deleteById(id);
    }

    @Override
    public void deleteCalendars() {
        lastUpdateService.updateSelectedSection(Enums.AllUpdateTypes.CALENDAR);
        calendarRepository.deleteAll();
    }

}