package backend.albago.domain.member.api;

import backend.albago.domain.member.application.MemberService;
import backend.albago.domain.member.dto.MemberRequestDTO;
import backend.albago.domain.member.dto.MemberResponseDTO;
import backend.albago.global.common.response.BaseResponse;
import backend.albago.global.error.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberRestController {

    private final MemberService memberService;

    @GetMapping("")
    @Operation(summary = "멤버 조회 API", description = "가입된 멤버 조회")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "MEMBER_200", description = "OK, 성공적으로 조회되었습니다.")
    })
    public BaseResponse<MemberResponseDTO.FindMemberResult> findMember(
            @RequestHeader(value = "member-id") String memberId
    ) {
        MemberResponseDTO.FindMemberResult result = memberService.findMember(memberId);
        return BaseResponse.onSuccess(SuccessStatus.MEMBER_FOUND, result);
    }

    @PostMapping("")
    @Operation(summary = "멤버 생성 API", description = "회원가입 시 멤버 생성")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "MEMBER_201", description = "OK, 성공적으로 생성되었습니다.")
    })
    public BaseResponse<MemberResponseDTO.CreateMemberResult> createMember(
            @RequestHeader(value = "member-id") String memberId,
            @RequestBody MemberRequestDTO.CreateMemberDTO request
    ) {
        MemberResponseDTO.CreateMemberResult result = memberService.createMember(memberId, request);
        return BaseResponse.onSuccess(SuccessStatus.MEMBER_CREATED, result);
    }

    @PatchMapping("/me")
    @Operation(summary = "회원 정보 수정 API", description = "회원 정보 수정")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "MEMBER_202", description = "OK, 성공적으로 수정되었습니다.")
    })
    public BaseResponse<MemberResponseDTO.UpdateMemberResult> updateMember(
            @RequestHeader(value = "member-id") String memberId,
            @RequestBody MemberRequestDTO.UpdateMemberDTO request
    ) {
        MemberResponseDTO.UpdateMemberResult result = memberService.updateMember(memberId, request);
        return BaseResponse.onSuccess(SuccessStatus.MEMBER_UPDATE, result);
    }

    @DeleteMapping("/me")
    @Operation(summary = "멤버 탈퇴 API", description = "회원 탈퇴")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "MEMBER_203", description = "OK, 성공적으로 삭제되었습니다.")
    })
    public BaseResponse<Void> deleteMember(
            @RequestHeader(value = "member-id") String memberId
    ) {
        memberService.deleteMember(memberId);
        return BaseResponse.onSuccess(SuccessStatus.MEMBER_DELETE, null);
    }

    @GetMapping("/me")
    @Operation(summary = "자신의 정보 조회 API", description = "가입된 자신의 개인 정보를 조회")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "MEMBER_204", description = "OK, 성공적으로 조회되었습니다.")
    })
    public BaseResponse<MemberResponseDTO.FindMeResult> findMe(
            @RequestHeader(value = "member-id") String memberId
    ) {
        MemberResponseDTO.FindMeResult result = memberService.findMe(memberId);
        return BaseResponse.onSuccess(SuccessStatus.ME_FOUND, result);
    }
}
