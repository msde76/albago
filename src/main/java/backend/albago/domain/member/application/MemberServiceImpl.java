package backend.albago.domain.member.application;

import backend.albago.domain.member.converter.MemberConverter;
import backend.albago.domain.member.domain.entity.Member;
import backend.albago.domain.member.domain.repository.MemberRepository;
import backend.albago.domain.member.dto.MemberRequestDTO;
import backend.albago.domain.member.dto.MemberResponseDTO;
import backend.albago.domain.member.exception.MemberException;
import backend.albago.global.error.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public MemberResponseDTO.FindMemberResult findMember(String memberIdString) {

        Long memberId = Long.parseLong(memberIdString);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(ErrorStatus.NO_SUCH_MEMBER));

        return MemberConverter.toFindMemberResult(member);
    }

    @Override
    @Transactional
    public MemberResponseDTO.CreateMemberResult createMember(String memberIdString, MemberRequestDTO.CreateMemberDTO request) {

        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new MemberException(ErrorStatus.MEMBER_ALREADY_EXIST);
        }

        Member newMember = MemberConverter.toMember(request);

        memberRepository.save(newMember);

        return MemberConverter.toCreateMemberResult(newMember);
    }

    @Override
    @Transactional
    public MemberResponseDTO.UpdateMemberResult updateMember(String memberIdString, MemberRequestDTO.UpdateMemberDTO request) {

        Long memberId = Long.parseLong(memberIdString);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(ErrorStatus.NO_SUCH_MEMBER));

        if (request.getName() != null) {
            member.setName(request.getName());
        }
        if (request.getPhoneNumber() != null) {
            member.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getIsNotificationOn() != null) {
            member.setIsNotificationOn(request.getIsNotificationOn());
        }

        return MemberConverter.toUpdateMemberResult(member);
    }

    @Override
    @Transactional
    public void deleteMember(String memberIdString) {

        Long memberId = Long.parseLong(memberIdString);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(ErrorStatus.NO_SUCH_MEMBER));

        memberRepository.delete(member);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberResponseDTO.FindMeResult findMe(String memberIdString) {

        Long memberId = Long.parseLong(memberIdString);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(ErrorStatus.NO_SUCH_MEMBER));

        return MemberConverter.toFindMeResult(member);
    }
}