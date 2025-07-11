package backend.albago.domain.team.application;

import backend.albago.domain.member.domain.entity.Member;
import backend.albago.domain.member.domain.repository.MemberRepository;
import backend.albago.domain.team.converter.TeamPostConverter;
import backend.albago.domain.team.domain.entity.Team;
import backend.albago.domain.team.domain.entity.TeamComment;
import backend.albago.domain.team.domain.entity.TeamMember;
import backend.albago.domain.team.domain.entity.TeamPost;
import backend.albago.domain.team.domain.repository.TeamCommentRepository;
import backend.albago.domain.team.domain.repository.TeamMemberRepository;
import backend.albago.domain.team.domain.repository.TeamPostRepository;
import backend.albago.domain.team.domain.repository.TeamRepository;
import backend.albago.domain.team.dto.TeamPostRequestDTO;
import backend.albago.domain.team.dto.TeamPostResponseDTO;
import backend.albago.domain.team.exception.TeamException;
import backend.albago.global.error.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamPostServiceImpl implements TeamPostService {

    private final TeamPostRepository teamPostRepository;
    private final TeamRepository teamRepository;
    private final TeamCommentRepository teamCommentRepository;
    private final MemberRepository memberRepository;
    private final TeamMemberRepository teamMemberRepository;

    @Override
    @Transactional(readOnly = true)
    public TeamPostResponseDTO.FindTeamPostsResult findTeamPosts(Long teamId, TeamPostRequestDTO.FindTeamPostsDTO request, String memberIdString) {

        validateMemberAndTeam(memberIdString, teamId);

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamException(ErrorStatus.TEAM_NOT_FOUND));

        int page = request.getPage();
        int size = request.getSize();

        if (page < 0) {
            throw new TeamException(ErrorStatus.PAGE_NUMBER_UNDER_ZERO);
        }
        if (size <= 0) {
            throw new TeamException(ErrorStatus.PAGE_SIZE_UNDER_ONE);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<TeamPost> teamPostPage = teamPostRepository.findByTeam(team, pageable);

        return TeamPostConverter.toFindTeamPostsResult(teamPostPage);
    }

    @Override
    @Transactional(readOnly = true)
    public TeamPostResponseDTO.FindTeamPostResult findTeamPost(Long teamId, Long teamPostId, String memberIdString) {

        validateMemberAndTeam(memberIdString, teamId);

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamException(ErrorStatus.TEAM_NOT_FOUND));

        TeamPost teamPost = teamPostRepository.findByTeamAndId(team, teamPostId)
                .orElseThrow(() -> new TeamException(ErrorStatus.TEAM_POST_NOT_FOUND));

        return TeamPostConverter.toFindTeamPostResult(teamPost);
    }

    @Override
    @Transactional
    public TeamPostResponseDTO.CreateTeamPostResult createTeamPost(Long teamId, TeamPostRequestDTO.CreateTeamPostDTO request, String memberIdString) {

        Member authorMember = validateMemberAndTeam(memberIdString, teamId);

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamException(ErrorStatus.TEAM_NOT_FOUND));

        TeamPost newTeamPost = TeamPost.builder()
                .team(team)
                .member(authorMember)
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        newTeamPost = teamPostRepository.save(newTeamPost);

        return TeamPostConverter.toCreateTeamPostResult(newTeamPost);
    }

    @Override
    @Transactional
    public TeamPostResponseDTO.UpdateTeamPostResult updateTeamPost(Long teamId, Long teamPostId, TeamPostRequestDTO.UpdateTeamPostDTO request, String memberIdString) {

        validateMemberAndTeam(memberIdString, teamId);

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamException(ErrorStatus.TEAM_NOT_FOUND));

        TeamPost teamPost = teamPostRepository.findByTeamAndId(team, teamPostId)
                .orElseThrow(() -> new TeamException(ErrorStatus.TEAM_POST_NOT_FOUND));

        if (request.getTitle() != null && !request.getTitle().isBlank()) {
            teamPost.setTitle(request.getTitle());
        }
        if (request.getContent() != null && !request.getContent().isBlank()) {
            teamPost.setContent(request.getContent());
        }

        return TeamPostConverter.toUpdateTeamPostResult(teamPost);
    }

    @Override
    @Transactional
    public void deleteTeamPost(Long teamId, Long teamPostId, String memberIdString) {

        validateMemberAndTeam(memberIdString, teamId);

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamException(ErrorStatus.TEAM_NOT_FOUND));

        TeamPost teamPost = teamPostRepository.findByTeamAndId(team, teamPostId)
                .orElseThrow(() -> new TeamException(ErrorStatus.TEAM_POST_NOT_FOUND));

        List<TeamComment> commentsToDelete = teamCommentRepository.findAllByTeamPost(teamPost);
        if (!commentsToDelete.isEmpty()) {
            teamCommentRepository.deleteAll(commentsToDelete);
        }

        teamPostRepository.delete(teamPost);
    }


    @Override
    @Transactional(readOnly = true)
    public TeamPostResponseDTO.findTeamPostsCommentResult findTeamPostsComment(Long teamId, Long teamPostId, TeamPostRequestDTO.FindTeamPostCommentDTO request, String memberIdString) {

        validateMemberAndTeam(memberIdString, teamId);

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamException(ErrorStatus.TEAM_NOT_FOUND));

        TeamPost teamPost = teamPostRepository.findByTeamAndId(team, teamPostId)
                .orElseThrow(() -> new TeamException(ErrorStatus.TEAM_POST_NOT_FOUND));

        int page = request.getPage();
        int size = request.getSize();

        if (page < 0) {
            throw new TeamException(ErrorStatus.PAGE_NUMBER_UNDER_ZERO);
        }
        if (size <= 0) {
            throw new TeamException(ErrorStatus.PAGE_SIZE_UNDER_ONE);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());
        Page<TeamComment> commentPage = teamCommentRepository.findByTeamPost(teamPost, pageable);

        return TeamPostConverter.toFindTeamPostsCommentResult(commentPage);
    }

    @Override
    @Transactional
    public TeamPostResponseDTO.CreateTeamPostCommentResult createTeamPostComment(Long teamId, Long teamPostId, TeamPostRequestDTO.CreateTeamPostCommentDTO request, String memberIdString) {

        Member authorMember = validateMemberAndTeam(memberIdString, teamId);

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamException(ErrorStatus.TEAM_NOT_FOUND));

        TeamPost teamPost = teamPostRepository.findByTeamAndId(team, teamPostId)
                .orElseThrow(() -> new TeamException(ErrorStatus.TEAM_POST_NOT_FOUND));

        TeamComment newComment = TeamComment.builder()
                .teamPost(teamPost)
                .member(authorMember)
                .content(request.getContent())
                .build();

        newComment = teamCommentRepository.save(newComment);

        return TeamPostConverter.toCreateTeamPostCommentResult(newComment);
    }

    @Override
    @Transactional
    public TeamPostResponseDTO.UpdateTeamPostCommentResult updateTeamPostComment(Long teamId, Long teamPostId, Long commentId, TeamPostRequestDTO.UpdateTeamPostCommentDTO request, String memberIdString) {

        validateMemberAndTeam(memberIdString, teamId);

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamException(ErrorStatus.TEAM_NOT_FOUND));

        TeamPost teamPost = teamPostRepository.findByTeamAndId(team, teamPostId)
                .orElseThrow(() -> new TeamException(ErrorStatus.TEAM_POST_NOT_FOUND));

        TeamComment teamComment = teamCommentRepository.findByTeamPostAndId(teamPost, commentId)
                .orElseThrow(() -> new TeamException(ErrorStatus.TEAM_POST_COMMENT_NOT_FOUND));

        if (request.getContent() != null && !request.getContent().isBlank()) {
            teamComment.setContent(request.getContent());
        }

        return TeamPostConverter.toUpdateTeamPostCommentResult(teamComment);
    }

    @Override
    @Transactional
    public void deleteTeamPostComment(Long teamId, Long teamPostId, Long commentId, String memberIdString) {

        validateMemberAndTeam(memberIdString, teamId);

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamException(ErrorStatus.TEAM_NOT_FOUND));

        TeamPost teamPost = teamPostRepository.findByTeamAndId(team, teamPostId)
                .orElseThrow(() -> new TeamException(ErrorStatus.TEAM_POST_NOT_FOUND));

        TeamComment teamComment = teamCommentRepository.findByTeamPostAndId(teamPost, commentId)
                .orElseThrow(() -> new TeamException(ErrorStatus.TEAM_POST_COMMENT_NOT_FOUND));

        teamCommentRepository.delete(teamComment);
    }

    private Member validateMemberAndTeam(String memberIdString, Long teamId) {

        Long memberId = Long.parseLong(memberIdString);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new TeamException(ErrorStatus.NO_SUCH_MEMBER));

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamException(ErrorStatus.TEAM_NOT_FOUND));

        Optional<TeamMember> teamMember = teamMemberRepository.findByMemberAndTeam(member, team);
        if (teamMember.isEmpty()) {
            throw new TeamException(ErrorStatus.MEMBER_NOT_IN_TEAM);
        }

        return member;
    }
}
