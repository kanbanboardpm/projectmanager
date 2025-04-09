package com.pm.projectmanager.domain.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pm.projectmanager.common.response.HttpResponseDto;
import com.pm.projectmanager.domain.user.dto.SignupRequestDto;
import com.pm.projectmanager.domain.user.dto.PasswordRequestDto;
import com.pm.projectmanager.domain.user.dto.UserResponseDto;
import com.pm.projectmanager.domain.user.dto.WithdrawRequestDto;
import com.pm.projectmanager.security.UserDetailsImpl;

import static com.pm.projectmanager.common.response.ResponseCodeEnum.LOGIN_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseCodeEnum.USER_DELETE_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseCodeEnum.USER_GET_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseCodeEnum.USER_LOGOUT_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseCodeEnum.USER_PASSWORD_CORRECT;
import static com.pm.projectmanager.common.response.ResponseCodeEnum.USER_PASSWORD_UPDATE_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseCodeEnum.USER_SIGNUP_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseCodeEnum.USER_UPDATE_SUCCESS;
import static com.pm.projectmanager.common.response.ResponseUtils.of;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;
	private final KakaoService kakaoService;

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
        @RequestPart("nickname") String nickname,
        @RequestPart(value = "image", required = false) MultipartFile image,
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) throws IOException {
		userService.update(nickname, userDetails, image);
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

	@GetMapping("/oauth/kakao")
	public ResponseEntity<HttpResponseDto> kakaoLogin(
		@RequestParam String code,
		@RequestParam String uri,
		HttpServletResponse res
		)
		throws JsonProcessingException {
		return of(LOGIN_SUCCESS, kakaoService.kakaoLogin(code, uri, res));
	}
}