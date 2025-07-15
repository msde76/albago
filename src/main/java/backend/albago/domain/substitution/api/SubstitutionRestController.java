package backend.albago.domain.substitution.api;

import backend.albago.domain.substitution.application.SubstitutionService;
import backend.albago.domain.substitution.dto.SubstitutionRequestDTO;
import backend.albago.domain.substitution.dto.SubstitutionResponseDTO;
import backend.albago.global.common.response.BaseResponse;
import backend.albago.global.error.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
public class SubstitutionRestController {

    private final SubstitutionService substitutionService;

    @PostMapping("/{teamId}/substitution-requests")
    @Operation(summary = "대타 요청 생성 API", description = "특정 팀 내에서의 근무 대타 요청을 생성")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "SUBSTITUTION_200", description = "OK, 성공적으로 생성되었습니다.")
    })
    public BaseResponse<SubstitutionResponseDTO.CreateSubstitutionResult> createSubstitution(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "teamId") Long teamId,
            @RequestBody SubstitutionRequestDTO.CreateSubstitutionDTO request
    ) {
        SubstitutionResponseDTO.CreateSubstitutionResult result = substitutionService.createSubstitution(teamId, request, memberId);
        return BaseResponse.onSuccess(SuccessStatus.SUBSTITUTION_CREATE, result);
    }

    @GetMapping("/{teamId}/substitution-requests/me/requested")
    @Operation(summary = "대타 요청한 목록 조회 API", description = "특정 팀 내에서의 사용자가 요청한 근무 대타 요청 목록을 조회")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "SUBSTITUTION_200", description = "OK, 성공적으로 조회되었습니다.")
    })
    public BaseResponse<SubstitutionResponseDTO.FindRequestSubstitutionResult> findRequestSubstitution(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "teamId") Long teamId
    ) {
        SubstitutionResponseDTO.FindRequestSubstitutionResult result = substitutionService.findRequestSubstitution(teamId, memberId);
        return BaseResponse.onSuccess(SuccessStatus.SUBSTITUTION_REQUEST_FINED, result);
    }

    @GetMapping("/{teamId}/substitution-requests/me/received")
    @Operation(summary = "대타 요청된 목록 조회 API", description = "특정 팀 내에서의 사용자에게 요청된 근무 대타 요청 목록을 조회")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "SUBSTITUTION_200", description = "OK, 성공적으로 조회되었습니다.")
    })
    public BaseResponse<SubstitutionResponseDTO.FindReceiveSubstitutionResult> findReceiveSubstitution(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "teamId") Long teamId
    ) {
        SubstitutionResponseDTO.FindReceiveSubstitutionResult result = substitutionService.findReceiveSubstitution(teamId, memberId);
        return BaseResponse.onSuccess(SuccessStatus.SUBSTITUTION_RECEIVE_FINED, result);
    }

    @GetMapping("/{teamId}/substitution-requests/{requestId}")
    @Operation(summary = "대타 요청의 상세 정보 조회 API", description = "특정 팀 내에서의 사용자의 대타 요청의 상세 정보 조회")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "SUBSTITUTION_200", description = "OK, 성공적으로 조회되었습니다.")
    })
    public BaseResponse<SubstitutionResponseDTO.FindSubstitutionResult> findSubstitution(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "teamId") Long teamId,
            @PathVariable(value = "requestId") Long requestId
    ) {
        SubstitutionResponseDTO.FindSubstitutionResult result = substitutionService.findSubstitution(teamId, requestId, memberId);
        return BaseResponse.onSuccess(SuccessStatus.SUBSTITUTION_FINED, result);
    }

    @PatchMapping("/{teamId}/substitution-requests/{requestId}/accept")
    @Operation(summary = "대타 요청 수락 API", description = "특정 팀 내에서의 사용자에게 요청된 근무 대타 요청을 수락")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "SUBSTITUTION_200", description = "OK, 성공적으로 수락되었습니다.")
    })
    public BaseResponse<SubstitutionResponseDTO.AcceptSubstitutionResult> acceptSubstitution(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "teamId") Long teamId,
            @PathVariable(value = "requestId") Long requestId
    ) {
        SubstitutionResponseDTO.AcceptSubstitutionResult result = substitutionService.acceptSubstitution(teamId, requestId, memberId);
        return BaseResponse.onSuccess(SuccessStatus.SUBSTITUTION_ACCEPT, result);
    }

    @PatchMapping("/{teamId}/substitution-requests/{requestId}/reject")
    @Operation(summary = "대타 요청 거절 API", description = "특정 팀 내에서의 사용자에게 요청된 근무 대타 요청을 거절")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "SUBSTITUTION_200", description = "OK, 성공적으로 거절되었습니다.")
    })
    public BaseResponse<SubstitutionResponseDTO.RejectSubstitutionResult> rejectSubstitution(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "teamId") Long teamId,
            @PathVariable(value = "requestId") Long requestId
    ) {
        SubstitutionResponseDTO.RejectSubstitutionResult result = substitutionService.rejectSubstitution(teamId, requestId, memberId);
        return BaseResponse.onSuccess(SuccessStatus.SUBSTITUTION_REJECT, result);
    }

    @DeleteMapping("/{teamId}/substitution-requests/{requestId}")
    @Operation(summary = "대타 요청 취소 API", description = "특정 팀 내에서의 사용자에게 요청된 근무 대타 요청을 취소")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "SUBSTITUTION_200", description = "OK, 성공적으로 삭제되었습니다.")
    })
    public BaseResponse<Void> deleteSubstitution(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "teamId") Long teamId,
            @PathVariable(value = "requestId") Long requestId
    ) {
        substitutionService.deleteSubstitution(teamId, requestId, memberId);
        return BaseResponse.onSuccess(SuccessStatus.SUBSTITUTION_DELETE, null);
    }

    @GetMapping("/{teamId}/substitution-requests/check-availability")
    @Operation(summary = "대타 가능 멤버 조회 API", description = "특정 팀 내에서 지정된 시간에 대타 가능한 멤버 목록을 조회")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "SUBSTITUTION_200", description = "OK, 성공적으로 조회되었습니다.")
    })
    public BaseResponse<SubstitutionResponseDTO.CheckAvailabilityResult> checkAvailability(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "teamId") Long teamId,
            @RequestBody SubstitutionRequestDTO.CheckAvailabilityDTO request
    ) {
        SubstitutionResponseDTO.CheckAvailabilityResult result = substitutionService.checkAvailability(teamId, request, memberId);
        return BaseResponse.onSuccess(SuccessStatus.SUBSTITUTION_AVAILABILITY_CHECKED, result);
    }

}
