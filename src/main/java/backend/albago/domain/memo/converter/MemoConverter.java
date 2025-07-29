package backend.albago.domain.memo.converter;

import backend.albago.domain.memo.domain.entity.Memo;
import backend.albago.domain.memo.dto.MemoResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class MemoConverter {

    public static MemoResponseDTO.MemoCreateResult toMemoCreateResult(Memo memo) {
        return MemoResponseDTO.MemoCreateResult.builder()
                .memoId(memo.getId())
                .memberId(memo.getMember().getId())
                .teamId(memo.getTeam() != null ? memo.getTeam().getId() : null)
                .personalScheduleId(memo.getPersonalSchedule() != null ? memo.getPersonalSchedule().getId() : null)
                .date(memo.getDate())
                .createdAt(memo.getCreatedAt())
                .build();
    }

    public static MemoResponseDTO.MemoDetailDTO toMemoDetailDTO(Memo memo) {
        return MemoResponseDTO.MemoDetailDTO.builder()
                .memoId(memo.getId())
                .memberId(memo.getMember().getId())
                .memberName(memo.getMember().getName())
                .teamId(memo.getTeam() != null ? memo.getTeam().getId() : null)
                .teamName(memo.getTeam() != null ? memo.getTeam().getTeamName() : null)
                .personalScheduleId(memo.getPersonalSchedule() != null ? memo.getPersonalSchedule().getId() : null)
                .content(memo.getContent())
                .date(memo.getDate())
                .createdAt(memo.getCreatedAt())
                .updatedAt(memo.getUpdatedAt())
                .build();
    }

    public static MemoResponseDTO.FindMemosResult toFindMemosResult(List<Memo> memos) {
        List<MemoResponseDTO.MemoDetailDTO> memoDetailList = memos.stream()
                .map(MemoConverter::toMemoDetailDTO)
                .collect(Collectors.toList());
        return MemoResponseDTO.FindMemosResult.builder()
                .memos(memoDetailList)
                .build();
    }

    public static MemoResponseDTO.FindMemoResult toFindMemoResult(Memo memo) {
        return MemoResponseDTO.FindMemoResult.builder()
                .memo(toMemoDetailDTO(memo))
                .build();
    }

    public static MemoResponseDTO.UpdateMemoResult toUpdateMemoResult(Memo memo) {
        return MemoResponseDTO.UpdateMemoResult.builder()
                .memoId(memo.getId())
                .memberId(memo.getMember().getId())
                .date(memo.getDate())
                .updatedAt(memo.getUpdatedAt())
                .updatedContent(memo.getContent())
                .build();
    }
}
