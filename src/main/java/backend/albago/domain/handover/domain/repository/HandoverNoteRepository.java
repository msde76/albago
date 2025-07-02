package backend.albago.domain.handover.domain.repository;

import backend.albago.domain.handover.domain.entity.HandoverNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HandoverNoteRepository extends JpaRepository<HandoverNote, Integer> {
}
