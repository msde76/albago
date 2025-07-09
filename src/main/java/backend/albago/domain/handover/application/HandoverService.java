package backend.albago.domain.handover.application;

import backend.albago.domain.handover.dto.HandoverRequestDTO;
import backend.albago.domain.handover.dto.HandoverResponseDTO;

public interface HandoverService {

    HandoverResponseDTO.CreateHandoverResult createHandover(Long teamId, HandoverRequestDTO.CreateHandoverDTO request, String memberIdString);

    HandoverResponseDTO.FindHandoversResult findHandovers(Long teamId, String memberIdString);

    HandoverResponseDTO.FindHandoverResult findHandover(Long teamId, Long noteId, String memberIdString);

    HandoverResponseDTO.UpdateHandoverResult updateHandover(Long teamId, Long noteId, HandoverRequestDTO.UpdateHandoverDTO request, String memberIdString);

    void deleteHandover(Long teamId, Long noteId, String memberIdString);
}
