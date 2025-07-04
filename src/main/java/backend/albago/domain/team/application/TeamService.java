package backend.albago.domain.team.application;

import backend.albago.domain.team.dto.TeamRequestDTO;
import backend.albago.domain.team.dto.TeamResponseDTO;

import java.util.List;

public interface TeamService {

    TeamResponseDTO.TeamCreateResult createTeam(TeamRequestDTO.TeamCreateDTO request, String memberId);

    List<TeamResponseDTO.TeamListResult> getTeams(String memberId);

    TeamResponseDTO.TeamResult getTeam(Long teamId, String memberId);

    TeamResponseDTO.TeamResult updateTeam(Long teamId, String memberId, TeamRequestDTO.TeamUpdateDTO request);

    void deleteTeam(Long teamId, String memberId);

    TeamResponseDTO.TeamInviteResult inviteTeam(Long teamId, String memberId, TeamRequestDTO.InviteMemberDTO request);

    TeamResponseDTO.UpdateTeamMemberResult updateTeamMember(Long teamId, Long teamMemberId, String memberId, TeamRequestDTO.UpdateTeamMemberDTO request);

    void deleteTeamMember(Long teamId, Long teamMemberId, String memberId);

    TeamResponseDTO.TeamInviteAcceptResult inviteTeamAccept(Long teamId, Long teamMemberId, String memberId);

    void inviteTeamReject(Long teamId, Long teamMemberId, String memberId);

}
