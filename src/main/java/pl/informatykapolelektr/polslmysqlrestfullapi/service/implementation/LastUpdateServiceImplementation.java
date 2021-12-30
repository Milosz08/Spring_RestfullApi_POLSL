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

import java.time.*;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import pl.informatykapolelektr.polslmysqlrestfullapi.exceptions.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.models.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.repository.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.service.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.utils.*;

@Service
public class LastUpdateServiceImplementation implements LastUpdateService {

    @Autowired
    private LastUpdateRepository lastUpdateRepository;

    @Override
    public List<Map<String, Object>> getAllLastUpdates() {
        List<LastUpdate> allLastUpdates = lastUpdateRepository.findAll();
        List<Map<String, Object>> returned = new ArrayList<>();
        for (LastUpdate allLastUpdate : allLastUpdates) {
            String date = allLastUpdate.getFullDate() + ", " + allLastUpdate.getFullTime();
            Enums.AllUpdateTypes type = allLastUpdate.getUpdateDateFor();
            returned.add(ReturnedJsonContent.returnedUpdatedDateContent(type, date));
        }
        return returned;
    }

    @Override
    public Map<String, Object> getSingleLastUpdate(Enums.AllUpdateTypes type) {
        Optional<LastUpdate> findLastUpdateByType = lastUpdateRepository.getLastUpdateByType(type);
        if(findLastUpdateByType.isPresent()) {
            String date = findLastUpdateByType.get().getFullDate() + ", " + findLastUpdateByType.get().getFullTime();
            return ReturnedJsonContent.returnedUpdatedDateContent(type, date);
        }
        throw new ApiRequestException("Podany typ: '" + type + "' nie istnieje");
    }

    @Override
    public void updateSelectedSection(Enums.AllUpdateTypes type) {
        Optional<LastUpdate> findLastUpdateByType = lastUpdateRepository.getLastUpdateByType(type);
        if(findLastUpdateByType.isPresent()) {
            findLastUpdateByType.get().set_id(findLastUpdateByType.get().get_id());
            findLastUpdateByType.get().setFullDate(new ServletTime(LocalDateTime.now()).getOnlyDate());
            findLastUpdateByType.get().setFullTime(new ServletTime(LocalDateTime.now()).getOnlyTime());
            lastUpdateRepository.save(findLastUpdateByType.get());
        } else {
            LastUpdate lastUpdate = new LastUpdate();
            lastUpdate.set_id(new RandomHexGenerator().generateSequence());
            lastUpdate.setUpdateDateFor(type);
            lastUpdate.setFullDate(new ServletTime(LocalDateTime.now()).getOnlyDate());
            lastUpdate.setFullTime(new ServletTime(LocalDateTime.now()).getOnlyTime());
            lastUpdateRepository.save(lastUpdate);
        }
    }

}