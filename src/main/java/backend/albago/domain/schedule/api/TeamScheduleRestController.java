package backend.albago.domain.schedule.api;

import backend.albago.domain.schedule.application.TeamScheduleService;
import backend.albago.domain.schedule.dto.TeamScheduleRequestDTO;
import backend.albago.domain.schedule.dto.TeamScheduleResponseDTO;
import backend.albago.global.common.response.BaseResponse;
import backend.albago.global.error.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
public class TeamScheduleRestController {

    private final TeamScheduleService teamScheduleService;

    @PostMapping("/{teamId}/schedules")
    @Operation(summary = "팀 스케줄 생성 API", description = "특정 팀의 스케줄을 생성")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "OK, 성공적으로 생성되었습니다.")
    })
    public BaseResponse<TeamScheduleResponseDTO.CreateTeamScheduleResult> createTeamSchedule(
            @PathVariable(value = "teamId") Long teamId,
            @RequestHeader(value = "member-id") String requestMemberId,
            @RequestBody TeamScheduleRequestDTO.CreateTeamScheduleDTO request
    ) {
        TeamScheduleResponseDTO.CreateTeamScheduleResult result = teamScheduleService.createTeamSchedule(teamId, requestMemberId, request);
        return BaseResponse.onSuccess(SuccessStatus.TEAM_SCHEDULE_CREATED, result);
    }

    @GetMapping("/{teamId}/schedules")
    @Operation(summary = "팀 스케줄 기간 조회 API", description = "특정 팀의 스케줄 중 특정 기간 조회")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공적으로 조회되었습니다.")
    })
    public BaseResponse<TeamScheduleResponseDTO.FindTeamScheduleResult> findTeamSchedule(
            @PathVariable(value = "teamId") Long teamId,
            @RequestHeader(value = "member-id") String requestMemberId,
            @RequestParam(value = "startDate") LocalDate startDate,
            @RequestParam(value = "endDate") LocalDate endDate
    ) {
        TeamScheduleResponseDTO.FindTeamScheduleResult result = teamScheduleService.findTeamSchedule(teamId, startDate, endDate, requestMemberId);
        return BaseResponse.onSuccess(SuccessStatus.TEAM_SCHEDULE_FOUND, result);
    }

    @PatchMapping("/{teamId}/schedules/{teamScheduleId}")
    @Operation(summary = "팀 스케줄 수정 API", description = "특정 팀의 스케줄 수정")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공적으로 수정되었습니다.")
    })
    public BaseResponse<TeamScheduleResponseDTO.UpdateTeamScheduleResult> updateTeamSchedule(
            @PathVariable(value = "teamId") Long teamId,
            @PathVariable(value = "teamScheduleId") Long teamScheduleId,
            @RequestHeader(value = "member-id") String requestMemberId,
            @RequestBody TeamScheduleRequestDTO.UpdateTeamScheduleDTO request
    ) {
        TeamScheduleResponseDTO.UpdateTeamScheduleResult result = teamScheduleService.updateTeamSchedule(teamId, teamScheduleId, requestMemberId, request);
        return BaseResponse.onSuccess(SuccessStatus.TEAM_SCHEDULE_UPDATE, result);
    }

    @DeleteMapping("/{teamId}/schedules/{teamScheduleId}")
    @Operation(summary = "팀 스케줄 삭제 API", description = "특정 팀의 스케줄 삭제")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공적으로 삭제되었습니다.")
    })
    public BaseResponse<Void> deleteTeamSchedule(
            @PathVariable(value = "teamId") Long teamId,
            @PathVariable(value = "teamScheduleId") Long teamScheduleId,
            @RequestHeader(value = "member-id") String requestMemberId
    ) {
        teamScheduleService.deleteTeamSchedule(teamId, teamScheduleId, requestMemberId);
        return BaseResponse.onSuccess(SuccessStatus.TEAM_SCHEDULE_DELETE, null);
    }
}
