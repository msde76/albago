package backend.albago.domain.team.application;

import backend.albago.domain.member.domain.entity.Member;
import backend.albago.domain.member.domain.repository.MemberRepository;
import backend.albago.domain.model.enums.PositionStatus;
import backend.albago.domain.team.converter.TeamConverter;
import backend.albago.domain.team.domain.entity.Team;
import backend.albago.domain.team.domain.entity.TeamMember;
import backend.albago.domain.team.domain.repository.TeamMemberRepository;
import backend.albago.domain.team.domain.repository.TeamRepository;
import backend.albago.domain.team.dto.TeamRequestDTO;
import backend.albago.domain.team.dto.TeamResponseDTO;
import backend.albago.domain.team.exception.TeamException;
import backend.albago.global.error.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    private final TeamMemberRepository teamMemberRepository;

    @Override
    @Transactional
    public TeamResponseDTO.TeamCreateResult createTeam(TeamRequestDTO.TeamCreateDTO request, String memberId){

        Long memberIdLong = Long.parseLong(memberId);

        Member ownerMember = memberRepository.findById(memberIdLong)
                .orElseThrow(() -> new TeamException(ErrorStatus.NO_SUCH_MEMBER));

        Team newTeam = TeamConverter.toTeam(request, ownerMember);

        Team savedTeam = teamRepository.save(newTeam);

        TeamMember ownerAsTeamMember = TeamConverter.toTeamMember(
                savedTeam,
                ownerMember,
                PositionStatus.Manager,
                BigDecimal.ZERO
        );

        teamMemberRepository.save(ownerAsTeamMember);

        return TeamConverter.toTeamCreateResult(savedTeam);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeamResponseDTO.TeamListResult> getTeams(String memberId){

        Long memberIdLong = Long.parseLong(memberId);

        Member member = memberRepository.findById(memberIdLong)
                .orElseThrow(() -> new TeamException(ErrorStatus.NO_SUCH_MEMBER));

        List<TeamMember> teamMembers = teamMemberRepository.findByMember(member);

        return TeamConverter.toTeamListResultList(teamMembers);
    }

    @Override
    @Transactional
    public TeamResponseDTO.TeamResult getTeam(Long teamId, String memberId){

        Long memberIdLong = Long.parseLong(memberId);

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamException(ErrorStatus.TEAM_NOT_FOUND));

        memberRepository.findById(memberIdLong)
                .orElseThrow(() -> new TeamException(ErrorStatus.NO_SUCH_MEMBER));

        boolean isMemberOfTeam = teamMemberRepository.findByTeamAndMemberId(team, memberIdLong).isPresent();
        if (!isMemberOfTeam) {
            throw new TeamException(ErrorStatus._FORBIDDEN);
        }

        List<TeamMember> teamMembersInTeam = teamMemberRepository.findByTeam(team);

        return TeamConverter.toTeamResult(team, teamMembersInTeam);
    }

    @Override
    @Transactional
    public TeamResponseDTO.TeamResult updateTeam(Long teamId, String memberId, TeamRequestDTO.TeamUpdateDTO request){

        Long memberIdLong = Long.parseLong(memberId);

        Member requestMember = memberRepository.findById(memberIdLong)
                .orElseThrow(() -> new TeamException(ErrorStatus.NO_SUCH_MEMBER));

        Team teamToUpdate = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamException(ErrorStatus.TEAM_NOT_FOUND));

        if (!teamToUpdate.getOwnerMember().getId().equals(requestMember.getId())) {
            throw new TeamException(ErrorStatus._FORBIDDEN);
        }

        Optional.ofNullable(request.getTeamName()).ifPresent(teamToUpdate::setTeamName);
        Optional.ofNullable(request.getImageUrl()).ifPresent(teamToUpdate::setImageUrl);
        Optional.ofNullable(request.getColor()).ifPresent(teamToUpdate::setColor);

        List<TeamMember> teamMembersInTeam = teamMemberRepository.findByTeam(teamToUpdate); // Fetch Join 사용

        return TeamConverter.toTeamResult(teamToUpdate, teamMembersInTeam);
    }

    @Override
    @Transactional
    public void deleteTeam(Long teamId, String memberId){

        Long memberIdLong = Long.parseLong(memberId);

        Member requestMember = memberRepository.findById(memberIdLong)
                .orElseThrow(() -> new TeamException(ErrorStatus.NO_SUCH_MEMBER));

        Team teamToDelete = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamException(ErrorStatus.TEAM_NOT_FOUND));

        if (!teamToDelete.getOwnerMember().getId().equals(requestMember.getId())) {
            throw new TeamException(ErrorStatus._FORBIDDEN);
        }

        List<TeamMember> teamMembers = teamMemberRepository.findByTeam(teamToDelete);
        if (!teamMembers.isEmpty()) {
            teamMemberRepository.deleteAll(teamMembers);
        }

        teamRepository.delete(teamToDelete);
    }

    @Override
    @Transactional
    public TeamResponseDTO.TeamInviteResult inviteTeam(Long teamId, String memberId, TeamRequestDTO.InviteMemberDTO request){

        Long memberIdLong = Long.parseLong(memberId);

        Member inviterMember = memberRepository.findById(memberIdLong)
                .orElseThrow(() -> new TeamException(ErrorStatus.NO_SUCH_MEMBER));

        Team teamToInvite = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamException(ErrorStatus.TEAM_NOT_FOUND));

        if (!teamToInvite.getOwnerMember().getId().equals(inviterMember.getId())) {
            throw new TeamException(ErrorStatus._FORBIDDEN);
        }

        Member invitedMember = memberRepository.findByEmail(request.getMemberEmail())
                .orElseThrow(() -> new TeamException(ErrorStatus.NO_SUCH_MEMBER_BY_EMAIL));

        if (invitedMember.getId().equals(inviterMember.getId())) {
            throw new TeamException(ErrorStatus.CANNOT_INVITE_SELF);
        }

        if (teamMemberRepository.findByTeamAndMember(teamToInvite, invitedMember).isPresent()) {
            throw new TeamException(ErrorStatus.ALREADY_TEAM_MEMBER);
        }

        TeamMember newTeamMember = TeamConverter.toTeamMember(teamToInvite, invitedMember, request);

        TeamMember savedTeamMember = teamMemberRepository.save(newTeamMember);

        return TeamConverter.toTeamInviteResult(savedTeamMember);
    }

    @Override
    @Transactional
    public TeamResponseDTO.UpdateTeamMemberResult updateTeamMember(Long teamId, Long teamMemberId, String memberId, TeamRequestDTO.UpdateTeamMemberDTO request){

        Long requestMemberId = Long.parseLong(memberId);

        Member requestMember = memberRepository.findById(requestMemberId)
                .orElseThrow(() -> new TeamException(ErrorStatus.NO_SUCH_MEMBER));

        Team targetTeam = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamException(ErrorStatus.TEAM_NOT_FOUND));

        if (!targetTeam.getOwnerMember().getId().equals(requestMember.getId())) {
            throw new TeamException(ErrorStatus._FORBIDDEN);
        }

        TeamMember teamMemberToUpdate = teamMemberRepository.findById(teamMemberId)
                .orElseThrow(() -> new TeamException(ErrorStatus.TEAM_MEMBER_NOT_FOUND));

        if (!teamMemberToUpdate.getTeam().getId().equals(teamId)) {
            throw new TeamException(ErrorStatus.TEAM_MEMBER_NOT_IN_TEAM);
        }

        Optional.ofNullable(request.getPosition()).ifPresent(teamMemberToUpdate::setPosition);
        Optional.ofNullable(request.getSalary()).ifPresent(teamMemberToUpdate::setSalary);
        Optional.ofNullable(request.getWorkHours()).ifPresent(teamMemberToUpdate::setWorkHours);
        Optional.ofNullable(request.getBreakHours()).ifPresent(teamMemberToUpdate::setBreakHours);
        Optional.ofNullable(request.getColor()).ifPresent(teamMemberToUpdate::setColor);

        Optional.ofNullable(request.getWeeklyAllowance()).ifPresent(teamMemberToUpdate::setWeeklyAllowance);
        Optional.ofNullable(request.getNightAllowance()).ifPresent(teamMemberToUpdate::setNightAllowance);
        Optional.ofNullable(request.getNightRate()).ifPresent(teamMemberToUpdate::setNightRate);
        Optional.ofNullable(request.getOvertimeAllowance()).ifPresent(teamMemberToUpdate::setOvertimeAllowance);
        Optional.ofNullable(request.getOvertimeRate()).ifPresent(teamMemberToUpdate::setOvertimeRate);
        Optional.ofNullable(request.getHolidayAllowance()).ifPresent(teamMemberToUpdate::setHolidayAllowance);
        Optional.ofNullable(request.getHolidayRate()).ifPresent(teamMemberToUpdate::setHolidayRate);
        Optional.ofNullable(request.getDeductions()).ifPresent(teamMemberToUpdate::setDeductions);
        Optional.ofNullable(request.getDailyPay()).ifPresent(teamMemberToUpdate::setDailyPay);
        Optional.ofNullable(request.getHourlyPay()).ifPresent(teamMemberToUpdate::setHourlyPay);
        Optional.ofNullable(request.getWeeklyPay()).ifPresent(teamMemberToUpdate::setWeeklyPay);
        Optional.ofNullable(request.getMonthlyPay()).ifPresent(teamMemberToUpdate::setMonthlyPay);

        teamMemberRepository.save(teamMemberToUpdate);

        return TeamConverter.toUpdateTeamMemberResult(teamMemberToUpdate);
    }

    @Override
    @Transactional
    public void deleteTeamMember(Long teamId, Long teamMemberId, String memberId){

        Long requestMemberId = Long.parseLong(memberId);

        Member requestMember = memberRepository.findById(requestMemberId)
                .orElseThrow(() -> new TeamException(ErrorStatus.NO_SUCH_MEMBER));

        Team targetTeam = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamException(ErrorStatus.TEAM_NOT_FOUND));

        TeamMember teamMemberToDelete = teamMemberRepository.findById(teamMemberId)
                .orElseThrow(() -> new TeamException(ErrorStatus.TEAM_MEMBER_NOT_FOUND));

        if (!teamMemberToDelete.getTeam().getId().equals(teamId)) {
            throw new TeamException(ErrorStatus.TEAM_MEMBER_NOT_IN_TEAM);
        }

        boolean isRequestMemberOwner = targetTeam.getOwnerMember().getId().equals(requestMember.getId());
        boolean isRequestMemberSelf = teamMemberToDelete.getMember().getId().equals(requestMember.getId());
        boolean isTargetMemberOwner = teamMemberToDelete.getMember().getId().equals(targetTeam.getOwnerMember().getId()); // 삭제 대상이 팀장인지

        // Case 1: 요청자가 팀장인 경우 (다른 멤버를 삭제하거나 자기 자신을 삭제하려 할 때)
        if (isRequestMemberOwner) {
            if (isTargetMemberOwner) {
                throw new TeamException(ErrorStatus.CANNOT_DELETE_OWNER_MEMBER);
            }
        }
        // Case 2: 요청자가 팀장이 아닌 경우 (일반 팀원)
        else {
            if (!isRequestMemberSelf) {
                throw new TeamException(ErrorStatus._FORBIDDEN);
            }
        }

        teamMemberRepository.delete(teamMemberToDelete);
    }

    @Override
    @Transactional
    public TeamResponseDTO.TeamInviteAcceptResult inviteTeamAccept(Long teamId, Long teamMemberId, String memberId){

        Long requestMemberId = Long.parseLong(memberId);

        Member acceptingMember = memberRepository.findById(requestMemberId)
                .orElseThrow(() -> new TeamException(ErrorStatus.NO_SUCH_MEMBER));

        // 팀 초대 당시에 생성되어서 조회 후 상태만 변경하면 됨
        TeamMember teamMemberToAccept = teamMemberRepository.findById(teamMemberId)
                .orElseThrow(() -> new TeamException(ErrorStatus.TEAM_MEMBER_NOT_FOUND));

        teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamException(ErrorStatus.TEAM_NOT_FOUND));

        if (!teamMemberToAccept.getTeam().getId().equals(teamId)) {
            throw new TeamException(ErrorStatus.TEAM_MEMBER_NOT_IN_TEAM);
        }

        if (!teamMemberToAccept.getMember().getId().equals(acceptingMember.getId())) {
            throw new TeamException(ErrorStatus._FORBIDDEN);
        }

        if (teamMemberToAccept.getIsAccepted()) {
            throw new TeamException(ErrorStatus.TEAM_INVITE_ALREADY_ACCEPTED);
        }

        teamMemberToAccept.setIsAccepted(true);

        teamMemberRepository.save(teamMemberToAccept);

        return TeamConverter.toTeamInviteAcceptResult(teamMemberToAccept);
    }

    @Override
    @Transactional
    public void inviteTeamReject(Long teamId, Long teamMemberId, String memberId){

        Long requestMemberId = Long.parseLong(memberId);

        Member rejectingMember = memberRepository.findById(requestMemberId)
                .orElseThrow(() -> new TeamException(ErrorStatus.NO_SUCH_MEMBER));

        TeamMember teamMemberToReject = teamMemberRepository.findById(teamMemberId)
                .orElseThrow(() -> new TeamException(ErrorStatus.TEAM_MEMBER_NOT_FOUND));

        teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamException(ErrorStatus.TEAM_NOT_FOUND));

        if (!teamMemberToReject.getTeam().getId().equals(teamId)) {
            throw new TeamException(ErrorStatus.TEAM_MEMBER_NOT_IN_TEAM);
        }

        if (!teamMemberToReject.getMember().getId().equals(rejectingMember.getId())) {
            throw new TeamException(ErrorStatus._FORBIDDEN);
        }

        if (teamMemberToReject.getIsAccepted()) {
            throw new TeamException(ErrorStatus.TEAM_INVITE_ALREADY_ACCEPTED);
        }

        teamMemberRepository.delete(teamMemberToReject);
    }

}
