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

import org.springframework.security.crypto.bcrypt.*;
import org.springframework.stereotype.*;
import org.springframework.security.crypto.password.*;

import pl.informatykapolelektr.polslmysqlrestfullapi.models.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.repository.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.service.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.utils.*;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImplementation(UserRepository usersRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public User getSingleUser(int role) {
        Optional<User> user = usersRepository.findByRole(role);
        if(user.isPresent()) {
            return user.get();
        }
        throw new RuntimeException("User not found for the role: " + role);
    }

    @Override
    public User addUser(User user) {
        return usersRepository.save(encodeFields(user));
    }

    @Override
    public User editUser(int role, User user) {
        Optional<User> userUpdate = usersRepository.findByRole(role);
        if(userUpdate.isPresent()) {
            user.set_id(userUpdate.get().get_id());
            return usersRepository.save(encodeFields(user));
        }
        throw new RuntimeException("User not found for the role: " + role);
    }

    private User encodeFields(User user) {
        user.setUsername(passwordEncoder.encode(user.getUsername()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setToken(passwordEncoder.encode(user.getToken()));
        user.setLastLogged(new ServletTime(LocalDateTime.now()).getFullDate());
        return user;
    }
}