package com.pm.projectmanager.domain.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pm.projectmanager.common.RedisService;
import com.pm.projectmanager.common.response.ResponseExceptionEnum;
import com.pm.projectmanager.domain.authority.AuthorityRepository;
import com.pm.projectmanager.domain.dto.UpdateRequestDto;
import com.pm.projectmanager.domain.user.dto.SignupRequestDto;
import com.pm.projectmanager.domain.user.dto.UpdatePasswordRequestDto;
import com.pm.projectmanager.domain.user.dto.UserResponseDto;
import com.pm.projectmanager.domain.user.dto.WithdrawRequestDto;
import com.pm.projectmanager.exception.PasswordIncorrectException;
import com.pm.projectmanager.exception.UserAlreadyExistsException;
import com.pm.projectmanager.security.UserDetailsImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final RedisService redisService;
	private final AuthorityRepository authorityRepository;

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
			requestDto.getNickname(), imageUrl);
		userRepository.save(user);
	}

	public void logout(String username) {
		redisService.deleteRefreshToken(username);
	}

	@Transactional
	public void update(UpdateRequestDto requestDto, UserDetailsImpl userDetails) {

		if (userRepository.existsByNickname(requestDto.getNickname())) {
			throw new UserAlreadyExistsException(ResponseExceptionEnum.NICKNAME_ALREADY_EXISTS);
		}

		User user = userDetails.getUser();
		user.update(requestDto.getNickname(), requestDto.getImage_url());
		userRepository.save(user);
	}

	public void updatePassword(UpdatePasswordRequestDto requestDto, UserDetailsImpl userDetails) {

		if (!passwordEncoder.matches(requestDto.getOldPassword(), userDetails.getPassword())) {
			throw new PasswordIncorrectException(ResponseExceptionEnum.PASSWORD_INCORRECT);
		}
		User user = userDetails.getUser();

		String encodedPassword = passwordEncoder.encode(requestDto.getNewPassword());
		user.updatePassword(encodedPassword);
		userRepository.save(user);
	}

	@Transactional
	public void withdraw(WithdrawRequestDto requestDto, UserDetailsImpl userDetails) {

		String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
		if (passwordEncoder.matches(requestDto.getPassword(), encodedPassword)) {
			authorityRepository.deleteByUserId(userDetails.getUser().getId());
			userRepository.delete(userDetails.getUser());
		} else {
			throw new PasswordIncorrectException(ResponseExceptionEnum.PASSWORD_INCORRECT);
		}
	}

	public UserResponseDto get(UserDetailsImpl userDetails) {
		User user = userDetails.getUser();
		return new UserResponseDto(user);
	}


}
