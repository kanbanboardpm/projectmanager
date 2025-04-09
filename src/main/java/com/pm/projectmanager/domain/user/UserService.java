package com.pm.projectmanager.domain.user;

import java.io.IOException;
import java.time.LocalDateTime;

import com.pm.projectmanager.common.gcs.GoogleCloudStorageService;
import com.pm.projectmanager.exception.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pm.projectmanager.aop.activityLog.ActionType;
import com.pm.projectmanager.aop.activityLog.LogActivity;
import com.pm.projectmanager.common.RedisService;
import com.pm.projectmanager.common.response.ResponseExceptionEnum;
import com.pm.projectmanager.domain.user.dto.SignupRequestDto;
import com.pm.projectmanager.domain.user.dto.PasswordRequestDto;
import com.pm.projectmanager.domain.user.dto.UserResponseDto;
import com.pm.projectmanager.domain.user.dto.WithdrawRequestDto;
import com.pm.projectmanager.security.UserDetailsImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserService {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final RedisService redisService;
    private final GoogleCloudStorageService googleCloudStorageService;

	@Transactional
	public void signup(SignupRequestDto requestDto) {
		if (userRepository.existsByEmail(requestDto.getEmail())) {
			throw new UserAlreadyExistsException(ResponseExceptionEnum.EMAIL_ALREADY_EXISTS);
		}
		if (userRepository.existsByNickname(requestDto.getNickname())) {
			throw new UserAlreadyExistsException(ResponseExceptionEnum.NICKNAME_ALREADY_EXISTS);
		}

		String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

		// default 이미지 주소
		String imageUrl = "https://img1.daumcdn.net/thumb/R1280x0/?fname=http://t1.daumcdn.net/brunch/service/user/7r5X/image/9djEiPBPMLu_IvCYyvRPwmZkM1g.jpg";

		User user = new User(requestDto.getEmail(), encodedPassword,
			requestDto.getNickname(), imageUrl, null);
		userRepository.save(user);
	}

	public void logout(String username) {
		redisService.deleteRefreshToken(username);
	}

	@Transactional
	@LogActivity(value = ActionType.USER, detail = "유저 수정: #{#requestDto.nickname}")
	public void update(String nickname, UserDetailsImpl userDetails, MultipartFile image) throws IOException {

		if (!nickname.equals(userDetails.getUser().getNickname()) && userRepository.existsByNickname(nickname)) {
			throw new UserAlreadyExistsException(ResponseExceptionEnum.NICKNAME_ALREADY_EXISTS);
		}

        String imageUrl = googleCloudStorageService.uploadImage(image);
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(
                () -> new UserNotFoundException(ResponseExceptionEnum.USER_NOT_FOUND)
        );
        boolean isImage = googleCloudStorageService.deleteImage(user.getPhotoUrl());
        if(!isImage) {
            throw new ImageUrlNotFoundException(ResponseExceptionEnum.IMAGE_URL_NOT_FOUND);
        }
		user.update(nickname, imageUrl);
		userRepository.save(user);
	}

	public void checkPassword(PasswordRequestDto requestDto, UserDetailsImpl userDetails) {
		if (!passwordEncoder.matches(requestDto.getPassword(), userDetails.getPassword())) {
			throw new PasswordIncorrectException(ResponseExceptionEnum.PASSWORD_INCORRECT);
		}
	}

	@Transactional
	@LogActivity(value = ActionType.USER, detail = "비밀번호 수정: #{#user.username}")
	public void updatePassword(PasswordRequestDto requestDto, UserDetailsImpl userDetails) {

		User user = userDetails.getUser();

		if (passwordEncoder.matches(requestDto.getPassword(), userDetails.getPassword())) {
			throw new PasswordIncorrectException(ResponseExceptionEnum.PASSWORD_EQUAL);
		}
		String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
		user.updatePassword(encodedPassword);
		userRepository.save(user);
	}

	@Transactional
	@LogActivity(value = ActionType.USER, detail = "유저 탈퇴: #{#user.username}")
	public void withdraw(WithdrawRequestDto requestDto, UserDetailsImpl userDetails) {

		if (passwordEncoder.matches(requestDto.getPassword(), userDetails.getPassword())) {
			userDetails.getUser().withdraw(LocalDateTime.now());
			userRepository.save(userDetails.getUser());
		} else {
			throw new PasswordIncorrectException(ResponseExceptionEnum.PASSWORD_INCORRECT);
		}
	}

	public UserResponseDto get(UserDetailsImpl userDetails) {
		User user = userDetails.getUser();
		return new UserResponseDto(user);
	}


}
