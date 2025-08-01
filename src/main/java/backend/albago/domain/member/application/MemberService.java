package backend.albago.domain.member.application;

import backend.albago.domain.member.dto.MemberRequestDTO;
import backend.albago.domain.member.dto.MemberResponseDTO;

public interface MemberService {

    MemberResponseDTO.FindMemberResult findMember(String memberId);

    MemberResponseDTO.CreateMemberResult createMember(String memberId, MemberRequestDTO.CreateMemberDTO request);

    MemberResponseDTO.UpdateMemberResult updateMember(String memberId, MemberRequestDTO.UpdateMemberDTO request);

    void deleteMember(String memberId);

    MemberResponseDTO.FindMeResult findMe(String memberId);
}
