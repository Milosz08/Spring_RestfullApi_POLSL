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
import pl.informatykapolelektr.polslmysqlrestfullapi.utils.*;

import java.util.*;

@Service
public class CovidServiceImplementation implements CovidService {

    private final CovidRepository covidRepository;

    public CovidServiceImplementation(CovidRepository covidRepository) {
        this.covidRepository = covidRepository;
    }

    @Override
    public List<Covid> getAllCovidData() {
        return covidRepository.findAll();
    }

    @Override
    public Covid addCovidData(Covid covid) {
        return covidRepository.save(covid);
    }

    @Override
    public Covid editCovidData(Enums.CovidDataEnums type, Covid covid) {
        Optional<Covid> covidUpdate = covidRepository.findByType(type);
        if(covidUpdate.isPresent()) {
            covid.set_id(covidUpdate.get().get_id());
            return covidRepository.save(covid);
        }
        throw new ApiRequestException("Element o typie: " + type + " nie znajduje się w bazie danych!");
    }

}