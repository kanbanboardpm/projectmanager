package com.pm.projectmanager.security;

import static com.pm.projectmanager.common.response.ResponseExceptionEnum.USER_NOT_FOUND;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pm.projectmanager.domain.user.User;
import com.pm.projectmanager.domain.user.UserRepository;
import com.pm.projectmanager.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        return new UserDetailsImpl(user);
    }
}
