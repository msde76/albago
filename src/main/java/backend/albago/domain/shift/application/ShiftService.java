package backend.albago.domain.shift.application;

import backend.albago.domain.shift.dto.ShiftRequestDTO;
import backend.albago.domain.shift.dto.ShiftResponseDTO;

import java.time.LocalDate;

public interface ShiftService {

    ShiftResponseDTO.ClockInResult clockIn(Long teamId, String memberIdString, ShiftRequestDTO.ClockInDTO request);

    ShiftResponseDTO.ClockOutResult clockOut(Long teamId, String memberIdString, ShiftRequestDTO.ClockOutDTO request);

    ShiftResponseDTO.findShiftsResult findShifts(Long teamId, String memberIdString, LocalDate startDate, LocalDate endDate);

    ShiftResponseDTO.findTeamShiftsResult findTeamShifts(Long teamId, String memberIdString, LocalDate startDate, LocalDate endDate);
}

