package backend.albago.domain.shift.api;

import backend.albago.domain.shift.application.ShiftService;
import backend.albago.domain.shift.dto.ShiftRequestDTO;
import backend.albago.domain.shift.dto.ShiftResponseDTO;
import backend.albago.domain.team.dto.TeamRequestDTO;
import backend.albago.domain.team.dto.TeamResponseDTO;
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
public class ShiftRestController {

    private final ShiftService shiftService;

    @PostMapping("/{teamId}/shifts/clock-in")
    @Operation(summary = "출근 기록 API", description = "팀에서의 출근을 기록")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "SHIFT_200", description = "OK, 성공적으로 생성되었습니다.")
    })
    public BaseResponse<ShiftResponseDTO.ClockInResult> clockIn(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "team-id") Long teamId,
            @RequestBody ShiftRequestDTO.ClockInDTO request
    ) {
        ShiftResponseDTO.ClockInResult result = shiftService.clockIn(teamId, memberId, request);
        return BaseResponse.onSuccess(SuccessStatus.CLOCK_IN, result);
    }

    @PatchMapping("/{teamId}/shifts/clock-out")
    @Operation(summary = "퇴근 기록 API", description = "팀에서의 퇴근을 기록하고 근무를 종료합니다. GPS 기반 위치 검증을 수행합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "SHIFT_200", description = "OK, 성공적으로 생성되었습니다.")
    })
    public BaseResponse<ShiftResponseDTO.ClockOutResult> clockOut(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "teamId") Long teamId,
            @RequestBody ShiftRequestDTO.ClockOutDTO request
    ) {
        ShiftResponseDTO.ClockOutResult result = shiftService.clockOut(teamId, memberId, request);
        return BaseResponse.onSuccess(SuccessStatus.CLOCK_OUT, result);
    }

    @GetMapping("{teamId}/shifts/me")
    @Operation(summary = "기간 별 개인 근무 기록 조회 API", description = "특정 팀에서의 개인 근무 기록 기간 별 조회")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "SHIFT_200", description = "OK, 성공적으로 조회되었습니다.")
    })
    public BaseResponse<ShiftResponseDTO.findShiftsResult> findShifts(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "teamId") Long teamId,
            @RequestParam(value = "startDate")LocalDate startDate,
            @RequestParam(value = "endDate") LocalDate endDate
            ) {
        ShiftResponseDTO.findShiftsResult result = shiftService.findShifts(teamId, memberId, startDate, endDate);
        return BaseResponse.onSuccess(SuccessStatus.GET_SHIFT_LOGS, result);
    }

    @GetMapping("{teamId}/shifts")
    @Operation(summary = "기간 별 팀 근무 기록 조회 API", description = "특정 팀의 근무 기록 기간 별 조회")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "SHIFT_200", description = "OK, 성공적으로 조회되었습니다.")
    })
    public BaseResponse<ShiftResponseDTO.findTeamShiftsResult> findTeamShifts(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "teamId") Long teamId,
            @RequestParam(value = "startDate")LocalDate startDate,
            @RequestParam(value = "endDate") LocalDate endDate
    ) {
        ShiftResponseDTO.findTeamShiftsResult result = shiftService.findTeamShifts(teamId, memberId, startDate, endDate);
        return BaseResponse.onSuccess(SuccessStatus.GET_SHIFT_LOGS, result);
    }
}
