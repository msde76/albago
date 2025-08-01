package backend.albago.domain.member.domain.repository;

import backend.albago.domain.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findById(Long memberId);

    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);
}
