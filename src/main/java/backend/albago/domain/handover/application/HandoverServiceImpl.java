package backend.albago.domain.handover.application;

import backend.albago.domain.handover.converter.HandoverConverter;
import backend.albago.domain.handover.domain.entity.HandoverNote;
import backend.albago.domain.handover.domain.repository.HandoverNoteRepository;
import backend.albago.domain.handover.dto.HandoverRequestDTO;
import backend.albago.domain.handover.dto.HandoverResponseDTO;
import backend.albago.domain.handover.exception.HandoverException;
import backend.albago.domain.member.domain.entity.Member;
import backend.albago.domain.member.domain.repository.MemberRepository;
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

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class HandoverServiceImpl implements HandoverService {

    private final HandoverNoteRepository handoverRepository;
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;


    @Override
    @Transactional
    public HandoverResponseDTO.CreateHandoverResult createHandover(Long teamId, HandoverRequestDTO.CreateHandoverDTO request, String memberIdString) {

        Member authorMember = validateMemberAndTeam(memberIdString, teamId);

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TEAM_NOT_FOUND));

        HandoverNote newHandover = HandoverNote.builder()
                .team(team)
                .member(authorMember)
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        newHandover = handoverRepository.save(newHandover);

        return HandoverConverter.toCreateHandoverResult(newHandover);
    }

    @Override
    @Transactional(readOnly = true)
    public HandoverResponseDTO.FindHandoversResult findHandovers(Long teamId, String memberIdString) {

        validateMemberAndTeam(memberIdString, teamId);

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TEAM_NOT_FOUND));

        List<HandoverNote> handovers = handoverRepository.findByTeamOrderByCreatedAtDesc(team);

        return HandoverConverter.toFindHandoversResult(handovers);
    }

    @Override
    @Transactional(readOnly = true)
    public HandoverResponseDTO.FindHandoverResult findHandover(Long teamId, Long noteId, String memberIdString) {

        validateMemberAndTeam(memberIdString, teamId);

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TEAM_NOT_FOUND));

        HandoverNote handover = handoverRepository.findByTeamAndId(team, noteId)
                .orElseThrow(() -> new HandoverException(ErrorStatus.HANDOVER_NOT_FOUND));

        return HandoverConverter.toFindHandoverResult(handover);
    }

    @Override
    @Transactional
    public HandoverResponseDTO.UpdateHandoverResult updateHandover(Long teamId, Long noteId, HandoverRequestDTO.UpdateHandoverDTO request, String memberIdString) {

        validateMemberAndTeam(memberIdString, teamId);

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TEAM_NOT_FOUND));

        HandoverNote handover = handoverRepository.findByTeamAndId(team, noteId)
                .orElseThrow(() -> new HandoverException(ErrorStatus.HANDOVER_NOT_FOUND));

        if (request.getTitle() != null && !request.getTitle().isBlank()) {
            handover.setTitle(request.getTitle());
        }
        if (request.getContent() != null && !request.getContent().isBlank()) {
            handover.setContent(request.getContent());
        }

        handoverRepository.save(handover);

        return HandoverConverter.toUpdateHandoverResult(handover);
    }


    @Override
    @Transactional
    public void deleteHandover(Long teamId, Long noteId, String memberIdString) {

        validateMemberAndTeam(memberIdString, teamId);

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TEAM_NOT_FOUND));


        HandoverNote handover = handoverRepository.findByTeamAndId(team, noteId)
                .orElseThrow(() -> new HandoverException(ErrorStatus.HANDOVER_NOT_FOUND));

        handoverRepository.delete(handover);
    }


    private Member validateMemberAndTeam(String memberIdString, Long teamId) {

        Long memberId = Long.parseLong(memberIdString);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.NO_SUCH_MEMBER));

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TEAM_NOT_FOUND));

        Optional<TeamMember> teamMember = teamMemberRepository.findByMemberAndTeam(member, team);
        if (teamMember.isEmpty()) {
            throw new GeneralException(ErrorStatus.MEMBER_NOT_IN_TEAM);
        }

        return member;
    }
}
