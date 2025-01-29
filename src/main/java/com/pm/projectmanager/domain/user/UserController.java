package com.pm.projectmanager.domain.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pm.projectmanager.common.response.HttpResponseDto;
import com.pm.projectmanager.domain.user.dto.SignupRequestDto;
import com.pm.projectmanager.security.UserDetailsImpl;

import static com.pm.projectmanager.common.response.ResponseCodeEnum.USER_LOGOUT_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseCodeEnum.USER_SIGNUP_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseUtils.of;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	@PostMapping
	public ResponseEntity<HttpResponseDto> signup(
		@Valid @RequestBody SignupRequestDto requestDto
	) {
		userService.signup(requestDto);
		return of(USER_SIGNUP_SUCCESS);
	}

	@GetMapping("/logout")
	public ResponseEntity<HttpResponseDto> logout(
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		userService.logout(userDetails.getUser().getUsername());
		return of(USER_LOGOUT_SUCCESS);
	}
}