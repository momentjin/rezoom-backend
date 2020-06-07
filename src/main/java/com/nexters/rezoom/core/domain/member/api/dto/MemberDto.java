package com.nexters.rezoom.core.domain.member.api.dto;

import com.nexters.rezoom.core.domain.member.domain.Account;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class MemberDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SignUpReq {

        @Email
        private String username;

        @NotEmpty
        private String password;

        @NotEmpty
        private String name;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SignInReq {
        @Email
        private String id;

        @NotEmpty
        private String password;

        public SignInReq(String id, String password) {
            this.id = id;
            this.password = password;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateReq {

        @NotEmpty
        private String name;

        private String motto;

        private String profileImageUrl;
    }

    @Getter
    public static class ViewRes {
        private Long PK;
        private String username;
        private String name;
        private String motto;

        public ViewRes(Account account) {
            this.PK = account.getPK();
            this.username = account.getUsername();
            this.name = account.getName();
            this.motto = account.getMotto();
        }
    }
}
