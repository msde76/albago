package backend.albago.domain.team.domain.repository;

import backend.albago.domain.member.domain.entity.Member;
import backend.albago.domain.team.domain.entity.Team;
import backend.albago.domain.team.domain.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {

    List<TeamMember> findByMember(Member member);

    Optional<TeamMember> findByMemberAndTeamId(Member member, Long teamId);

    List<TeamMember> findByTeam(Team team);

    Optional<TeamMember> findByTeamAndMember(Team team, Member member);
}
