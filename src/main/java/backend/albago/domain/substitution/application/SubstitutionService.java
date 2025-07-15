package backend.albago.domain.substitution.application;

import backend.albago.domain.substitution.dto.SubstitutionRequestDTO;
import backend.albago.domain.substitution.dto.SubstitutionResponseDTO;

public interface SubstitutionService {

    SubstitutionResponseDTO.CreateSubstitutionResult createSubstitution(Long teamId, SubstitutionRequestDTO.CreateSubstitutionDTO request, String memberIdString);

    SubstitutionResponseDTO.FindRequestSubstitutionResult findRequestSubstitution(Long teamId, String memberIdString);

    SubstitutionResponseDTO.FindReceiveSubstitutionResult findReceiveSubstitution(Long teamId, String memberIdString);

    SubstitutionResponseDTO.FindSubstitutionResult findSubstitution(Long teamId, Long requestId, String memberIdString);

    SubstitutionResponseDTO.AcceptSubstitutionResult acceptSubstitution(Long teamId, Long requestId, String memberIdString);

    SubstitutionResponseDTO.RejectSubstitutionResult rejectSubstitution(Long teamId, Long requestId, String memberIdString);

    void deleteSubstitution(Long teamId, Long requestId, String memberIdString);

    SubstitutionResponseDTO.CheckAvailabilityResult checkAvailability(Long teamId, SubstitutionRequestDTO.CheckAvailabilityDTO requestDTO, String memberIdString);
}
