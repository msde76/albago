package backend.albago.domain.team.api;

import backend.albago.domain.team.application.TeamPostService;
import backend.albago.domain.team.application.TeamService;
import backend.albago.domain.team.dto.TeamRequestDTO;
import backend.albago.domain.team.dto.TeamResponseDTO;
import backend.albago.global.common.response.BaseResponse;
import backend.albago.global.error.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
public class TeamRestController {

    private final TeamService teamService;
    //private final TeamPostService teamPostService;

    @PostMapping
    @Operation(summary = "팀을 생성 API", description = "query string으로 JWT 토큰과 팀 생성에 필요한 정보를 넘겨주세요.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "TEAM_200", description = "OK, 성공적으로 생성되었습니다.")
    })
    public BaseResponse<TeamResponseDTO.TeamCreateResult> createTeam(
            @RequestHeader(value = "member-id") String memberId,
            @RequestBody TeamRequestDTO.TeamCreateDTO request
    ) {
        TeamResponseDTO.TeamCreateResult result = teamService.createTeam(request, memberId);
        return BaseResponse.onSuccess(SuccessStatus.TEAM_CREATED, result);
    }

    @GetMapping("/me")
    @Operation(summary = "팀을 목록 조회 API", description = "사용자의 팀 목록 조회")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "TEAM_201", description = "OK, 성공적으로 조회되었습니다.")
    })
    public BaseResponse<List<TeamResponseDTO.TeamListResult>> getTeams(
            @RequestHeader(value = "member-id") String memberId
    ) {
        List<TeamResponseDTO.TeamListResult> result = teamService.getTeams(memberId);
        return BaseResponse.onSuccess(SuccessStatus.TEAM_LIST_FINED, result);
    }

    @GetMapping("/{teamId}")
    @Operation(summary = "특정 팀 조회 API", description = "사용자의 특정 팀의 세부 정보 조회")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "TEAM_202", description = "OK, 성공적으로 조회되었습니다.")
    })
    public BaseResponse<TeamResponseDTO.TeamResult> getTeam(
            @RequestParam(value = "teamId") Long teamId,
            @RequestHeader(value = "member-id") String memberId
    ) {
        TeamResponseDTO.TeamResult result = teamService.getTeam(teamId, memberId);
        return BaseResponse.onSuccess(SuccessStatus.TEAM_FINED, result);
    }

    @PatchMapping("/{teamId}")
    @Operation(summary = "특정 팀 수정 API", description = "사용자의 특정 팀의 정보 수정")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "TEAM_203", description = "OK, 성공적으로 수정되었습니다.")
    })
    public BaseResponse<TeamResponseDTO.TeamResult> updateTeam(
            @RequestParam(value = "teamId") Long teamId,
            @RequestHeader(value = "member-id") String memberId,
            @RequestBody TeamRequestDTO.TeamUpdateDTO request
    ) {
        TeamResponseDTO.TeamResult result = teamService.updateTeam(teamId, memberId, request);
        return BaseResponse.onSuccess(SuccessStatus.TEAM_UPDATE, result);
    }

    @DeleteMapping("/{teamId}")
    @Operation(summary = "특정 팀 삭제 API", description = "사용자의 특정 팀 삭제 수정")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "TEAM_204", description = "OK, 성공적으로 삭제되었습니다.")
    })
    public BaseResponse<Void> deleteTeam(
            @RequestParam(value = "teamId") Long teamId,
            @RequestHeader(value = "member-id") String memberId
    ) {
        teamService.deleteTeam(teamId, memberId);
        return BaseResponse.onSuccess(SuccessStatus.TEAM_DELETE, null);
    }

    @PostMapping("/{teamId}/members")
    @Operation(summary = "팀 초대 API", description = "특정 팀에 새로운 사용자 초대")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "TEAM_205", description = "OK, 성공적으로 수정되었습니다.")
    })
    public BaseResponse<TeamResponseDTO.TeamInviteResult> inviteTeam(
            @RequestParam(value = "teamId") Long teamId,
            @RequestHeader(value = "member-id") String memberId,
            @RequestBody TeamRequestDTO.InviteMemberDTO request
    ) {
        TeamResponseDTO.TeamInviteResult result = teamService.inviteTeam(teamId, memberId, request);
        return BaseResponse.onSuccess(SuccessStatus.TEAM_INVITE, result);
    }

    @PatchMapping("/{teamId}/members/{teamMemberId}")
    @Operation(summary = "팀 멤버 수정 API", description = "특정 팀 멤버의 정보를 수정")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "TEAM_206", description = "OK, 성공적으로 수정되었습니다.")
    })
    public BaseResponse<TeamResponseDTO.UpdateTeamMemberResult> updateTeamMember(
            @RequestParam(value = "teamId") Long teamId,
            @RequestParam(value = "teamMemberId") Long teamMemberId,
            @RequestHeader(value = "member-id") String memberId,
            @RequestBody TeamRequestDTO.UpdateTeamMemberDTO request
    ) {
        TeamResponseDTO.UpdateTeamMemberResult result = teamService.updateTeamMember(teamId, teamMemberId, memberId, request);
        return BaseResponse.onSuccess(SuccessStatus.TEAM_MEMBER_UPDATE, result);
    }

    @DeleteMapping("/{teamId}/members/{teamMemberId}")
    @Operation(summary = "팀 멤버 삭제 API", description = "특정 팀 멤버를 삭제")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "TEAM_207", description = "OK, 성공적으로 수정되었습니다.")
    })
    public BaseResponse<Void> deleteTeamMember(
            @RequestParam(value = "teamId") Long teamId,
            @RequestParam(value = "teamMemberId") Long teamMemberId,
            @RequestHeader(value = "member-id") String memberId
    ) {
        teamService.deleteTeamMember(teamId, teamMemberId, memberId);
        return BaseResponse.onSuccess(SuccessStatus.TEAM_MEMBER_DELETE, null);
    }

    @PatchMapping("/{teamId}/invitations/{teamMemberId}/accept")
    @Operation(summary = "팀 초대 수락 API", description = "사용자 초대 수락")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "TEAM_208", description = "OK, 성공적으로 수정되었습니다.")
    })
    public BaseResponse<TeamResponseDTO.TeamInviteAcceptResult> inviteTeamAccept(
            @RequestParam(value = "teamId") Long teamId,
            @RequestParam(value = "teamMemberId") Long teamMemberId,
            @RequestHeader(value = "member-id") String memberId
    ) {
        TeamResponseDTO.TeamInviteAcceptResult result = teamService.inviteTeamAccept(teamId, teamMemberId, memberId);
        return BaseResponse.onSuccess(SuccessStatus.TEAM_INVITE_ACCEPT, result);
    }

    @DeleteMapping("/{teamId}/invitations/{teamMemberId}/reject")
    @Operation(summary = "팀 멤버 삭제 API", description = "특정 팀 멤버를 삭제")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "TEAM_207", description = "OK, 성공적으로 수정되었습니다.")
    })
    public BaseResponse<Void> inviteTeamReject(
            @RequestParam(value = "teamId") Long teamId,
            @RequestParam(value = "teamMemberId") Long teamMemberId,
            @RequestHeader(value = "member-id") String memberId
    ) {
        teamService.inviteTeamReject(teamId, teamMemberId, memberId);
        return BaseResponse.onSuccess(SuccessStatus.TEAM_INVITE_REJECT, null);
    }

}
