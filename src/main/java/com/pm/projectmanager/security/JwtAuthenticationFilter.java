package com.pm.projectmanager.security;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pm.projectmanager.common.RedisService;
import com.pm.projectmanager.domain.user.dto.LoginRequestDto;
import com.pm.projectmanager.domain.user.dto.LoginResponseDto;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final JwtProvider jwtProvider;
    private final RedisService redisService;

    public JwtAuthenticationFilter(JwtProvider jwtProvider, RedisService redisService) {
        this.jwtProvider = jwtProvider;
		this.redisService = redisService;
		setFilterProcessesUrl("/api/users/login");
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            return handleStandardLogin(req);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Authentication handleStandardLogin(HttpServletRequest req) throws IOException {
        LoginRequestDto requestDto = new ObjectMapper().readValue(req.getInputStream(), LoginRequestDto.class);
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getEmail(),
                        requestDto.getPassword(),
                        null
                )
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res,
        FilterChain chain, Authentication authResult) throws IOException {
        log.info("인증 성공 및 JWT 생성");
        String email = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();

        String accessToken = jwtProvider.createAccessToken(email);
        String refreshToken = jwtProvider.createRefreshToken(email);

        res.setHeader(AUTHORIZATION_HEADER, accessToken);
        // jwtProvider.addJwtToCookie(accessToken, res);
        redisService.saveRefreshToken(email, refreshToken);

        res.setStatus(SC_OK);
        res.setCharacterEncoding("UTF-8");
        res.setContentType("application/json");

        String jsonResponse = new ObjectMapper().writeValueAsString(new LoginResponseDto(accessToken));
        res.getWriter().write(jsonResponse);
    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse res, AuthenticationException failed) throws IOException {
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        res.setCharacterEncoding("UTF-8");
        res.setContentType("application/json");

        String jsonResponse = new ObjectMapper().writeValueAsString(new LoginResponseDto(failed.getMessage()));
        res.getWriter().print(jsonResponse);
    }
}
