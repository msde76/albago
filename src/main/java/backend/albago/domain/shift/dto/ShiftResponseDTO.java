package backend.albago.domain.shift.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ShiftResponseDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ClockInResult {
        private Long shiftLogId;
        private Long memberId;
        private Long teamId;
        private LocalDate shiftDate;
        private LocalDateTime clockInTime;
        private Double recordedClockInLatitude;
        private Double recordedClockInLongitude;
        private String message;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ClockOutResult {
        private Long shiftLogId;
        private Long memberId;
        private Long teamId;
        private LocalDate shiftDate;
        private LocalDateTime clockInTime;
        private LocalDateTime clockOutTime;
        private Long workDurationMinutes;
        private String payLocation;
        private Double recordedClockOutLatitude;
        private Double recordedClockOutLongitude;
        private String message;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ShiftDetailDTO {
        private Long shiftLogId;
        private Long memberId;
        private String memberName;
        private LocalDate shiftDate;
        private LocalDateTime clockInTime;
        private LocalDateTime clockOutTime;
        private Long workDurationMinutes;
        private String payLocation;
        private Double recordedClockInLatitude;
        private Double recordedClockInLongitude;
        private Double recordedClockOutLatitude;
        private Double recordedClockOutLongitude;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class findShiftsResult {
        private Long memberId;
        private Long teamId;
        private LocalDate startDate;
        private LocalDate endDate;
        private List<ShiftDetailDTO> shiftLogs;
        private Long totalWorkDurationMinutes;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class findTeamShiftsResult {
        private Long teamId;
        private String teamName;
        private LocalDate startDate;
        private LocalDate endDate;
        private List<ShiftDetailDTO> teamShiftLogs;
        private Long totalTeamWorkDurationMinutes;
    }
}
