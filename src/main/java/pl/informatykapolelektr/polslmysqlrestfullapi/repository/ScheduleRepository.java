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

package pl.informatykapolelektr.polslmysqlrestfullapi.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.*;
import pl.informatykapolelektr.polslmysqlrestfullapi.models.*;

import javax.transaction.*;
import java.util.*;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, String> {

    @Query("SELECT s FROM Schedule s WHERE s.day=:d")
    List<Schedule> getScheduleSubjectsByDayOfWeek(@Param("d") int day);

    @Query("SELECT s FROM Schedule s WHERE s.title=:t")
    Optional<Schedule> getScheduleSubjectByTitle(@Param("t") String title);

    @Transactional
    @Modifying
    @Query("DELETE FROM Schedule s WHERE s.title=:t")
    void deleteAllScheduleSubjectsByTitle(@Param("t") String title);

    @Query("SELECT s.updatedAt FROM Schedule s ORDER BY s.updatedAt")
    List<Date> findLastEditField();

}