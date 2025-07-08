package backend.albago.domain.post.application;

import backend.albago.domain.member.domain.entity.Member;
import backend.albago.domain.member.domain.repository.MemberRepository;
import backend.albago.domain.post.converter.PostConverter;
import backend.albago.domain.post.domain.entity.Comment;
import backend.albago.domain.post.domain.entity.Post;
import backend.albago.domain.post.domain.entity.PostLike;
import backend.albago.domain.post.domain.repository.CommentRepository;
import backend.albago.domain.post.domain.repository.PostLikeRepository;
import backend.albago.domain.post.domain.repository.PostRepository;
import backend.albago.domain.post.dto.PostRequestDTO;
import backend.albago.domain.post.dto.PostResponseDTO;
import backend.albago.domain.post.exception.PostException;
import backend.albago.global.error.code.status.ErrorStatus;
import backend.albago.global.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional(readOnly = true)
    public PostResponseDTO.FindPostsResult findPosts(String memberIdString, PostRequestDTO.FindPostsDTO request) {

        Long memberId = Long.parseLong(memberIdString);

        memberRepository.findById(memberId)
                .orElseThrow(() -> new PostException(ErrorStatus.NO_SUCH_MEMBER));

        int page = request.getPage();
        long size = request.getSize();

        if (page < 0) {
            throw new PostException(ErrorStatus.PAGE_SIZE_UNDER_ONE);
        }
        if (size <= 0) {
            throw new PostException(ErrorStatus.PAGE_UNDER_ONE);
        }

        Pageable pageable = PageRequest.of(page, (int) size, Sort.by("createdAt").ascending());

        Page<Post> postPage = postRepository.findAllByOrderByCreatedAtDesc(pageable);

        return PostConverter.toFindPostsResult(postPage);
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponseDTO.FindLikePostsResult findLikePosts(String memberIdString, PostRequestDTO.FindPostsDTO request) {

        Long memberId = Long.parseLong(memberIdString);

        memberRepository.findById(memberId)
                .orElseThrow(() -> new PostException(ErrorStatus.NO_SUCH_MEMBER));

        int page = request.getPage();
        long size = request.getSize();
        final int MIN_LIKE_COUNT_FOR_POPULAR = 10;

        if (page < 0) {
            throw new PostException(ErrorStatus.PAGE_SIZE_UNDER_ONE);
        }
        if (size <= 0) {
            throw new PostException(ErrorStatus.PAGE_UNDER_ONE);
        }

        Pageable pageable = PageRequest.of(page, (int) size,
                Sort.by(Sort.Direction.DESC, "likeCount")
                        .and(Sort.by(Sort.Direction.DESC, "createdAt")));

        Page<Post> postPage = postRepository.findByLikeCountGreaterThanEqualOrderByLikeCountDescCreatedAtDesc(
                MIN_LIKE_COUNT_FOR_POPULAR, pageable
        );

        return PostConverter.toFindLikePostsResult(postPage, MIN_LIKE_COUNT_FOR_POPULAR);
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponseDTO.FindPostResult findPost(String memberIdString, Long postId) {

        Long memberId = Long.parseLong(memberIdString);

        Member requesterMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new PostException(ErrorStatus.NO_SUCH_MEMBER));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorStatus.POST_NOT_FOUND));

        boolean isLikedByCurrentUser = postLikeRepository.findByPostAndMember(post, requesterMember).isPresent();

        return PostConverter.toFindPostResult(post, isLikedByCurrentUser);
    }

    @Override
    @Transactional
    public PostResponseDTO.CreatePostResult createPost(String memberIdString, PostRequestDTO.CreatePostDTO request) {

        Long memberId = Long.parseLong(memberIdString);

        Member authorMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new PostException(ErrorStatus.NO_SUCH_MEMBER));

        Post newPost = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .member(authorMember)
                .likeCount(0)
                .build();

        newPost = postRepository.save(newPost);

        return PostConverter.toCreatePostResult(newPost);
    }

    @Override
    @Transactional
    public PostResponseDTO.UpdatePostResult updatePost(String memberIdString, Long postId, PostRequestDTO.UpdatePostDTO request) {

        Long memberId = Long.parseLong(memberIdString);

        Member requesterMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new PostException(ErrorStatus.NO_SUCH_MEMBER));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorStatus.POST_NOT_FOUND));


        if (!post.getMember().getId().equals(requesterMember.getId())) {
            throw new PostException(ErrorStatus._FORBIDDEN);
        }

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());

        postRepository.save(post);

        return PostConverter.toUpdatePostResult(post);
    }

    @Override
    @Transactional
    public void deletePost(String memberIdString, Long postId) {

        Long memberId = Long.parseLong(memberIdString);

        Member requesterMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new PostException(ErrorStatus.NO_SUCH_MEMBER));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorStatus.POST_NOT_FOUND));

        if (!post.getMember().getId().equals(requesterMember.getId())) {
            throw new PostException(ErrorStatus._FORBIDDEN);
        }

        List<Comment> commentsToDelete = commentRepository.findByPost(post);
        if (!commentsToDelete.isEmpty()) {
            commentRepository.deleteAll(commentsToDelete);
        }

        postRepository.delete(post);
    }

    @Override
    @Transactional
    public PostResponseDTO.PostLikeResult postLike(String memberIdString, Long postId) {

        Long memberId = Long.parseLong(memberIdString);

        Member likerMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new PostException(ErrorStatus.NO_SUCH_MEMBER));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorStatus.POST_NOT_FOUND));

        Optional<PostLike> existingLike = postLikeRepository.findByPostAndMember(post, likerMember);
        if (existingLike.isPresent()) {
            throw new PostException(ErrorStatus.ALREADY_LIKED);
        }

        PostLike newPostLike = PostLike.builder()
                .post(post)
                .member(likerMember)
                .build();
        newPostLike = postLikeRepository.save(newPostLike);

        post.setLikeCount(post.getLikeCount() + 1);

        postRepository.save(post);

        return PostConverter.toPostLikeResult(newPostLike, post);
    }

    @Override
    @Transactional
    public PostResponseDTO.PostUnlikeResult postUnlike(String memberIdString, Long postId) {

        Long memberId = Long.parseLong(memberIdString);

        Member unlikerMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new PostException(ErrorStatus.NO_SUCH_MEMBER));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorStatus.POST_NOT_FOUND));

        PostLike existingLike = postLikeRepository.findByPostAndMember(post, unlikerMember)
                .orElseThrow(() -> new PostException(ErrorStatus.NOT_LIKED_YET));

        postLikeRepository.delete(existingLike);

        if (post.getLikeCount() > 0) {
            post.setLikeCount(post.getLikeCount() - 1);
        }
        postRepository.save(post);

        return PostConverter.toPostUnlikeResult(post.getId(), unlikerMember.getId(), post.getLikeCount());
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponseDTO.FindCommentsResult findComments(String memberIdString, Long postId, PostRequestDTO.FindCommentsDTO request) {

        Long memberId = Long.parseLong(memberIdString);

        memberRepository.findById(memberId)
                .orElseThrow(() -> new PostException(ErrorStatus.NO_SUCH_MEMBER));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorStatus.POST_NOT_FOUND));

        int page = request.getPage();
        long size = request.getSize();

        if (page < 0) {
            throw new PostException(ErrorStatus.PAGE_SIZE_UNDER_ONE);
        }
        if (size <= 0) {
            throw new PostException(ErrorStatus.PAGE_UNDER_ONE);
        }

        Pageable pageable = PageRequest.of(page, (int) size, Sort.by("createdAt").ascending());

        Page<Comment> commentPage = commentRepository.findByPost(post, pageable);

        return PostConverter.toFindCommentsResult(commentPage);
    }

    @Override
    @Transactional
    public PostResponseDTO.CreateCommentsResult createComments(String memberIdString, Long postId, PostRequestDTO.CreateCommentsDTO request) {

        Long memberId = Long.parseLong(memberIdString);

        Member authorMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new PostException(ErrorStatus.NO_SUCH_MEMBER));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorStatus.POST_NOT_FOUND));

        Comment newComment = Comment.builder()
                .content(request.getContent())
                .post(post)
                .member(authorMember)
                .build();

        newComment = commentRepository.save(newComment);

        return PostConverter.toCreateCommentsResult(newComment);
    }

    @Override
    @Transactional
    public PostResponseDTO.UpdateCommentsResult updateComments(String memberIdString, Long postId, Long commentId, PostRequestDTO.UpdateCommentsDTO request) {

        Long memberId = Long.parseLong(memberIdString);

        Member requesterMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new PostException(ErrorStatus.NO_SUCH_MEMBER));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorStatus.POST_NOT_FOUND));

        Comment comment = commentRepository.findByPostAndId(post, commentId)
                .orElseThrow(() -> new PostException(ErrorStatus.COMMENT_NOT_FOUND)); // 새로운 에러 코드 필요

        if (!comment.getMember().getId().equals(requesterMember.getId())) {
            throw new PostException(ErrorStatus._FORBIDDEN);
        }

        comment.setContent(request.getContent());
        commentRepository.save(comment);

        return PostConverter.toUpdateCommentsResult(comment);
    }

    @Override
    @Transactional
    public void deleteComments(String memberIdString, Long postId, Long commentId) {

        Long memberId = Long.parseLong(memberIdString);

        Member requesterMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new PostException(ErrorStatus.NO_SUCH_MEMBER));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorStatus.POST_NOT_FOUND));

        Comment comment = commentRepository.findByPostAndId(post, commentId)
                .orElseThrow(() -> new PostException(ErrorStatus.COMMENT_NOT_FOUND));

        if (!comment.getMember().getId().equals(requesterMember.getId())) {
            throw new PostException(ErrorStatus._FORBIDDEN);
        }

        commentRepository.delete(comment);
    }
}
