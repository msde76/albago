package backend.albago.domain.memo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MemoResponseDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MemoDetailDTO {
        private Long memoId;
        private Long memberId;
        private String memberName;
        private Long teamId;
        private String teamName;
        private Long personalScheduleId;
        private String content;
        private LocalDate date;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MemoCreateResult {
        private Long memoId;
        private Long memberId;
        private Long teamId;
        private Long personalScheduleId;
        private LocalDate date;
        private LocalDateTime createdAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FindMemosResult {
        private List<MemoDetailDTO> memos;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FindMemoResult {
        private MemoDetailDTO memo;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdateMemoResult {
        private Long memoId;
        private Long memberId;
        private LocalDate date;
        private LocalDateTime updatedAt;
        private String updatedContent;
    }
}