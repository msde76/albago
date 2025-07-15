package backend.albago.domain.substitution.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class SubstitutionRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateSubstitutionDTO {
        private LocalDateTime timeRangeStart;
        private LocalDateTime timeRangeEnd;
        private Long substituteId;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CheckAvailabilityDTO {
        private LocalDateTime timeRangeStart;
        private LocalDateTime timeRangeEnd;
    }
}
