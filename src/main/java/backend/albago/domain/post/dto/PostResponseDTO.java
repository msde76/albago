package backend.albago.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class PostResponseDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PostDetailDTO {
        private Long postId;
        private String title;
        private String content;
        private Long memberId;
        private String memberName;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private int likeCount;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FindPostsResult {
        private List<PostDetailDTO> postList;
        private int currentPage;
        private int totalPages;
        private long totalElements;
        private boolean isFirst;
        private boolean isLast;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FindLikePostsResult {
        private List<PostDetailDTO> postList;
        private int currentPage;
        private int totalPages;
        private long totalElements;
        private boolean isFirst;
        private boolean isLast;
        private int minLikeCount;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FindPostResult {
        private PostDetailDTO postDetail;
        private boolean isLikedByCurrentUser;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreatePostResult {
        private Long postId;
        private Long memberId;
        private String title;
        private LocalDateTime createdAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdatePostResult {
        private Long postId;
        private LocalDateTime updatedAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PostLikeResult {
        private Long postId;
        private Long memberId;
        private int currentLikeCount;
        private LocalDateTime likedAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PostUnlikeResult {
        private Long postId;
        private Long memberId;
        private int currentLikeCount;
        private LocalDateTime unlikedAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CommentDetailDTO {
        private Long commentId;
        private Long memberId;
        private String memberName;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FindCommentsResult {
        List<CommentDetailDTO> comments;
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
    public static class CreateCommentsResult {
        private Long commentId;
        private Long postId;
        private Long memberId;
        private String content;
        private LocalDateTime createdAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdateCommentsResult {
        private Long commentId;
        private Long postId;
        private Long memberId;
        private String content;
        private LocalDateTime updatedAt;
    }
}
