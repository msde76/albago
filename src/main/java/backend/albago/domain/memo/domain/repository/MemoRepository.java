package backend.albago.domain.memo.domain.repository;

import backend.albago.domain.member.domain.entity.Member;
import backend.albago.domain.memo.domain.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MemoRepository extends JpaRepository<Memo,Long> {

    List<Memo> findByMemberAndDateBetweenOrderByDateAsc(Member member, LocalDate startDate, LocalDate endDate);
}
