package com.pm.projectmanager.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignupRequestDto {

	@NotBlank(message = "Required Email")
	private String email;
	@NotBlank(message = "Required Password")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()]).{8,}$", message = "대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함합니다. \n비밀번호는 최소 8글자 이상이어야 합니다.")
	private String password;
	@NotBlank(message = "Required Nickname")
	private String nickname;
}