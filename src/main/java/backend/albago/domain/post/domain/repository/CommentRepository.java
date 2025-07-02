package backend.albago.domain.post.domain.repository;

import backend.albago.domain.post.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
