package backend.albago.domain.post.application;

import backend.albago.domain.post.dto.PostRequestDTO;
import backend.albago.domain.post.dto.PostResponseDTO;

public interface PostService {

    PostResponseDTO.FindPostsResult findPosts(String memberIdString, PostRequestDTO.FindPostsDTO request);

    PostResponseDTO.FindLikePostsResult findLikePosts(String memberIdString, PostRequestDTO.FindPostsDTO request);

    PostResponseDTO.FindPostResult findPost(String memberIdString, Long postId);

    PostResponseDTO.CreatePostResult createPost(String memberIdString, PostRequestDTO.CreatePostDTO request);

    PostResponseDTO.UpdatePostResult updatePost(String memberIdString, Long postId, PostRequestDTO.UpdatePostDTO request);

    void deletePost(String memberId, Long postId);

    PostResponseDTO.PostLikeResult postLike(String memberIdString, Long postId);

    PostResponseDTO.PostUnlikeResult postUnlike(String memberIdString, Long postId); // 새로 추가되는 메서드

    PostResponseDTO.FindCommentsResult findComments(String memberIdString, Long postId, PostRequestDTO.FindCommentsDTO request);

    PostResponseDTO.CreateCommentsResult createComments(String memberIdString, Long postId, PostRequestDTO.CreateCommentsDTO request);

    PostResponseDTO.UpdateCommentsResult updateComments(String memberIdString, Long postId, Long commentId, PostRequestDTO.UpdateCommentsDTO request);

    void deleteComments(String memberIdString, Long postId, Long commentId);
}
