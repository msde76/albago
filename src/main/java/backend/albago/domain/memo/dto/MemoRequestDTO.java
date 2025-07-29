package backend.albago.domain.memo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class MemoRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MemoCreateDTO {
        private Long teamId;
        private Long personalScheduleId;
        private String content;
        private LocalDate date;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MemoUpdateDTO {
        private String content;
        private LocalDate date;
    }
}
