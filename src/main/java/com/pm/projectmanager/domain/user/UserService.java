package com.pm.projectmanager.domain.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pm.projectmanager.common.RedisService;
import com.pm.projectmanager.common.response.ResponseExceptionEnum;
import com.pm.projectmanager.domain.user.dto.SignupRequestDto;
import com.pm.projectmanager.exception.UserAlreadyExistsException;
import com.pm.projectmanager.security.JwtProvider;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final RedisService redisService;

	@Transactional
	public void signup(SignupRequestDto requestDto) {
		if (userRepository.existsByUsername(requestDto.getUsername())) {
			throw new UserAlreadyExistsException(ResponseExceptionEnum.USERNAME_ALREADY_EXISTS);
		}
		if (userRepository.existsByNickname(requestDto.getNickname())) {
			throw new UserAlreadyExistsException(ResponseExceptionEnum.USERNAME_ALREADY_EXISTS);
		}

		String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

		// default 이미지 주소
		String imageUrl = "https://img1.daumcdn.net/thumb/R1280x0/?fname=http://t1.daumcdn.net/brunch/service/user/7r5X/image/9djEiPBPMLu_IvCYyvRPwmZkM1g.jpg";

		User user = new User(requestDto.getUsername(), encodedPassword,
			requestDto.getNickname(), imageUrl);
		userRepository.save(user);
	}

	public void logout(String username) {
		redisService.delete(username);
	}
}
