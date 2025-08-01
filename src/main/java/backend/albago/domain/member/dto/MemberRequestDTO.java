package backend.albago.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class MemberRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateMemberDTO {
        private String name;
        private String email;
        private LocalDate birthday;
        private String phoneNumber;
        private Boolean isNotificationOn;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdateMemberDTO {
        private String name;
        private String phoneNumber;
        private Boolean isNotificationOn;
    }
}
