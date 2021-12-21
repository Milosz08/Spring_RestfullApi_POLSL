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

import static pl.informatykapolelektr.polslmysqlrestfullapi.utils.ReturnedNoContent.returnedNoContent;
import static pl.informatykapolelektr.polslmysqlrestfullapi.utils.ServletConfig.*;

import java.util.*;

@RestController
@RequestMapping(DEF_PREFIX + CREDENTIALS)
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(usersService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{role}")
    public ResponseEntity<User> getSingleUser(@PathVariable int role) {
        return new ResponseEntity<>(usersService.getSingleUser(role), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        return new ResponseEntity<>(usersService.addUser(user), HttpStatus.CREATED);
    }

    @PutMapping("/{role}")
    public ResponseEntity<User> updateUser(@PathVariable int role, @RequestBody User user) {
        return new ResponseEntity<>(usersService.editUser(role, user), HttpStatus.OK);
    }
}