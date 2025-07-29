package backend.albago.domain.memo.application;

import backend.albago.domain.memo.converter.MemoConverter;
import backend.albago.domain.memo.domain.entity.Memo;
import backend.albago.domain.memo.domain.repository.MemoRepository;
import backend.albago.domain.memo.dto.MemoRequestDTO;
import backend.albago.domain.memo.dto.MemoResponseDTO;
import backend.albago.domain.memo.exception.MemoException;
import backend.albago.domain.member.domain.entity.Member;
import backend.albago.domain.member.domain.repository.MemberRepository;
import backend.albago.domain.schedule.domain.entity.PersonalSchedule;
import backend.albago.domain.schedule.domain.repository.PersonalScheduleRepository;
import backend.albago.domain.team.domain.entity.Team;
import backend.albago.domain.team.domain.entity.TeamMember;
import backend.albago.domain.team.domain.repository.TeamMemberRepository;
import backend.albago.domain.team.domain.repository.TeamRepository;
import backend.albago.global.error.code.status.ErrorStatus;
import backend.albago.global.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemoServiceImpl implements MemoService {

    private final MemoRepository memoRepository;
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final PersonalScheduleRepository personalScheduleRepository;
    private final TeamMemberRepository teamMemberRepository;

    @Override
    @Transactional
    public MemoResponseDTO.MemoCreateResult createMemo(MemoRequestDTO.MemoCreateDTO request, String memberIdString) {

        Long memberId = Long.parseLong(memberIdString);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.NO_SUCH_MEMBER));

        Team team = null;
        if (request.getTeamId() != null) {
            team = teamRepository.findById(request.getTeamId())
                    .orElseThrow(() -> new GeneralException(ErrorStatus.TEAM_NOT_FOUND));

            Optional<TeamMember> teamMember = teamMemberRepository.findByMemberAndTeam(member, team);
            if (teamMember.isEmpty()) {
                throw new GeneralException(ErrorStatus.MEMBER_NOT_IN_TEAM);
            }
        }

        PersonalSchedule personalSchedule = null;
        if (request.getPersonalScheduleId() != null) {
            personalSchedule = personalScheduleRepository.findById(request.getPersonalScheduleId())
                    .orElseThrow(() -> new GeneralException(ErrorStatus.SCHEDULE_NOT_FOUND));

            if (!personalSchedule.getMember().getId().equals(memberId)) {
                throw new MemoException(ErrorStatus.NOT_MY_SCHEDULE);
            }
        }

        if (request.getContent() == null || request.getContent().trim().isEmpty()) {
            throw new MemoException(ErrorStatus.MEMO_CONTENT_EMPTY);
        }
        if (request.getDate() == null) {
            throw new MemoException(ErrorStatus.MEMO_DATE_EMPTY);
        }

        Memo newMemo = Memo.builder()
                .member(member)
                .team(team)
                .personalSchedule(personalSchedule)
                .content(request.getContent())
                .date(request.getDate())
                .build();

        newMemo = memoRepository.save(newMemo);

        return MemoConverter.toMemoCreateResult(newMemo);
    }

    @Override
    @Transactional(readOnly = true)
    public MemoResponseDTO.FindMemosResult findMemos(LocalDate startDate, LocalDate endDate, String memberIdString) {
        Long memberId = Long.parseLong(memberIdString);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.NO_SUCH_MEMBER));

        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            throw new MemoException(ErrorStatus.INVALID_DATE_RANGE);
        }

        List<Memo> memos = memoRepository.findByMemberAndDateBetweenOrderByDateAsc(member, startDate, endDate);

        return MemoConverter.toFindMemosResult(memos);
    }

    @Override
    @Transactional(readOnly = true)
    public MemoResponseDTO.FindMemoResult findMemo(Long memoId, String memberIdString) {
        Long memberId = Long.parseLong(memberIdString);

        Memo memo = memoRepository.findById(memoId)
                .orElseThrow(() -> new MemoException(ErrorStatus.MEMO_NOT_FOUND));

        if (!memo.getMember().getId().equals(memberId)) {
            throw new MemoException(ErrorStatus.NOT_MY_MEMO);
        }

        return MemoConverter.toFindMemoResult(memo);
    }

    @Override
    @Transactional
    public MemoResponseDTO.UpdateMemoResult updateMemo(Long memoId, String memberIdString, MemoRequestDTO.MemoUpdateDTO request) {
        Long memberId = Long.parseLong(memberIdString);

        Memo memo = memoRepository.findById(memoId)
                .orElseThrow(() -> new MemoException(ErrorStatus.MEMO_NOT_FOUND));

        if (!memo.getMember().getId().equals(memberId)) {
            throw new MemoException(ErrorStatus.NOT_MY_MEMO);
        }

        if (request.getContent() != null && !request.getContent().trim().isEmpty()) {
            memo.setContent(request.getContent());
        }

        memoRepository.save(memo);

        return MemoConverter.toUpdateMemoResult(memo);
    }

    @Override
    @Transactional
    public void deleteMemo(Long memoId, String memberIdString) {
        Long memberId = Long.parseLong(memberIdString);

        Memo memo = memoRepository.findById(memoId)
                .orElseThrow(() -> new MemoException(ErrorStatus.MEMO_NOT_FOUND));

        if (!memo.getMember().getId().equals(memberId)) {
            throw new MemoException(ErrorStatus.NOT_MY_MEMO);
        }

        memoRepository.delete(memo);
    }
}