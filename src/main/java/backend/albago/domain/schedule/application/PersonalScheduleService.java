package backend.albago.domain.schedule.application;

import backend.albago.domain.schedule.dto.PersonalScheduleRequestDTO;
import backend.albago.domain.schedule.dto.PersonalScheduleResponseDTO;

import java.time.LocalDate;

public interface PersonalScheduleService {

    PersonalScheduleResponseDTO.CreatePersonalScheduleResult createPersonalSchedule(PersonalScheduleRequestDTO.CreatePersonalScheduleDTO request, String memberId);

    PersonalScheduleResponseDTO.FindPersonalScheduleResult findPersonalSchedule(LocalDate startDate, LocalDate endDate, String memberId);

    PersonalScheduleResponseDTO.UpdatePersonalScheduleResult updatePersonalSchedule(PersonalScheduleRequestDTO.UpdatePersonalScheduleDTO request, Long scheduleId, String memberId);

    void deletePersonalSchedule(Long scheduleId, String memberId);

}
