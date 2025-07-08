package backend.albago.domain.post.domain.repository;

import backend.albago.domain.member.domain.entity.Member;
import backend.albago.domain.post.domain.entity.Post;
import backend.albago.domain.post.domain.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    Optional<PostLike> findByPostAndMember(Post post, Member member);

    Long countByPost(Post post);

    void deleteByPostAndMember(Post post, Member member);
}
