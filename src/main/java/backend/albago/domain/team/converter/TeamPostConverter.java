package backend.albago.domain.team.converter;

import backend.albago.domain.team.domain.entity.TeamComment;
import backend.albago.domain.team.domain.entity.TeamPost;
import backend.albago.domain.team.dto.TeamPostResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class TeamPostConverter {

    public static TeamPostResponseDTO.TeamPostDetailDTO toTeamPostDetailDTO(TeamPost teamPost) {
        return TeamPostResponseDTO.TeamPostDetailDTO.builder()
                .teamPostId(teamPost.getId())
                .teamId(teamPost.getTeam().getId())
                .authorMemberId(teamPost.getMember().getId())
                .authorMemberName(teamPost.getMember().getName())
                .title(teamPost.getTitle())
                .content(teamPost.getContent())
                .createdAt(teamPost.getCreatedAt())
                .updatedAt(teamPost.getUpdatedAt())
                .build();
    }

    public static TeamPostResponseDTO.FindTeamPostsResult toFindTeamPostsResult(Page<TeamPost> teamPostPage) {
        List<TeamPostResponseDTO.TeamPostDetailDTO> teamPostDetailList = teamPostPage.getContent().stream()
                .map(TeamPostConverter::toTeamPostDetailDTO)
                .collect(Collectors.toList());

        return TeamPostResponseDTO.FindTeamPostsResult.builder()
                .teamPosts(teamPostDetailList)
                .currentPage(teamPostPage.getNumber())
                .totalPages(teamPostPage.getTotalPages())
                .totalElements(teamPostPage.getTotalElements())
                .hasNext(teamPostPage.hasNext())
                .isFirst(teamPostPage.isFirst())
                .isLast(teamPostPage.isLast())
                .build();
    }

    public static TeamPostResponseDTO.FindTeamPostResult toFindTeamPostResult(TeamPost teamPost) {
        return TeamPostResponseDTO.FindTeamPostResult.builder()
                .teamPost(toTeamPostDetailDTO(teamPost))
                .build();
    }

    public static TeamPostResponseDTO.CreateTeamPostResult toCreateTeamPostResult(TeamPost teamPost) {
        return TeamPostResponseDTO.CreateTeamPostResult.builder()
                .teamPostId(teamPost.getId())
                .teamId(teamPost.getTeam().getId())
                .authorMemberId(teamPost.getMember().getId())
                .title(teamPost.getTitle())
                .content(teamPost.getContent())
                .createdAt(teamPost.getCreatedAt())
                .build();
    }

    public static TeamPostResponseDTO.UpdateTeamPostResult toUpdateTeamPostResult(TeamPost teamPost) {
        return TeamPostResponseDTO.UpdateTeamPostResult.builder()
                .teamPostId(teamPost.getId())
                .teamId(teamPost.getTeam().getId())
                .authorMemberId(teamPost.getMember().getId())
                .title(teamPost.getTitle())
                .content(teamPost.getContent())
                .updatedAt(teamPost.getUpdatedAt())
                .build();
    }

    public static TeamPostResponseDTO.TeamPostCommentDetailDTO toTeamPostCommentDetailDTO(TeamComment teamComment) {
        return TeamPostResponseDTO.TeamPostCommentDetailDTO.builder()
                .commentId(teamComment.getId())
                .teamPostId(teamComment.getTeamPost().getId())
                .authorMemberId(teamComment.getMember().getId())
                .authorMemberName(teamComment.getMember().getName())
                .content(teamComment.getContent())
                .createdAt(teamComment.getCreatedAt())
                .updatedAt(teamComment.getUpdatedAt())
                .build();
    }

    public static TeamPostResponseDTO.findTeamPostsCommentResult toFindTeamPostsCommentResult(Page<TeamComment> commentPage) {
        List<TeamPostResponseDTO.TeamPostCommentDetailDTO> commentDetailList = commentPage.getContent().stream()
                .map(TeamPostConverter::toTeamPostCommentDetailDTO)
                .collect(Collectors.toList());

        return TeamPostResponseDTO.findTeamPostsCommentResult.builder()
                .comments(commentDetailList)
                .currentPage(commentPage.getNumber())
                .totalPages(commentPage.getTotalPages())
                .totalElements(commentPage.getTotalElements())
                .hasNext(commentPage.hasNext())
                .isFirst(commentPage.isFirst())
                .isLast(commentPage.isLast())
                .build();
    }

    public static TeamPostResponseDTO.CreateTeamPostCommentResult toCreateTeamPostCommentResult(TeamComment teamComment) {
        return TeamPostResponseDTO.CreateTeamPostCommentResult.builder()
                .commentId(teamComment.getId())
                .teamPostId(teamComment.getTeamPost().getId())
                .authorMemberId(teamComment.getMember().getId())
                .content(teamComment.getContent())
                .createdAt(teamComment.getCreatedAt())
                .build();
    }

    public static TeamPostResponseDTO.UpdateTeamPostCommentResult toUpdateTeamPostCommentResult(TeamComment teamComment) {
        return TeamPostResponseDTO.UpdateTeamPostCommentResult.builder()
                .commentId(teamComment.getId())
                .teamPostId(teamComment.getTeamPost().getId())
                .authorMemberId(teamComment.getMember().getId())
                .content(teamComment.getContent())
                .updatedAt(teamComment.getUpdatedAt())
                .build();
    }
}
