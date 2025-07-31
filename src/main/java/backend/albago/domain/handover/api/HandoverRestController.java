package backend.albago.domain.handover.api;

import backend.albago.domain.handover.application.HandoverService;
import backend.albago.domain.handover.dto.HandoverRequestDTO;
import backend.albago.domain.handover.dto.HandoverResponseDTO;
import backend.albago.global.common.response.BaseResponse;
import backend.albago.global.error.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
public class HandoverRestController {

    private final HandoverService handoverService;

    @PostMapping("/{teamId}/handover-notes")
    @Operation(summary = "인수인계 노트 생성 API", description = "특정 팀의 인수인계 노트를 작성")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "HANDOVER_200", description = "OK, 성공적으로 생성되었습니다.")
    })
    public BaseResponse<HandoverResponseDTO.CreateHandoverResult> createHandover(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "teamId") Long teamId,
            @RequestBody HandoverRequestDTO.CreateHandoverDTO request
    ) {
        HandoverResponseDTO.CreateHandoverResult result = handoverService.createHandover(teamId, request, memberId);
        return BaseResponse.onSuccess(SuccessStatus.HANDOVER_CREATED, result);
    }

    @GetMapping("/{teamId}/handover-notes")
    @Operation(summary = "인수인계 노트 목록 조회 API", description = "특정 팀의 인수인계 노트의 목록을 조회")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "HANDOVER_200", description = "OK, 성공적으로 조회되었습니다.")
    })
    public BaseResponse<HandoverResponseDTO.FindHandoversResult> findHandovers(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "teamId") Long teamId
    ) {
        HandoverResponseDTO.FindHandoversResult result = handoverService.findHandovers(teamId, memberId);
        return BaseResponse.onSuccess(SuccessStatus.HANDOVERS_FINED, result);
    }

    @GetMapping("/{teamId}/handover-notes/{noteId}")
    @Operation(summary = "인수인계 노트 조회 API", description = "특정 인수인계 노트를 상세 조회")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "HANDOVER_200", description = "OK, 성공적으로 조회되었습니다.")
    })
    public BaseResponse<HandoverResponseDTO.FindHandoverResult> findHandover(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "teamId") Long teamId,
            @PathVariable(value = "noteId") Long noteId
    ) {
        HandoverResponseDTO.FindHandoverResult result = handoverService.findHandover(teamId, noteId, memberId);
        return BaseResponse.onSuccess(SuccessStatus.HANDOVER_FINED, result);
    }

    @PatchMapping("/{teamId}/handover-notes/{noteId}")
    @Operation(summary = "인수인계 노트 수정 API", description = "특정 인수인계 노트를 수정")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "HANDOVER_200", description = "OK, 성공적으로 수정되었습니다.")
    })
    public BaseResponse<HandoverResponseDTO.UpdateHandoverResult> updateHandover(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "teamId") Long teamId,
            @PathVariable(value = "noteId") Long noteId,
            @RequestBody HandoverRequestDTO.UpdateHandoverDTO request
    ) {
        HandoverResponseDTO.UpdateHandoverResult result = handoverService.updateHandover(teamId, noteId, request, memberId);
        return BaseResponse.onSuccess(SuccessStatus.HANDOVER_UPDATE, result);
    }

    @DeleteMapping("/{teamId}/handover-notes/{noteId}")
    @Operation(summary = "인수인계 노트 삭제 API", description = "특정 인수인계 노트를 삭제")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "HANDOVER_200", description = "OK, 성공적으로 삭제되었습니다.")
    })
    public BaseResponse<Void> deleteHandover(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "teamId") Long teamId,
            @PathVariable(value = "noteId") Long noteId
    ) {
        handoverService.deleteHandover(teamId, noteId, memberId);
        return BaseResponse.onSuccess(SuccessStatus.HANDOVER_DELETE, null);
    }

}
