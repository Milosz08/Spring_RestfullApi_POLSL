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

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import pl.informatykapolelektr.polslmysqlrestfullapi.models.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.service.*;

import javax.validation.*;

import static pl.informatykapolelektr.polslmysqlrestfullapi.utils.ServletConfig.*;

import java.util.*;

@RestController
@RequestMapping(DEF_PREFIX + CREDENTIALS)
@CrossOrigin
public class UserAuthController {

    @Autowired
    private UserAuthService usersService;

    @GetMapping
    public ResponseEntity<List<UserAuth>> getAllUsers() {
        return new ResponseEntity<>(usersService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> validateCredentials(@Valid @RequestBody UserAuth userAuth) {
        return usersService.validateCredentials(userAuth);
    }

    @PostMapping("/authenticate/user")
    public ResponseEntity<?> validateCredentialsUser(@Valid @RequestBody UserAuth userAuth) {
        return usersService.validateCredentialsUser(userAuth);
    }

    @GetMapping("/{role}")
    public ResponseEntity<UserAuth> getSingleUser(@PathVariable int role) {
        return new ResponseEntity<>(usersService.getSingleUser(role), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserAuth> addUser(@Valid @RequestBody UserAuth userAuth) {
        return new ResponseEntity<>(usersService.addUser(userAuth), HttpStatus.CREATED);
    }

    @PutMapping("/{role}")
    public ResponseEntity<UserAuth> updateUser(@PathVariable int role, @RequestBody UserAuth userAuth) {
        return new ResponseEntity<>(usersService.editUser(role, userAuth), HttpStatus.OK);
    }

}