package backend.albago.domain.team.domain.repository;

import backend.albago.domain.team.domain.entity.Team;
import backend.albago.domain.team.domain.entity.TeamPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamPostRepository extends JpaRepository<TeamPost, Long> {

    Page<TeamPost> findByTeam(Team team, Pageable pageable);

    Optional<TeamPost> findByTeamAndId(Team team, Long id);
}
