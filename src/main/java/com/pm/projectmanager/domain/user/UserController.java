package com.pm.projectmanager.domain.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pm.projectmanager.common.response.HttpResponseDto;
import com.pm.projectmanager.domain.dto.UpdateRequestDto;
import com.pm.projectmanager.domain.user.dto.SignupRequestDto;
import com.pm.projectmanager.domain.user.dto.PasswordRequestDto;
import com.pm.projectmanager.domain.user.dto.UserResponseDto;
import com.pm.projectmanager.domain.user.dto.WithdrawRequestDto;
import com.pm.projectmanager.security.UserDetailsImpl;

import static com.pm.projectmanager.common.response.ResponseCodeEnum.USER_DELETE_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseCodeEnum.USER_GET_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseCodeEnum.USER_LOGOUT_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseCodeEnum.USER_PASSWORD_CORRECT;
import static com.pm.projectmanager.common.response.ResponseCodeEnum.USER_PASSWORD_UPDATE_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseCodeEnum.USER_SIGNUP_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseCodeEnum.USER_UPDATE_SUCCESS;
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
		userService.logout(userDetails.getUser().getEmail());
		return of(USER_LOGOUT_SUCCESS);
	}

	@PutMapping
	public ResponseEntity<HttpResponseDto> update(
		@Valid @RequestBody UpdateRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		userService.update(requestDto, userDetails);
		return of(USER_UPDATE_SUCCESS);
	}

	@PostMapping("/password")
	public ResponseEntity<HttpResponseDto> checkPassword(
		@Valid @RequestBody PasswordRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		userService.checkPassword(requestDto, userDetails);
		return of(USER_PASSWORD_CORRECT);
	}

	@PutMapping("/password")
	public ResponseEntity<HttpResponseDto> updatePassword(
		@Valid @RequestBody PasswordRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		userService.updatePassword(requestDto, userDetails);
		return of(USER_PASSWORD_UPDATE_SUCCESS);
	}

	@DeleteMapping("/withdraw")
	public ResponseEntity<HttpResponseDto> delete(
		@Valid @RequestBody WithdrawRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		userService.withdraw(requestDto, userDetails);
		return of(USER_DELETE_SUCCESS);
	}

	@GetMapping
	public ResponseEntity<HttpResponseDto> get(
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		UserResponseDto responseDto = userService.get(userDetails);
		return of(USER_GET_SUCCESS, responseDto);
	}
}