package backend.albago.domain.handover.domain.repository;

import backend.albago.domain.handover.domain.entity.HandoverNote;
import backend.albago.domain.team.domain.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HandoverNoteRepository extends JpaRepository<HandoverNote, Long> {

    List<HandoverNote> findByTeamOrderByCreatedAtDesc(Team team);

    Optional<HandoverNote> findByTeamAndId(Team team, Long id);
}
