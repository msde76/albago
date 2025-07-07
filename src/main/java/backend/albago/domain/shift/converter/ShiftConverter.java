package backend.albago.domain.shift.converter;

import backend.albago.domain.shift.domain.entity.ShiftLog;
import backend.albago.domain.shift.dto.ShiftResponseDTO;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ShiftConverter {

    public static ShiftResponseDTO.ClockInResult toClockInResult(ShiftLog shiftLog, String message) {
        return ShiftResponseDTO.ClockInResult.builder()
                .shiftLogId(shiftLog.getId())
                .memberId(shiftLog.getMember().getId())
                .teamId(shiftLog.getTeam().getId())
                .shiftDate(shiftLog.getShiftDate())
                .clockInTime(shiftLog.getClockInTime())
                .recordedClockInLatitude(shiftLog.getRecordedClockInLatitude())
                .recordedClockInLongitude(shiftLog.getRecordedClockInLongitude())
                .message(message)
                .build();
    }

    public static ShiftResponseDTO.ClockOutResult toClockOutResult(ShiftLog shiftLog, String message) {
        long workDurationMinutes = 0;
        if (shiftLog.getClockInTime() != null && shiftLog.getClockOutTime() != null) {
            Duration duration = Duration.between(shiftLog.getClockInTime(), shiftLog.getClockOutTime());
            workDurationMinutes = duration.toMinutes();
        }

        return ShiftResponseDTO.ClockOutResult.builder()
                .shiftLogId(shiftLog.getId())
                .memberId(shiftLog.getMember().getId())
                .teamId(shiftLog.getTeam().getId())
                .shiftDate(shiftLog.getShiftDate())
                .clockInTime(shiftLog.getClockInTime())
                .clockOutTime(shiftLog.getClockOutTime())
                .workDurationMinutes(workDurationMinutes)
                .payLocation(shiftLog.getPayLocation())
                .recordedClockOutLatitude(shiftLog.getRecordedClockOutLatitude())
                .recordedClockOutLongitude(shiftLog.getRecordedClockOutLongitude())
                .message(message)
                .build();
    }

    public static ShiftResponseDTO.ShiftDetailDTO toShiftDetailDTO(ShiftLog shiftLog) {
        long workDurationMinutes = 0;
        if (shiftLog.getClockInTime() != null && shiftLog.getClockOutTime() != null) {
            Duration duration = Duration.between(shiftLog.getClockInTime(), shiftLog.getClockOutTime());
            workDurationMinutes = duration.toMinutes();
        }

        return ShiftResponseDTO.ShiftDetailDTO.builder()
                .shiftLogId(shiftLog.getId())
                .memberId(shiftLog.getMember().getId())
                .memberName(shiftLog.getMember().getName())
                .shiftDate(shiftLog.getShiftDate())
                .clockInTime(shiftLog.getClockInTime())
                .clockOutTime(shiftLog.getClockOutTime())
                .workDurationMinutes(workDurationMinutes)
                .payLocation(shiftLog.getPayLocation())
                .recordedClockInLatitude(shiftLog.getRecordedClockInLatitude())
                .recordedClockInLongitude(shiftLog.getRecordedClockInLongitude())
                .recordedClockOutLatitude(shiftLog.getRecordedClockOutLatitude())
                .recordedClockOutLongitude(shiftLog.getRecordedClockOutLongitude())
                .build();
    }

    public static ShiftResponseDTO.findShiftsResult toFindShiftsResult(
            Long memberId, Long teamId, LocalDate startDate, LocalDate endDate,
            List<ShiftLog> shiftLogs) {

        List<ShiftResponseDTO.ShiftDetailDTO> shiftDetailDTOs = shiftLogs.stream()
                .map(ShiftConverter::toShiftDetailDTO)
                .collect(Collectors.toList());

        long totalWorkDurationMinutes = shiftDetailDTOs.stream()
                .mapToLong(ShiftResponseDTO.ShiftDetailDTO::getWorkDurationMinutes)
                .sum();

        return ShiftResponseDTO.findShiftsResult.builder()
                .memberId(memberId)
                .teamId(teamId)
                .startDate(startDate)
                .endDate(endDate)
                .shiftLogs(shiftDetailDTOs)
                .totalWorkDurationMinutes(totalWorkDurationMinutes)
                .build();
    }

    public static ShiftResponseDTO.findTeamShiftsResult toFindTeamShiftsResult(
            Long teamId, String teamName, LocalDate startDate, LocalDate endDate,
            List<ShiftLog> shiftLogs) {

        List<ShiftResponseDTO.ShiftDetailDTO> shiftDetailDTOs = shiftLogs.stream()
                .map(ShiftConverter::toShiftDetailDTO) // 재사용
                .collect(Collectors.toList());

        long totalTeamWorkDurationMinutes = shiftDetailDTOs.stream()
                .mapToLong(ShiftResponseDTO.ShiftDetailDTO::getWorkDurationMinutes)
                .sum();

        return ShiftResponseDTO.findTeamShiftsResult.builder()
                .teamId(teamId)
                .teamName(teamName)
                .startDate(startDate)
                .endDate(endDate)
                .teamShiftLogs(shiftDetailDTOs)
                .totalTeamWorkDurationMinutes(totalTeamWorkDurationMinutes)
                .build();
    }
}
