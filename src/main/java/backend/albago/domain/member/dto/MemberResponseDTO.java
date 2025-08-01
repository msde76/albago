package backend.albago.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MemberResponseDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FindMemberResult {
        private Long id;
        private String name;
        private String email;
        private LocalDate birthday;
        private String phoneNumber;
        private Boolean isNotificationOn;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateMemberResult {
        private Long id;
        private String name;
        private LocalDateTime createdAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdateMemberResult {
        private Long id;
        private LocalDateTime updatedAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FindMeResult {
        private Long id;
        private String name;
        private String email;
        private LocalDate birthday;
        private String phoneNumber;
        private Boolean isNotificationOn;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
