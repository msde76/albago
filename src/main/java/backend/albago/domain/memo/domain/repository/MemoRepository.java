package backend.albago.domain.memo.domain.repository;

import backend.albago.domain.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Member,Long> {
}
