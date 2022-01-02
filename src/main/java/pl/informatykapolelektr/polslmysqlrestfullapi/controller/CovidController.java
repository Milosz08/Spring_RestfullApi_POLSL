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
import pl.informatykapolelektr.polslmysqlrestfullapi.utils.*;

import javax.validation.*;
import java.util.*;

import static pl.informatykapolelektr.polslmysqlrestfullapi.utils.ServletConfig.*;

@RestController
@RequestMapping(DEF_PREFIX + COVID)
@CrossOrigin
public class CovidController {

    @Autowired
    private CovidService covidService;

    @GetMapping
    public ResponseEntity<List<Covid>> getAllCovidData() {
        return new ResponseEntity<>(covidService.getAllCovidData(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Covid> addCovidData(@Valid @RequestBody Covid covid) {
        return new ResponseEntity<>(covidService.addCovidData(covid), HttpStatus.CREATED);
    }

    @PutMapping("/{type}")
    public ResponseEntity<Covid> updateCovidData(@PathVariable Enums.CovidDataEnums type, @RequestBody Covid covid) {
        return new ResponseEntity<>(covidService.editCovidData(type, covid), HttpStatus.OK);
    }

}