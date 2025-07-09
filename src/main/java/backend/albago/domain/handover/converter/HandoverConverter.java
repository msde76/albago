package backend.albago.domain.handover.converter;

import backend.albago.domain.handover.domain.entity.HandoverNote;
import backend.albago.domain.handover.dto.HandoverResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class HandoverConverter {

    public static HandoverResponseDTO.HandoverDetailDTO toHandoverDetailDTO(HandoverNote handover) {
        return HandoverResponseDTO.HandoverDetailDTO.builder()
                .noteId(handover.getId())
                .teamId(handover.getTeam().getId())
                .memberId(handover.getMember().getId())
                .memberName(handover.getMember().getName())
                .title(handover.getTitle())
                .content(handover.getContent())
                .createdAt(handover.getCreatedAt())
                .updatedAt(handover.getUpdatedAt())
                .build();
    }

    public static HandoverResponseDTO.CreateHandoverResult toCreateHandoverResult(HandoverNote handover) {
        return HandoverResponseDTO.CreateHandoverResult.builder()
                .noteId(handover.getId())
                .teamId(handover.getTeam().getId())
                .memberId(handover.getMember().getId())
                .title(handover.getTitle())
                .content(handover.getContent())
                .createdAt(handover.getCreatedAt())
                .build();
    }

    public static HandoverResponseDTO.FindHandoversResult toFindHandoversResult(List<HandoverNote> handovers) {
        List<HandoverResponseDTO.HandoverDetailDTO> handoverDetailList = handovers.stream()
                .map(HandoverConverter::toHandoverDetailDTO)
                .collect(Collectors.toList());
        return HandoverResponseDTO.FindHandoversResult.builder()
                .handovers(handoverDetailList)
                .build();
    }

    public static HandoverResponseDTO.FindHandoverResult toFindHandoverResult(HandoverNote handover) {
        return HandoverResponseDTO.FindHandoverResult.builder()
                .handover(toHandoverDetailDTO(handover))
                .build();
    }

    public static HandoverResponseDTO.UpdateHandoverResult toUpdateHandoverResult(HandoverNote handover) {
        return HandoverResponseDTO.UpdateHandoverResult.builder()
                .noteId(handover.getId())
                .teamId(handover.getTeam().getId())
                .memberId(handover.getMember().getId())
                .title(handover.getTitle())
                .content(handover.getContent())
                .updatedAt(handover.getUpdatedAt())
                .build();
    }
}
