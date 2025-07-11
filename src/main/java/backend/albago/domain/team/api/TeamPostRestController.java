package backend.albago.domain.team.api;

import backend.albago.domain.team.application.TeamPostService;
import backend.albago.domain.team.dto.TeamPostRequestDTO;
import backend.albago.domain.team.dto.TeamPostResponseDTO;
import backend.albago.global.common.response.BaseResponse;
import backend.albago.global.error.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
public class TeamPostRestController {

    private final TeamPostService teamPostService;

    @GetMapping("/{teamId}/posts")
    @Operation(summary = "팀 게시물 조회 API", description = "특정 팀의 모든 게시물 목록을 조회")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "TEAM_POST_200", description = "OK, 성공적으로 조회되었습니다.")
    })
    public BaseResponse<TeamPostResponseDTO.FindTeamPostsResult> findTeamPosts(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "teamId") Long teamId,
            @RequestBody TeamPostRequestDTO.FindTeamPostsDTO request
    ) {
        TeamPostResponseDTO.FindTeamPostsResult result = teamPostService.findTeamPosts(teamId, request, memberId);
        return BaseResponse.onSuccess(SuccessStatus.TEAM_POSTS_FINED, result);
    }

    @GetMapping("/{teamId}/posts/{teamPostId}")
    @Operation(summary = "팀 게시물 상세 조회 API", description = "특정 팀 게시물의 상세 정보 조회")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "TEAM_POST_200", description = "OK, 성공적으로 조회되었습니다.")
    })
    public BaseResponse<TeamPostResponseDTO.FindTeamPostResult> findTeamPost(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "teamId") Long teamId,
            @PathVariable(value = "teamPostId") Long teamPostId
    ) {
        TeamPostResponseDTO.FindTeamPostResult result = teamPostService.findTeamPost(teamId, teamPostId, memberId);
        return BaseResponse.onSuccess(SuccessStatus.TEAM_POST_FINED, result);
    }

    @PostMapping("/{teamId}/posts")
    @Operation(summary = "팀 게시물 생성 API", description = "특정 팀의 게시물 생성")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "TEAM_POST_200", description = "OK, 성공적으로 생성되었습니다.")
    })
    public BaseResponse<TeamPostResponseDTO.CreateTeamPostResult> createTeamPost(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "teamId") Long teamId,
            @RequestBody TeamPostRequestDTO.CreateTeamPostDTO request
    ) {
        TeamPostResponseDTO.CreateTeamPostResult result = teamPostService.createTeamPost(teamId, request, memberId);
        return BaseResponse.onSuccess(SuccessStatus.TEAM_POSTS_CREATE, result);
    }

    @PatchMapping("/{teamId}/posts/{teamPostId}")
    @Operation(summary = "팀 게시물 수정 API", description = "특정 팀의 게시물 수정")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "TEAM_POST_200", description = "OK, 성공적으로 수정되었습니다.")
    })
    public BaseResponse<TeamPostResponseDTO.UpdateTeamPostResult> updateTeamPost(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "teamId") Long teamId,
            @PathVariable(value = "teamPostId") Long teamPostId,
            @RequestBody TeamPostRequestDTO.UpdateTeamPostDTO request
    ) {
        TeamPostResponseDTO.UpdateTeamPostResult result = teamPostService.updateTeamPost(teamId, teamPostId, request, memberId);
        return BaseResponse.onSuccess(SuccessStatus.TEAM_POSTS_UPDATE, result);
    }

    @DeleteMapping("/{teamId}/posts/{teamPostId}")
    @Operation(summary = "팀 게시물 삭제 API", description = "특정 팀의 게시물 삭제")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "TEAM_POST_200", description = "OK, 성공적으로 삭제되었습니다.")
    })
    public BaseResponse<Void> deleteTeamPost(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "teamId") Long teamId,
            @PathVariable(value = "teamPostId") Long teamPostId
    ) {
        teamPostService.deleteTeamPost(teamId, teamPostId, memberId);
        return BaseResponse.onSuccess(SuccessStatus.TEAM_POSTS_DELETE, null);
    }

    @GetMapping("/{teamId}/posts/{teamPostId}/comments")
    @Operation(summary = "팀 게시물 댓글 조회 API", description = "특정 팀 게시물의 모든 댓글 조회")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "TEAM_POST_COMMENT_200", description = "OK, 성공적으로 조회되었습니다.")
    })
    public BaseResponse<TeamPostResponseDTO.findTeamPostsCommentResult> findTeamPostsComment(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "teamId") Long teamId,
            @PathVariable(value = "teamPostId") Long teamPostId,
            @RequestBody TeamPostRequestDTO.FindTeamPostCommentDTO request
    ) {
        TeamPostResponseDTO.findTeamPostsCommentResult result = teamPostService.findTeamPostsComment(teamId, teamPostId, request, memberId);
        return BaseResponse.onSuccess(SuccessStatus.TEAM_POSTS_COMMENT_FINED, result);
    }

    @PostMapping("/{teamId}/posts/{teamPostId}/comments")
    @Operation(summary = "팀 게시물 댓글 작성 API", description = "특정 팀 게시물에 댓글 작성")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "TEAM_POST_COMMENT_200", description = "OK, 성공적으로 작성되었습니다.")
    })
    public BaseResponse<TeamPostResponseDTO.CreateTeamPostCommentResult> createTeamPostComment(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "teamId") Long teamId,
            @PathVariable(value = "teamPostId") Long teamPostId,
            @RequestBody TeamPostRequestDTO.CreateTeamPostCommentDTO request
    ) {
        TeamPostResponseDTO.CreateTeamPostCommentResult result = teamPostService.createTeamPostComment(teamId, teamPostId, request, memberId);
        return BaseResponse.onSuccess(SuccessStatus.TEAM_POSTS_COMMENT_CREATE, result);
    }

    @PatchMapping("/{teamId}/posts/{teamPostId}/comments/{commentId}")
    @Operation(summary = "팀 게시물 댓글 수정 API", description = "특정 팀 게시물에 댓글 수정")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "TEAM_POST_COMMENT_200", description = "OK, 성공적으로 수정되었습니다.")
    })
    public BaseResponse<TeamPostResponseDTO.UpdateTeamPostCommentResult> updateTeamPostComment(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "teamId") Long teamId,
            @PathVariable(value = "teamPostId") Long teamPostId,
            @PathVariable(value = "commentId") Long commentId,
            @RequestBody TeamPostRequestDTO.UpdateTeamPostCommentDTO request
    ) {
        TeamPostResponseDTO.UpdateTeamPostCommentResult result = teamPostService.updateTeamPostComment(teamId, teamPostId, commentId, request, memberId);
        return BaseResponse.onSuccess(SuccessStatus.TEAM_POSTS_COMMENT_UPDATE, result);
    }

    @DeleteMapping("/{teamId}/posts/{teamPostId}/comments/{commentId}")
    @Operation(summary = "팀 게시물 댓글 삭제 API", description = "특정 팀 게시물에 댓글 삭제")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "TEAM_POST_COMMENT_200", description = "OK, 성공적으로 삭제되었습니다.")
    })
    public BaseResponse<Void> deleteTeamPostComment(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "teamId") Long teamId,
            @PathVariable(value = "commentId") Long commentId,
            @PathVariable(value = "teamPostId") Long teamPostId
    ) {
        teamPostService.deleteTeamPostComment(teamId, teamPostId, commentId, memberId);
        return BaseResponse.onSuccess(SuccessStatus.TEAM_POSTS_COMMENT_DELETE, null);
    }
}
