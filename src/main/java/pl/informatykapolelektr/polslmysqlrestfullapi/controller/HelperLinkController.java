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
@RequestMapping(DEF_PREFIX + HELPER_LINKS)
@CrossOrigin
public class HelperLinkController {

    private final HelperLinkService helperLinkService;

    public HelperLinkController(HelperLinkService helperLinkService) {
        this.helperLinkService = helperLinkService;
    }

    @GetMapping
    public ResponseEntity<List<HelperLink>> getAllHelperLinks() {
        return new ResponseEntity<>(helperLinkService.getAllHelperLinks(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HelperLink> getSingleHelperLink(@PathVariable String id) {
        return new ResponseEntity<>(helperLinkService.getSingleHelperLink(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HelperLink> addHelperLink(@Valid @RequestBody HelperLink helperLink) {
        return new ResponseEntity<>(helperLinkService.addHelperLink(helperLink), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HelperLink> editHelperLink(@PathVariable String id, @Valid @RequestBody HelperLink helperLink) {
        return new ResponseEntity<>(helperLinkService.editHelperLink(id, helperLink), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSingleHelperLink(@PathVariable String id) {
        helperLinkService.deleteSingleHelperLink(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllHelperLinks() {
        helperLinkService.deleteAllHelperLink();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}