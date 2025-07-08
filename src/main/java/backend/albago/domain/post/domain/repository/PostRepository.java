package backend.albago.domain.post.domain.repository;

import backend.albago.domain.post.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<Post> findByLikeCountGreaterThanEqualOrderByLikeCountDescCreatedAtDesc(int likeCount, Pageable pageable);

}
