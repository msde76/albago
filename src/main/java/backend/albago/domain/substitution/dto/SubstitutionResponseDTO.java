package backend.albago.domain.substitution.dto;

import backend.albago.domain.model.enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class SubstitutionResponseDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SubstitutionDetailDTO {
        private Long requestId;
        private Long teamId;
        private Long requesterId;
        private String requesterName;
        private Long substituteId; // null일 수 있음
        private String substituteName;
        private LocalDateTime timeRangeStart;
        private LocalDateTime timeRangeEnd;
        private RequestStatus status;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateSubstitutionResult {
        private Long requestId;
        private Long teamId;
        private Long requesterId;
        private Long substituteId; // null일 수 있음
        private LocalDateTime timeRangeStart;
        private LocalDateTime timeRangeEnd;
        private RequestStatus status; // 초기 상태는 PENDING
        private LocalDateTime createdAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FindRequestSubstitutionResult {
        List<SubstitutionDetailDTO> requests;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FindReceiveSubstitutionResult {
        List<SubstitutionDetailDTO> requests;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FindSubstitutionResult {
        private SubstitutionDetailDTO request;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AcceptSubstitutionResult {
        private Long requestId;
        private Long teamId;
        private Long requesterId;
        private Long substituteId;
        private RequestStatus status; // ACCEPTED
        private LocalDateTime updatedAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RejectSubstitutionResult {
        private Long requestId;
        private Long teamId;
        private Long requesterId;
        private Long substituteId;
        private RequestStatus status; // REJECTED
        private LocalDateTime updatedAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AvailableMemberDTO {
        private Long memberId;
        private String memberName;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CheckAvailabilityResult {
        private List<AvailableMemberDTO> availableMembers;
    }
}
