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

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.exceptions.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.models.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.repository.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.service.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.utils.*;

import java.time.*;
import java.util.*;

@Service
public class UserMessageServiceImplementation implements UserMessageService {

    @Autowired
    private UserMessageRepository userMessageRepository;

    @Override
    public List<UserMessage> getAllUserMessages() {
        return userMessageRepository.findAll();
    }

    @Override
    public UserMessage addUserMessage(UserMessage userMessage) {
        userMessage.setServletTime(new ServletTime(LocalDateTime.now()).getFullDate());
        return userMessageRepository.save(userMessage);
    }

    @Override
    public UserMessage editUserMessage(String id) {
        Optional<UserMessage> updateUserMessage = userMessageRepository.findById(id);
        if(updateUserMessage.isPresent()) {
            updateUserMessage.get().setIfClicked(true);
            updateUserMessage.get().setServletTime(new ServletTime(LocalDateTime.now()).getFullDate());
            return userMessageRepository.save(updateUserMessage.get());
        }
        throw new ApiRequestException("Wiadomość o ID: " + id + " nie znajduje się w bazie danych!");
    }

    @Override
    public void deleteSingleUserMessage(String id) {
        userMessageRepository.deleteById(id);
    }

    @Override
    public void deleteAllUserMessages() {
        userMessageRepository.deleteAll();
    }

}