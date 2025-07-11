package backend.albago.domain.team.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class TeamPostResponseDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TeamPostDetailDTO {
        private Long teamPostId;
        private Long teamId;
        private Long authorMemberId;
        private String authorMemberName;
        private String title;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FindTeamPostsResult {
        List<TeamPostDetailDTO> teamPosts;
        private int currentPage;
        private int totalPages;
        private long totalElements;
        private boolean hasNext;
        private boolean isFirst;
        private boolean isLast;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FindTeamPostResult {
        private TeamPostDetailDTO teamPost;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateTeamPostResult {
        private Long teamPostId;
        private Long teamId;
        private Long authorMemberId;
        private String title;
        private String content;
        private LocalDateTime createdAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdateTeamPostResult {
        private Long teamPostId;
        private Long teamId;
        private Long authorMemberId;
        private String title;
        private String content;
        private LocalDateTime updatedAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TeamPostCommentDetailDTO {
        private Long commentId;
        private Long teamPostId;
        private Long authorMemberId;
        private String authorMemberName; // 작성자 이름
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class findTeamPostsCommentResult {
        List<TeamPostCommentDetailDTO> comments;
        private int currentPage;
        private int totalPages;
        private long totalElements;
        private boolean hasNext;
        private boolean isFirst;
        private boolean isLast;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateTeamPostCommentResult {
        private Long commentId;
        private Long teamPostId;
        private Long authorMemberId;
        private String content;
        private LocalDateTime createdAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdateTeamPostCommentResult {
        private Long commentId;
        private Long teamPostId;
        private Long authorMemberId;
        private String content;
        private LocalDateTime updatedAt;
    }
}
