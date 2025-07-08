package backend.albago.domain.post.api;

import backend.albago.domain.post.application.PostService;
import backend.albago.domain.post.dto.PostRequestDTO;
import backend.albago.domain.post.dto.PostResponseDTO;
import backend.albago.global.common.response.BaseResponse;
import backend.albago.global.error.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostRestController {

    private final PostService postService;

    @GetMapping("")
    @Operation(summary = "게시물 목록 조회 API", description = "공용 게시판의 모든 게시물 목록 조회")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "POST_200", description = "OK, 성공적으로 조회되었습니다.")
    })
    public BaseResponse<PostResponseDTO.FindPostsResult> findPosts(
            @RequestHeader(value = "member-id") String memberId,
            @RequestBody PostRequestDTO.FindPostsDTO request
    ) {
        PostResponseDTO.FindPostsResult result = postService.findPosts(memberId, request);
        return BaseResponse.onSuccess(SuccessStatus.GET_POSTS, result);
    }

    @GetMapping("/like")
    @Operation(summary = "인기 게시물 목록 조회 API", description = "인기 게시판의 모든 게시물 목록 조회")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "POST_200", description = "OK, 성공적으로 조회되었습니다.")
    })
    public BaseResponse<PostResponseDTO.FindLikePostsResult> findLikePosts(
            @RequestHeader(value = "member-id") String memberId,
            @RequestBody PostRequestDTO.FindPostsDTO request
    ) {
        PostResponseDTO.FindLikePostsResult result = postService.findLikePosts(memberId, request);
        return BaseResponse.onSuccess(SuccessStatus.GET_LIKE_POSTS, result);
    }

    @GetMapping("/{postId}")
    @Operation(summary = "게시물 상세 조회 API", description = "게시판의 게시물 상세 조회")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "POST_200", description = "OK, 성공적으로 조회되었습니다.")
    })
    public BaseResponse<PostResponseDTO.FindPostResult> findPost(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "postId") Long postId
    ) {
        PostResponseDTO.FindPostResult result = postService.findPost(memberId, postId);
        return BaseResponse.onSuccess(SuccessStatus.GET_POST, result);
    }

    @PostMapping("")
    @Operation(summary = "게시물 생성 API", description = "게시판에 게시물을 생성")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "POST_200", description = "OK, 성공적으로 생성되었습니다.")
    })
    public BaseResponse<PostResponseDTO.CreatePostResult> createPost(
            @RequestHeader(value = "member-id") String memberId,
            @RequestBody PostRequestDTO.CreatePostDTO request
    ) {
        PostResponseDTO.CreatePostResult result = postService.createPost(memberId, request);
        return BaseResponse.onSuccess(SuccessStatus.POST_CREATED, result);
    }

    @PatchMapping("/{postId}")
    @Operation(summary = "게시물 수정 API", description = "특정 게시물을 수정")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "POST_200", description = "OK, 성공적으로 수정되었습니다.")
    })
    public BaseResponse<PostResponseDTO.UpdatePostResult> updatePost(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "postId") Long postId,
            @RequestBody PostRequestDTO.UpdatePostDTO request
    ) {
        PostResponseDTO.UpdatePostResult result = postService.updatePost(memberId, postId, request);
        return BaseResponse.onSuccess(SuccessStatus.POST_UPDATED, result);
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "게시물 삭제 API", description = "특정 게시물을 삭제")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "POST_200", description = "OK, 성공적으로 삭제되었습니다.")
    })
    public BaseResponse<Void> deletePost(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "postId") Long postId
    ) {
        postService.deletePost(memberId, postId);
        return BaseResponse.onSuccess(SuccessStatus.POST_DELETED, null);
    }

    @PostMapping("/{postId}/like")
    @Operation(summary = "게시물 좋아요 API", description = "특정 게시물에 좋아요 생성")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "POST_200", description = "OK, 성공적으로 생성되었습니다.")
    })
    public BaseResponse<PostResponseDTO.PostLikeResult> postLike(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "postId") Long postId
    ) {
        PostResponseDTO.PostLikeResult result = postService.postLike(memberId, postId);
        return BaseResponse.onSuccess(SuccessStatus.POST_LIKE, result);
    }

    @DeleteMapping("/{postId}/like")
    @Operation(summary = "게시물 좋아요 취소 API", description = "특정 게시물에 좋아요 취소")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "POST_200", description = "OK, 성공적으로 삭제되었습니다.")
    })
    public BaseResponse<PostResponseDTO.PostUnlikeResult> postUnlike(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "postId") Long postId
    ) {
        PostResponseDTO.PostUnlikeResult result = postService.postUnlike(memberId, postId);
        return BaseResponse.onSuccess(SuccessStatus.POST_UNLIKE, result);
    }

    @GetMapping("/{postId}/comments")
    @Operation(summary = "댓글 조회 API", description = "특정 게시물의 모든 댓글 조회")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "POST_200", description = "OK, 성공적으로 조회되었습니다.")
    })
    public BaseResponse<PostResponseDTO.FindCommentsResult> findComments(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "postId") Long postId,
            @RequestBody PostRequestDTO.FindCommentsDTO request
    ) {
        PostResponseDTO.FindCommentsResult result = postService.findComments(memberId, postId, request);
        return BaseResponse.onSuccess(SuccessStatus.COMMENT_FINED, result);
    }

    @PostMapping("/{postId}/comments")
    @Operation(summary = "댓글 작성 API", description = "특정 게시물에 댓글 작성")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "POST_200", description = "OK, 성공적으로 생성되었습니다.")
    })
    public BaseResponse<PostResponseDTO.CreateCommentsResult> createComments(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "postId") Long postId,
            @RequestBody PostRequestDTO.CreateCommentsDTO request
    ) {
        PostResponseDTO.CreateCommentsResult result = postService.createComments(memberId, postId, request);
        return BaseResponse.onSuccess(SuccessStatus.COMMENT_CREATED, result);
    }

    @PatchMapping("/{postId}/comments/{commentId}")
    @Operation(summary = "댓글 수정 API", description = "특정 게시물에 댓글 수정")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "POST_200", description = "OK, 성공적으로 수정되었습니다.")
    })
    public BaseResponse<PostResponseDTO.UpdateCommentsResult> updateComments(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "postId") Long postId,
            @PathVariable(value = "commentId") Long commentId,
            @RequestBody PostRequestDTO.UpdateCommentsDTO request
    ) {
        PostResponseDTO.UpdateCommentsResult result = postService.updateComments(memberId, postId, commentId, request);
        return BaseResponse.onSuccess(SuccessStatus.COMMENT_UPDATE, result);
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    @Operation(summary = "댓글 삭제 API", description = "특정 게시물에 댓글 삭제")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "POST_200", description = "OK, 성공적으로 삭제되었습니다.")
    })
    public BaseResponse<Void> deleteComments(
            @RequestHeader(value = "member-id") String memberId,
            @PathVariable(value = "postId") Long postId,
            @PathVariable(value = "commentId") Long commentId
    ) {
        postService.deleteComments(memberId, postId, commentId);
        return BaseResponse.onSuccess(SuccessStatus.COMMENT_DELETE, null);
    }
}
