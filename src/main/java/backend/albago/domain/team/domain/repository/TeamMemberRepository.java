package backend.albago.domain.team.domain.repository;

import backend.albago.domain.team.domain.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Integer> {
}
