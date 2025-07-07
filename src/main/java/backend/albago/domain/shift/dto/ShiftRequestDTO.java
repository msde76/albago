package backend.albago.domain.shift.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ShiftRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ClockInDTO {
        private Double currentLatitude;
        private Double currentLongitude;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ClockOutDTO {
        private String payLocation;
        private Double currentLatitude;
        private Double currentLongitude;
    }
}
