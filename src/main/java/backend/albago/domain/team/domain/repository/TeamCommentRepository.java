package backend.albago.domain.team.domain.repository;

import backend.albago.domain.team.domain.entity.TeamComment;
import backend.albago.domain.team.domain.entity.TeamPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamCommentRepository extends JpaRepository<TeamComment, Long> {

    Page<TeamComment> findByTeamPost(TeamPost teamPost, Pageable pageable);

    List<TeamComment> findAllByTeamPost(TeamPost teamPost);

    Optional<TeamComment> findByTeamPostAndId(TeamPost teamPost, Long id);
}
