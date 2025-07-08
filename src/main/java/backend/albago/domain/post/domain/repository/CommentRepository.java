package backend.albago.domain.post.domain.repository;

import backend.albago.domain.post.domain.entity.Comment;
import backend.albago.domain.post.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByPost(Post post, Pageable pageable);

    Optional<Comment> findByPostAndId(Post post, Long id);

    List<Comment> findByPost(Post post);
}
