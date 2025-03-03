package com.pm.projectmanager.domain.authority;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pm.projectmanager.common.response.HttpResponseDto;
import com.pm.projectmanager.common.response.ResponseCodeEnum;
import com.pm.projectmanager.common.response.ResponseUtils;
import com.pm.projectmanager.domain.authority.dto.AuthorityResponseDto;
import com.pm.projectmanager.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authorities")
public class AuthorityController {

	private final AuthorityService authorityService;

	@GetMapping("/{projectId}")
	public ResponseEntity<HttpResponseDto> getRole(
		@PathVariable Long projectId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		UserRole role = authorityService.getUserRole(projectId, userDetails.getUser().getId());
		AuthorityResponseDto responseDto = new AuthorityResponseDto(role);
		return ResponseUtils.of(ResponseCodeEnum.USERROLE_GET_SUCCESS, responseDto);
	}
}
