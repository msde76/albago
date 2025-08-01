package backend.albago.domain.schedule.api;

import backend.albago.domain.schedule.application.PersonalScheduleService;
import backend.albago.domain.schedule.dto.PersonalScheduleRequestDTO;
import backend.albago.domain.schedule.dto.PersonalScheduleResponseDTO;
import backend.albago.global.common.response.BaseResponse;
import backend.albago.global.error.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class PersonalScheduleRestController {

    private final PersonalScheduleService personalScheduleService;

    @PostMapping("/personal")
    @Operation(summary = "개인 스케줄 생성 API", description = "사용자의 개인 스케줄을 생성")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "SCHEDULE_200", description = "OK, 성공적으로 생성되었습니다.")
    })
    public BaseResponse<PersonalScheduleResponseDTO.CreatePersonalScheduleResult> createPersonalSchedule(
            @RequestHeader(value = "member-id") String memberId,
            @RequestBody PersonalScheduleRequestDTO.CreatePersonalScheduleDTO request
    ) {
        PersonalScheduleResponseDTO.CreatePersonalScheduleResult result = personalScheduleService.createPersonalSchedule(request, memberId);
        return BaseResponse.onSuccess(SuccessStatus.PERSONAL_SCHEDULE_CREATED, result);
    }

    @GetMapping("personal")
    @Operation(summary = "개인 스케줄 기간 조회 API", description = "사용자의 개인 스케줄 중 특정 기간 조회")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "SCHEDULE_201", description = "OK, 성공적으로 조회되었습니다.")
    })
    public BaseResponse<PersonalScheduleResponseDTO.FindPersonalScheduleResult> findPersonalSchedule(
            @RequestHeader(value = "member-id") String memberId,
            @RequestParam(value = "starDate")LocalDate startDate,
            @RequestParam(value = "endDate") LocalDate endDate
            ) {
        PersonalScheduleResponseDTO.FindPersonalScheduleResult result = personalScheduleService.findPersonalSchedule(startDate, endDate, memberId);
        return BaseResponse.onSuccess(SuccessStatus.PERSONAL_SCHEDULE_FOUND, result);
    }

    @PatchMapping("personal/{scheduleId}")
    @Operation(summary = "개인 스케줄 수정 API", description = "사용자의 개인 스케줄 수정")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "SCHEDULE_202", description = "OK, 성공적으로 수정되었습니다.")
    })
    public BaseResponse<PersonalScheduleResponseDTO.UpdatePersonalScheduleResult> updatePersonalSchedule(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "scheduleId") Long scheduleId,
            @RequestBody PersonalScheduleRequestDTO.UpdatePersonalScheduleDTO request
    ) {
        PersonalScheduleResponseDTO.UpdatePersonalScheduleResult result = personalScheduleService.updatePersonalSchedule(request, scheduleId, memberId);
        return BaseResponse.onSuccess(SuccessStatus.PERSONAL_SCHEDULE_UPDATE, result);
    }

    @DeleteMapping("/personal/{scheduleId}")
    @Operation(summary = "개인 스케줄 삭제 API", description = "사용자의 개인 스케줄 삭제")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "SCHEDULE_203", description = "OK, 성공적으로 삭제되었습니다.")
    })
    public BaseResponse<Void> deletePersonalSchedule(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "scheduleId") Long scheduleId
    ) {
        personalScheduleService.deletePersonalSchedule(scheduleId, memberId);
        return BaseResponse.onSuccess(SuccessStatus.PERSONAL_SCHEDULE_DELETE, null);
    }

    @GetMapping("/personal/schedules")
    @Operation(summary = "모든 개인 스케줄 조회 API", description = "사용자의 모든 개인 스케줄 조회")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "SCHEDULE_204", description = "OK, 성공적으로 수정되었습니다.")
    })
    public BaseResponse<PersonalScheduleResponseDTO.FindAllPersonalScheduleResult> findAllPersonalSchedule(
            @RequestHeader(value = "member-id") String memberId
    ) {
        PersonalScheduleResponseDTO.FindAllPersonalScheduleResult result = personalScheduleService.findAllPersonalSchedule(memberId);
        return BaseResponse.onSuccess(SuccessStatus.PERSONAL_SCHEDULE_ALL_FOUND, result);
    }
}
