package backend.albago.domain.team.application;

import backend.albago.domain.team.dto.TeamPostRequestDTO;
import backend.albago.domain.team.dto.TeamPostResponseDTO;

public interface TeamPostService {

    TeamPostResponseDTO.FindTeamPostsResult findTeamPosts(Long teamId, TeamPostRequestDTO.FindTeamPostsDTO request, String memberIdString);

    TeamPostResponseDTO.FindTeamPostResult findTeamPost(Long teamId, Long teamPostId, String memberIdString);

    TeamPostResponseDTO.CreateTeamPostResult createTeamPost(Long teamId, TeamPostRequestDTO.CreateTeamPostDTO request, String memberIdString);

    TeamPostResponseDTO.UpdateTeamPostResult updateTeamPost(Long teamId, Long teamPostId, TeamPostRequestDTO.UpdateTeamPostDTO request, String memberIdString);

    void deleteTeamPost(Long teamId, Long teamPostId, String memberIdString);

    TeamPostResponseDTO.findTeamPostsCommentResult findTeamPostsComment(Long teamId, Long teamPostId, TeamPostRequestDTO.FindTeamPostCommentDTO request, String memberIdString);

    TeamPostResponseDTO.CreateTeamPostCommentResult createTeamPostComment(Long teamId, Long teamPostId, TeamPostRequestDTO.CreateTeamPostCommentDTO request, String memberIdString);

    TeamPostResponseDTO.UpdateTeamPostCommentResult updateTeamPostComment(Long teamId, Long teamPostId, Long commentId, TeamPostRequestDTO.UpdateTeamPostCommentDTO request, String memberIdString);

    void deleteTeamPostComment(Long teamId, Long teamPostId, Long commentId, String memberIdString);
}
