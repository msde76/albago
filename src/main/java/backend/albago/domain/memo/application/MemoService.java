package backend.albago.domain.memo.application;

import backend.albago.domain.memo.dto.MemoRequestDTO;
import backend.albago.domain.memo.dto.MemoResponseDTO;

import java.time.LocalDate;

public interface MemoService {

    MemoResponseDTO.MemoCreateResult createMemo(MemoRequestDTO.MemoCreateDTO request, String memberIdString);

    MemoResponseDTO.FindMemosResult findMemos(LocalDate startDate, LocalDate endDate, String memberIdString);

    MemoResponseDTO.FindMemoResult findMemo(Long memoId, String memberIdString);

    MemoResponseDTO.UpdateMemoResult updateMemo(Long memoId, String memberIdString, MemoRequestDTO.MemoUpdateDTO request);

    void deleteMemo(Long memoId, String memberIdString);
}
