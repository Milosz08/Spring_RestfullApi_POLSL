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
import org.springframework.stereotype.*;

import pl.informatykapolelektr.polslmysqlrestfullapi.exceptions.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.repository.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.service.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.utils.*;

@Service
public class LastUpdateServiceImplementation implements LastUpdateService {

    private final CalendarRepository calendarRepository;
    private final HelperLinkRepository helperLinkRepository;
    private final CovidRepository covidRepository;
    private final UserMessageRepository userMessageRepository;
    private final UserRepository userRepository;

    public LastUpdateServiceImplementation(
        CalendarRepository calendarRepository, HelperLinkRepository helperLinkRepository,
        CovidRepository covidRepository, UserMessageRepository userMessageRepository, UserRepository userRepository
    ) {
        this.calendarRepository = calendarRepository;
        this.helperLinkRepository = helperLinkRepository;
        this.covidRepository = covidRepository;
        this.userMessageRepository = userMessageRepository;
        this.userRepository = userRepository;
    }

    private Map<String, Object> singleRepoDateElement(List<Date> dates, Enums.AllUpdateTypes type) {
        String date = ServletTime.formattingDate(dates);
        return ReturnedJsonContent.returnedUpdatedDateContent(type, date);
    }

    @Override
    public List<Map<String, Object>> getAllLastUpdates() {
        List<Map<String, Object>> returned = new ArrayList<>();
        returned.add(singleRepoDateElement(calendarRepository.findLastEditField(), Enums.AllUpdateTypes.CALENDAR));
        returned.add(singleRepoDateElement(covidRepository.findLastEditField(), Enums.AllUpdateTypes.COVID));
        returned.add(singleRepoDateElement(helperLinkRepository.findLastEditField(), Enums.AllUpdateTypes.HELPERS));
        returned.add(singleRepoDateElement(userMessageRepository.findLastEditField(), Enums.AllUpdateTypes.USER_MESS));
        returned.add(singleRepoDateElement(userRepository.findLastEditField(), Enums.AllUpdateTypes.AUTH));
        return returned;
    }

    @Override
    public Map<String, Object> getSingleLastUpdate(Enums.AllUpdateTypes type) {
        String date;
        switch(type) {
            case CALENDAR:
                date = ServletTime.formattingDate(calendarRepository.findLastEditField());
                break;
            case COVID:
                date = ServletTime.formattingDate(covidRepository.findLastEditField());
                break;
            case HELPERS:
                date = ServletTime.formattingDate(helperLinkRepository.findLastEditField());
                break;
            case USER_MESS:
                date = ServletTime.formattingDate(userMessageRepository.findLastEditField());
                break;
            case AUTH:
                date = ServletTime.formattingDate(userRepository.findLastEditField());
            default:
                throw new ApiRequestException("Podany typ: " + type + " nie istenie w obsługiwanej encji!");
        }
        return ReturnedJsonContent.returnedUpdatedDateContent(type, date);
    }

}