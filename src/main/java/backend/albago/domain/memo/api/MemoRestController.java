package backend.albago.domain.memo.api;

import backend.albago.domain.memo.application.MemoService;
import backend.albago.domain.memo.dto.MemoRequestDTO;
import backend.albago.domain.memo.dto.MemoResponseDTO;
import backend.albago.global.common.response.BaseResponse;
import backend.albago.global.error.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/memos")
public class MemoRestController {

    private final MemoService memoService;

    @PostMapping("")
    @Operation(summary = "개인 메모 작성 API", description = "개인 스케줄 또는 팀 스케줄에 개인 메모를 작성")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "MEMO_200", description = "OK, 성공적으로 생성되었습니다.")
    })
    public BaseResponse<MemoResponseDTO.MemoCreateResult> createMemo(
            @RequestHeader(value = "member-id") String memberId,
            @RequestBody MemoRequestDTO.MemoCreateDTO request
    ) {
        MemoResponseDTO.MemoCreateResult result = memoService.createMemo(request, memberId);
        return BaseResponse.onSuccess(SuccessStatus.MEMO_CREATED, result);
    }

    @GetMapping("/me")
    @Operation(summary = "특정 날짜의 개인 메모 조회 API", description = "특정 날짜 범위의 개인 메모의 목록 조회")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "MEMO_200", description = "OK, 성공적으로 조회되었습니다.")
    })
    public BaseResponse<MemoResponseDTO.FindMemosResult> findMemos(
            @RequestHeader(value = "member-id") String memberId,
            @RequestParam(value = "starDate") LocalDate startDate,
            @RequestParam(value = "endDate") LocalDate endDate
    ) {
        MemoResponseDTO.FindMemosResult result = memoService.findMemos(startDate, endDate, memberId);
        return BaseResponse.onSuccess(SuccessStatus.MEMOS_FINED, result);
    }

    @GetMapping("/{memoId}")
    @Operation(summary = "개인 메모 조회 API", description = "개인 메모의 상세 정보 조회")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "MEMO_200", description = "OK, 성공적으로 조회되었습니다.")
    })
    public BaseResponse<MemoResponseDTO.FindMemoResult> findMemo(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "memoId") Long memoId
    ) {
        MemoResponseDTO.FindMemoResult result = memoService.findMemo(memoId, memberId);
        return BaseResponse.onSuccess(SuccessStatus.MEMO_FINED, result);
    }

    @PatchMapping("/{memoId}")
    @Operation(summary = "개인 메모 수정 API", description = "개인 메모의 상세 정보 수정")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "MEMO_200", description = "OK, 성공적으로 수정되었습니다.")
    })
    public BaseResponse<MemoResponseDTO.UpdateMemoResult> updateMemo(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "memoId") Long memoId,
            @RequestBody MemoRequestDTO.MemoUpdateDTO request
    ) {
        MemoResponseDTO.UpdateMemoResult result = memoService.updateMemo(memoId, memberId, request);
        return BaseResponse.onSuccess(SuccessStatus.MEMO_UPDATE, result);
    }

    @DeleteMapping("/{memoId}")
    @Operation(summary = "개인 메모 삭제 API", description = "개인 메모를 삭제")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "MEMO_200", description = "OK, 성공적으로 삭제되었습니다.")
    })
    public BaseResponse<Void> deleteMemo(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "memoId") Long memoId
    ) {
        memoService.deleteMemo(memoId, memberId);
        return BaseResponse.onSuccess(SuccessStatus.MEMO_DELETE, null);
    }
}
