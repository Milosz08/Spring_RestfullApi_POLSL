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

import java.util.*;
import org.springframework.stereotype.*;

import pl.informatykapolelektr.polslmysqlrestfullapi.exceptions.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.models.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.repository.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.service.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.utils.*;

@Service
public class HelperLinkServiceImplementation implements HelperLinkService {

    private final HelperLinkRepository helperLinkRepository;
    private final IconRepository iconRepository;

    public HelperLinkServiceImplementation(HelperLinkRepository helperLinkRepository, IconRepository iconRepository) {
        this.helperLinkRepository = helperLinkRepository;
        this.iconRepository = iconRepository;
    }

    private HelperLink addOrUpdate(HelperLink helperLink) {
        helperLink.getHelperIcon().set_id(new RandomHexGenerator().generateSequence());
        helperLinkRepository.save(helperLink);
        iconRepository.save(helperLink.getHelperIcon());
        return helperLink;
    }

    @Override
    public List<HelperLink> getAllHelperLinks() {
        return helperLinkRepository.findAll();
    }

    @Override
    public HelperLink getSingleHelperLink(String id) {
        Optional<HelperLink> helperLinkFind = helperLinkRepository.findById(id);
        if(helperLinkFind.isPresent()) {
            return helperLinkFind.get();
        }
        throw new ApiRequestException("Link pomocy o ID: " + id + " nie znajduje się w bazie danych!");
    }

    @Override
    public HelperLink addHelperLink(HelperLink helperLink) {
        return addOrUpdate(helperLink);
    }

    @Override
    public HelperLink editHelperLink(String id, HelperLink helperLink) {
        Optional<HelperLink> helperLinkFind = helperLinkRepository.findById(id);
        helperLink.set_id(id);
        if (helperLinkFind.isPresent()) {
            return addOrUpdate(helperLink);
        }
        throw new ApiRequestException("Link pomocy o ID: " + id + " nie znajduje się w bazie danych!");
    }

    @Override
    public void deleteSingleHelperLink(String id) {
        helperLinkRepository.deleteById(id);
    }

    @Override
    public void deleteAllHelperLink() {
        helperLinkRepository.deleteAll();
    }

}