package backend.albago.domain.substitution.converter;

import backend.albago.domain.member.domain.entity.Member;
import backend.albago.domain.substitution.domain.entity.SubstitutionRequest;
import backend.albago.domain.substitution.dto.SubstitutionResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class SubstitutionConverter {

    public static SubstitutionResponseDTO.SubstitutionDetailDTO toSubstitutionDetailDTO(SubstitutionRequest request) {
        return SubstitutionResponseDTO.SubstitutionDetailDTO.builder()
                .requestId(request.getId())
                .teamId(request.getTeam().getId())
                .requesterId(request.getRequester().getId())
                .requesterName(request.getRequester().getName())
                .substituteId(request.getSubstitute() != null ? request.getSubstitute().getId() : null)
                .substituteName(request.getSubstitute() != null ? request.getSubstitute().getName() : null)
                .timeRangeStart(request.getTimeRangeStart())
                .timeRangeEnd(request.getTimeRangeEnd())
                .status(request.getStatus())
                .createdAt(request.getCreatedAt())
                .updatedAt(request.getUpdatedAt())
                .build();
    }

    public static SubstitutionResponseDTO.CreateSubstitutionResult toCreateSubstitutionResult(SubstitutionRequest request) {
        return SubstitutionResponseDTO.CreateSubstitutionResult.builder()
                .requestId(request.getId())
                .teamId(request.getTeam().getId())
                .requesterId(request.getRequester().getId())
                .substituteId(request.getSubstitute() != null ? request.getSubstitute().getId() : null)
                .timeRangeStart(request.getTimeRangeStart())
                .timeRangeEnd(request.getTimeRangeEnd())
                .status(request.getStatus())
                .createdAt(request.getCreatedAt())
                .build();
    }

    public static SubstitutionResponseDTO.FindRequestSubstitutionResult toFindRequestSubstitutionResult(List<SubstitutionRequest> requests) {
        List<SubstitutionResponseDTO.SubstitutionDetailDTO> requestDetailList = requests.stream()
                .map(SubstitutionConverter::toSubstitutionDetailDTO)
                .collect(Collectors.toList());
        return SubstitutionResponseDTO.FindRequestSubstitutionResult.builder()
                .requests(requestDetailList)
                .build();
    }

    public static SubstitutionResponseDTO.FindReceiveSubstitutionResult toFindReceiveSubstitutionResult(List<SubstitutionRequest> requests) {
        List<SubstitutionResponseDTO.SubstitutionDetailDTO> requestDetailList = requests.stream()
                .map(SubstitutionConverter::toSubstitutionDetailDTO)
                .collect(Collectors.toList());
        return SubstitutionResponseDTO.FindReceiveSubstitutionResult.builder()
                .requests(requestDetailList)
                .build();
    }

    public static SubstitutionResponseDTO.FindSubstitutionResult toFindSubstitutionResult(SubstitutionRequest request) {
        return SubstitutionResponseDTO.FindSubstitutionResult.builder()
                .request(toSubstitutionDetailDTO(request))
                .build();
    }

    public static SubstitutionResponseDTO.AcceptSubstitutionResult toAcceptSubstitutionResult(SubstitutionRequest request) {
        return SubstitutionResponseDTO.AcceptSubstitutionResult.builder()
                .requestId(request.getId())
                .teamId(request.getTeam().getId())
                .requesterId(request.getRequester().getId())
                .substituteId(request.getSubstitute() != null ? request.getSubstitute().getId() : null)
                .status(request.getStatus())
                .updatedAt(request.getUpdatedAt())
                .build();
    }

    public static SubstitutionResponseDTO.RejectSubstitutionResult toRejectSubstitutionResult(SubstitutionRequest request) {
        return SubstitutionResponseDTO.RejectSubstitutionResult.builder()
                .requestId(request.getId())
                .teamId(request.getTeam().getId())
                .requesterId(request.getRequester().getId())
                .substituteId(request.getSubstitute() != null ? request.getSubstitute().getId() : null)
                .status(request.getStatus())
                .updatedAt(request.getUpdatedAt())
                .build();
    }

    public static SubstitutionResponseDTO.AvailableMemberDTO toAvailableMemberDTO(Member member) {
        return SubstitutionResponseDTO.AvailableMemberDTO.builder()
                .memberId(member.getId())
                .memberName(member.getName())
                .build();
    }

    public static SubstitutionResponseDTO.CheckAvailabilityResult toCheckAvailabilityResult(List<Member> availableMembers) {
        List<SubstitutionResponseDTO.AvailableMemberDTO> memberList = availableMembers.stream()
                .map(SubstitutionConverter::toAvailableMemberDTO)
                .collect(Collectors.toList());
        return SubstitutionResponseDTO.CheckAvailabilityResult.builder()
                .availableMembers(memberList)
                .build();
    }
}
