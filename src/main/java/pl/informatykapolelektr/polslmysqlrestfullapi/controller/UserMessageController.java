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

import java.util.*;

import static pl.informatykapolelektr.polslmysqlrestfullapi.utils.ServletConfig.*;

@RestController
@RequestMapping(DEF_PREFIX + USER_MESSAGES)
@CrossOrigin
public class UserMessageController {

    private final UserMessageService userMessageService;

    public UserMessageController(UserMessageService userMessageService) {
        this.userMessageService = userMessageService;
    }

    @GetMapping
    public ResponseEntity<List<UserMessage>> getAllUserMessages() {
        return new ResponseEntity<>(userMessageService.getAllUserMessages(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserMessage> addUserMessage(@RequestBody UserMessage userMessage) {
        return new ResponseEntity<>(userMessageService.addUserMessage(userMessage), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserMessage> editUserMessage(@PathVariable String id) {
        return new ResponseEntity<>(userMessageService.editUserMessage(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserMessage(@PathVariable String id) {
        userMessageService.deleteSingleUserMessage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllUserMessages() {
        userMessageService.deleteAllUserMessages();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}