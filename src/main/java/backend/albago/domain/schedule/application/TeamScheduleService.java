package backend.albago.domain.schedule.application;

import backend.albago.domain.schedule.dto.TeamScheduleRequestDTO;
import backend.albago.domain.schedule.dto.TeamScheduleResponseDTO;

import java.time.LocalDate;

public interface TeamScheduleService {

    TeamScheduleResponseDTO.CreateTeamScheduleResult createTeamSchedule(
            Long teamId,
            String requestMemberId,
            TeamScheduleRequestDTO.CreateTeamScheduleDTO request);

    TeamScheduleResponseDTO.FindTeamScheduleResult findTeamSchedule(
            Long teamId,
            LocalDate startDate,
            LocalDate endDate,
            String requestMemberId);

    TeamScheduleResponseDTO.UpdateTeamScheduleResult updateTeamSchedule(
            Long teamId,
            Long teamScheduleId,
            String requestMemberId,
            TeamScheduleRequestDTO.UpdateTeamScheduleDTO request);

    void deleteTeamSchedule(
            Long teamId,
            Long teamScheduleId,
            String requestMemberId);

    TeamScheduleResponseDTO.FindAllTeamScheduleResult findAllTeamSchedule(Long teamId, String requestMemberId);

}
