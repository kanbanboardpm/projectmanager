package com.pm.projectmanager.domain.project;

import java.util.List;
import java.util.stream.Collectors;

import com.pm.projectmanager.common.Color;
import com.pm.projectmanager.domain.category.CategoryService;
import com.pm.projectmanager.domain.category.dto.CreateCategoryRequestDto;
import org.springframework.stereotype.Service;

import com.pm.projectmanager.common.RedisService;
import com.pm.projectmanager.common.response.ResponseExceptionEnum;
import com.pm.projectmanager.domain.authority.Authority;
import com.pm.projectmanager.domain.authority.AuthorityRepository;
import com.pm.projectmanager.domain.authority.AuthorityService;
import com.pm.projectmanager.domain.project.dto.ProjectCreateDto;
import com.pm.projectmanager.domain.project.dto.ProjectInviteDto;
import com.pm.projectmanager.domain.project.dto.ProjectResponseDto;
import com.pm.projectmanager.domain.project.dto.ProjectUpdateDto;
import com.pm.projectmanager.domain.user.dto.UserResponseDto;
import com.pm.projectmanager.exception.AuthorityNullException;
import com.pm.projectmanager.exception.NoInviteException;
import com.pm.projectmanager.exception.ProjectNullException;
import com.pm.projectmanager.security.UserDetailsImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {

	private final ProjectRepository projectRepository;
	private final AuthorityService authorityService;
	private final AuthorityRepository authorityRepository;
	private final RedisService redisService;
    private final CategoryService categoryService;

	@Transactional
	public void create(ProjectCreateDto requestDto, UserDetailsImpl userDetails) {

		Project project = Project.builder()
			.name(requestDto.getName())
			.color(requestDto.getColor())
			.build();

		projectRepository.save(project);

		authorityService.create(project, userDetails.getUser());

        Color color = Color.DEFAULT;
        String categoryName = "default";
        String categoryDescription = "카테고리를 새로 생성해 주세요.";
        categoryService.createCategory(new CreateCategoryRequestDto(
                        color,
                        categoryName,
                        categoryDescription),
                        userDetails.getUser(), project.getId());
	}

	public ProjectResponseDto get(Long projectId, UserDetailsImpl userDetails) {

		Project project = projectRepository.findById(projectId)
			.orElseThrow(() -> new ProjectNullException(ResponseExceptionEnum.PROJECT_NOT_FOUND));

		authorityCheck(projectId, userDetails);

		return new ProjectResponseDto(project);
	}

	public List<ProjectResponseDto> getAll(UserDetailsImpl userDetails) {

		List<Authority> authorities = authorityRepository.findByUserId(userDetails.getUser().getId());

		return authorities.stream()
			.map(authority -> new ProjectResponseDto(authority.getProject()))
			.collect(Collectors.toList());
	}

	@Transactional
	public void update(ProjectUpdateDto requestDto, UserDetailsImpl userDetails, Long projectId) {

		Project project = projectRepository.findById(projectId)
			.orElseThrow(() -> new ProjectNullException(ResponseExceptionEnum.PROJECT_NOT_FOUND));

		authorityCheck(projectId, userDetails);

		project.updateName(requestDto.getName());
		project.updateColor(requestDto.getColor());

		projectRepository.save(project);
	}

	@Transactional
	public void delete(UserDetailsImpl userDetails, Long projectId) {

		Project project = projectRepository.findById(projectId)
			.orElseThrow(() -> new ProjectNullException(ResponseExceptionEnum.PROJECT_NOT_FOUND));

		Authority authority = authorityRepository.findByProjectIdAndUserId(project.getId(), userDetails.getUser().getId());

		authorityCheck(projectId, userDetails);

		authorityRepository.delete(authority);
		projectRepository.delete(project);

	}

	@Transactional
	public void invite(ProjectInviteDto requestDto, UserDetailsImpl userDetails, Long projectId) {

		authorityCheck(projectId, userDetails);

		projectRepository.findById(projectId)
			.orElseThrow(() -> new ProjectNullException(ResponseExceptionEnum.PROJECT_NOT_FOUND));

		List<String> emailList = requestDto.getEmails();

		for (String email : emailList) {
			inviteCreate(projectId, email);
		}
	}


	@Transactional
	public void inviteAccept(Long projectId, UserDetailsImpl userDetails) {
		authorityCheck(projectId, userDetails);

		if (redisService.checkInvite(userDetails.getUser().getEmail(), projectId)) {
			Project project = projectRepository.findById(projectId)
				.orElseThrow(() -> new ProjectNullException(ResponseExceptionEnum.PROJECT_NOT_FOUND));

			authorityService.create(project, userDetails.getUser());
			redisService.deleteInvite(userDetails.getUser().getEmail(), projectId);
		} else {
			throw new NoInviteException(ResponseExceptionEnum.NO_INVITE_EXCEPTION);
		}
	}

	@Transactional
	public void inviteRefuse(Long projectId, UserDetailsImpl userDetails) {

		if (redisService.checkInvite(userDetails.getUser().getEmail(), projectId)) {
			redisService.deleteInvite(userDetails.getUser().getEmail(), projectId);
		} else {
			throw new NoInviteException(ResponseExceptionEnum.NO_INVITE_EXCEPTION);
		}
	}

	public List<UserResponseDto> getUsers(UserDetailsImpl userDetails, Long projectId) {
		authorityCheck(projectId, userDetails);
		List<Authority> authorities = authorityRepository.findByProjectId(projectId);

		return authorities.stream()
			.map(authority -> new UserResponseDto(authority.getUser()))
			.collect(Collectors.toList());
	}

	@Transactional
	public void deleteUser(Long projectId, UserDetailsImpl userDetails, Long userId) {

		authorityCheck(projectId, userDetails);
		authorityRepository.delete(authorityRepository.findByProjectIdAndUserId(projectId, userId));
	}

	private void inviteCreate(Long projectId, String email) {
		redisService.invite(email, projectId);

	}

	private void authorityCheck(Long projectId, UserDetailsImpl userDetails) {
		if (!authorityRepository.existsByProjectIdAndUserId(projectId, userDetails.getUser().getId())) {
			throw new AuthorityNullException(ResponseExceptionEnum.AUTHORITY_NULL_EXCEPTION);
		}
	}


}

