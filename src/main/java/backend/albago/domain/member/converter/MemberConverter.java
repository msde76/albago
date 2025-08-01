package backend.albago.domain.member.converter;

import backend.albago.domain.member.domain.entity.Member;
import backend.albago.domain.member.dto.MemberRequestDTO;
import backend.albago.domain.member.dto.MemberResponseDTO;

public class MemberConverter {

    public static Member toMember(MemberRequestDTO.CreateMemberDTO request) {
        return Member.builder()
                .name(request.getName())
                .email(request.getEmail())
                .birthday(request.getBirthday())
                .phoneNumber(request.getPhoneNumber())
                .isNotificationOn(request.getIsNotificationOn())
                .build();
    }

    public static MemberResponseDTO.FindMemberResult toFindMemberResult(Member member) {
        return MemberResponseDTO.FindMemberResult.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .birthday(member.getBirthday())
                .phoneNumber(member.getPhoneNumber())
                .isNotificationOn(member.getIsNotificationOn())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
                .build();
    }

    public static MemberResponseDTO.CreateMemberResult toCreateMemberResult(Member member) {
        return MemberResponseDTO.CreateMemberResult.builder()
                .id(member.getId())
                .name(member.getName())
                .createdAt(member.getCreatedAt())
                .build();
    }

    public static MemberResponseDTO.UpdateMemberResult toUpdateMemberResult(Member member) {
        return MemberResponseDTO.UpdateMemberResult.builder()
                .id(member.getId())
                .updatedAt(member.getUpdatedAt())
                .build();
    }

    public static MemberResponseDTO.FindMeResult toFindMeResult(Member member) {
        return MemberResponseDTO.FindMeResult.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .birthday(member.getBirthday())
                .phoneNumber(member.getPhoneNumber())
                .isNotificationOn(member.getIsNotificationOn())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
                .build();
    }
}