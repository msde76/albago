package backend.albago.domain.post.converter;

import backend.albago.domain.post.domain.entity.Comment;
import backend.albago.domain.post.domain.entity.Post;
import backend.albago.domain.post.domain.entity.PostLike;
import backend.albago.domain.post.dto.PostResponseDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class PostConverter {

    public static PostResponseDTO.PostDetailDTO toPostDetailDTO(Post post) {
        return PostResponseDTO.PostDetailDTO.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .memberId(post.getMember().getId())
                .memberName(post.getMember().getName())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .likeCount(post.getLikeCount())
                .build();
    }

    public static PostResponseDTO.FindPostsResult toFindPostsResult(Page<Post> postPage) {
        List<PostResponseDTO.PostDetailDTO> postDetailDTOs = postPage.getContent().stream()
                .map(PostConverter::toPostDetailDTO)
                .collect(Collectors.toList());

        return PostResponseDTO.FindPostsResult.builder()
                .postList(postDetailDTOs)
                .currentPage(postPage.getNumber())
                .totalPages(postPage.getTotalPages())
                .totalElements(postPage.getTotalElements())
                .isFirst(postPage.isFirst())
                .isLast(postPage.isLast())
                .build();
    }

    public static PostResponseDTO.FindLikePostsResult toFindLikePostsResult(Page<Post> postPage, int minLikeCount) {
        List<PostResponseDTO.PostDetailDTO> postDetailDTOs = postPage.getContent().stream()
                .map(PostConverter::toPostDetailDTO)
                .collect(Collectors.toList());

        return PostResponseDTO.FindLikePostsResult.builder()
                .postList(postDetailDTOs)
                .currentPage(postPage.getNumber())
                .totalPages(postPage.getTotalPages())
                .totalElements(postPage.getTotalElements())
                .isFirst(postPage.isFirst())
                .isLast(postPage.isLast())
                .minLikeCount(minLikeCount)
                .build();
    }

    public static PostResponseDTO.FindPostResult toFindPostResult(Post post, boolean isLikedByCurrentUser) {
        PostResponseDTO.PostDetailDTO postDetailDTO = toPostDetailDTO(post);

        return PostResponseDTO.FindPostResult.builder()
                .postDetail(postDetailDTO)
                .isLikedByCurrentUser(isLikedByCurrentUser)
                .build();
    }

    public static PostResponseDTO.CreatePostResult toCreatePostResult(Post post) {
        return PostResponseDTO.CreatePostResult.builder()
                .postId(post.getId())
                .memberId(post.getMember().getId())
                .title(post.getTitle())
                .createdAt(post.getCreatedAt())
                .build();
    }

    public static PostResponseDTO.UpdatePostResult toUpdatePostResult(Post post) {
        return PostResponseDTO.UpdatePostResult.builder()
                .postId(post.getId())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    public static PostResponseDTO.PostLikeResult toPostLikeResult(PostLike postLike, Post post) {
        return PostResponseDTO.PostLikeResult.builder()
                .postId(post.getId())
                .memberId(postLike.getMember().getId())
                .currentLikeCount(post.getLikeCount())
                .likedAt(postLike.getCreatedAt())
                .build();
    }

    public static PostResponseDTO.PostUnlikeResult toPostUnlikeResult(Long postId, Long memberId, int currentLikeCount) {
        return PostResponseDTO.PostUnlikeResult.builder()
                .postId(postId)
                .memberId(memberId)
                .currentLikeCount(currentLikeCount)
                .unlikedAt(LocalDateTime.now())
                .build();
    }

    public static PostResponseDTO.CommentDetailDTO toCommentDetailDTO(Comment comment) {
        return PostResponseDTO.CommentDetailDTO.builder()
                .commentId(comment.getId())
                .memberId(comment.getMember().getId())
                .memberName(comment.getMember().getName())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }

    public static PostResponseDTO.FindCommentsResult toFindCommentsResult(Page<Comment> commentPage) {
        List<PostResponseDTO.CommentDetailDTO> commentDetailList = commentPage.getContent().stream()
                .map(PostConverter::toCommentDetailDTO)
                .collect(Collectors.toList());

        return PostResponseDTO.FindCommentsResult.builder()
                .comments(commentDetailList)
                .currentPage(commentPage.getNumber())
                .totalPages(commentPage.getTotalPages())
                .totalElements(commentPage.getTotalElements())
                .hasNext(commentPage.hasNext())
                .isFirst(commentPage.isFirst())
                .isLast(commentPage.isLast())
                .build();
    }

    public static PostResponseDTO.CreateCommentsResult toCreateCommentsResult(Comment comment) {
        return PostResponseDTO.CreateCommentsResult.builder()
                .commentId(comment.getId())
                .postId(comment.getPost().getId())
                .memberId(comment.getMember().getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }

    public static PostResponseDTO.UpdateCommentsResult toUpdateCommentsResult(Comment comment) {
        return PostResponseDTO.UpdateCommentsResult.builder()
                .commentId(comment.getId())
                .postId(comment.getPost().getId())
                .memberId(comment.getMember().getId())
                .content(comment.getContent())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }
}
