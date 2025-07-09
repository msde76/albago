package backend.albago.domain.handover.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class HandoverResponseDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class HandoverDetailDTO {
        private Long noteId;
        private Long teamId;
        private Long memberId;
        private String memberName;
        private String title;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateHandoverResult {
        private Long noteId;
        private Long teamId;
        private Long memberId;
        private String title;
        private String content;
        private LocalDateTime createdAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FindHandoversResult {
        private List<HandoverDetailDTO> handovers;
        // 목록 조회이므로 페이징 정보도 추가할 수 있습니다 (현재는 리스트만 반환).
        // private int currentPage;
        // private int totalPages;
        // private long totalElements;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FindHandoverResult {
        private HandoverDetailDTO handover;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdateHandoverResult {
        private Long noteId;
        private Long teamId;
        private Long memberId;
        private String title;
        private String content;
        private LocalDateTime updatedAt;
    }
}
