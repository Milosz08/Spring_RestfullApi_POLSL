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
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.stereotype.*;
import org.springframework.security.crypto.password.*;

import pl.informatykapolelektr.polslmysqlrestfullapi.exceptions.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.models.UserAuth;
import pl.informatykapolelektr.polslmysqlrestfullapi.repository.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.service.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.utils.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.utils.jwt.*;

@Service
public class UserAuthServiceImplementation implements UserAuthService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserAuthRepository usersRepository;
    @Autowired
    private LastUpdateService lastUpdateService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private DbUserDetailsService dbUserDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    public UserAuthServiceImplementation() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public List<UserAuth> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public UserAuth getSingleUser(int role) {
        Optional<UserAuth> findUserByRole = usersRepository.findUserByRole(role);
        if (findUserByRole.isPresent()) {
            return findUserByRole.get();
        }
        throw new ApiRequestException("Użytkownik z rolą: '" + role + "' nie znajduje się w bazie danych");
    }

    @Override
    public UserAuth addUser(UserAuth userAuth) {
        lastUpdateService.updateSelectedSection(Enums.AllUpdateTypes.AUTH);
        return usersRepository.save(encodeFields(userAuth));
    }

    @Override
    public ResponseEntity<Map<String, Object>> validateCredentials(UserAuth userAuth) {
        Optional<UserAuth> findUserByUsername = usersRepository.findUserAuthByUsername(userAuth.getUsername());
        if (findUserByUsername.isPresent()) {
            UserAuth findUserAuthValid = findUserByUsername.get();
            int role = findUserAuthValid.getRole();
            if (role <= 1) {
                return new ResponseEntity<>(ReturnedJsonContent.returnedCredentialsNotFound(), HttpStatus.NOT_FOUND);
            }
            List<Boolean> valid = validateAllFields(userAuth, findUserAuthValid);
            if (valid.stream().allMatch(item -> item)) {
                String jwt = "";
                if (role == 2 || role == 3) {
                    final UserDetails userDetails = dbUserDetailsService.loadUserByUsername(userAuth.getUsername());
                    jwt = jwtUtil.generateToken(userDetails);
                }
                findUserAuthValid.setLastLogged(new ServletTime(LocalDateTime.now()).getFullDate());
                usersRepository.save(findUserAuthValid);
                return new ResponseEntity<>(
                        ReturnedJsonContent.returnedCredentials(valid, role, false, jwt), HttpStatus.OK
                );
            }
            return new ResponseEntity<>(
                    ReturnedJsonContent.returnedCredentials(valid, role, true, ""), HttpStatus.UNAUTHORIZED
            );
        }
        return new ResponseEntity<>(ReturnedJsonContent.returnedCredentialsNotFound(), HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Map<String, Object>> validateCredentialsUser(UserAuth userAuth) {
        Optional<UserAuth> findUserByRole = usersRepository.findUserByRole(1);
        if (findUserByRole.isPresent()) {
            List<Boolean> valid = validateAllFields(userAuth, findUserByRole.get());
            if (valid.stream().allMatch(item -> item)) {
                return new ResponseEntity<>(
                    ReturnedJsonContent.returnedCredentials(valid, 1, false, ""), HttpStatus.OK
                );
            }
            return new ResponseEntity<>(
                ReturnedJsonContent.returnedCredentials(valid, 1, true, ""), HttpStatus.UNAUTHORIZED
            );
        }
        throw new ApiRequestException("Użytkownik z nie znajduje się w bazie danych");
    }

    @Override
    public UserAuth editUser(int role, UserAuth userAuth) {
        Optional<UserAuth> findUserByRole = usersRepository.findUserByRole(role);
        if (findUserByRole.isPresent()) {
            userAuth.set_id(findUserByRole.get().get_id());
            lastUpdateService.updateSelectedSection(Enums.AllUpdateTypes.AUTH);
            return usersRepository.save(encodeFields(userAuth));
        }
        throw new ApiRequestException("Użytkownik z rolą: '" + role + "' nie znajduje się w bazie danych");
    }

    private List<Boolean> validateAllFields(UserAuth userAuth, UserAuth findUserAuth) {
        List<Boolean> returnList = new ArrayList<>();
        returnList.add(userAuth.getUsername().equals(findUserAuth.getUsername()));
        returnList.add(BCrypt.checkpw(userAuth.getPassword(), findUserAuth.getPassword()));
        returnList.add(BCrypt.checkpw(userAuth.getToken(), findUserAuth.getToken()));
        return returnList;
    }

    private UserAuth encodeFields(UserAuth userAuth) {
        userAuth.setPassword(passwordEncoder.encode(userAuth.getPassword()));
        userAuth.setToken(passwordEncoder.encode(userAuth.getToken()));
        userAuth.setLastLogged(new ServletTime(LocalDateTime.now()).getFullDate());
        return userAuth;
    }
}