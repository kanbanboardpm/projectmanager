package com.pm.projectmanager.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoUserInfoDto {
    private Long id;
    private String nickname;
    private String email;
    private String picture;

    public KakaoUserInfoDto(Long id, String nickname, String email, String picture) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.picture = picture;
    }
}