package com.pm.projectmanager.domain.project;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pm.projectmanager.common.Color;
import com.pm.projectmanager.domain.authority.UserRole;
import com.pm.projectmanager.domain.card.Card;
import com.pm.projectmanager.domain.card.CardRepository;
import com.pm.projectmanager.domain.category.CategoryRepository;
import com.pm.projectmanager.domain.category.CategoryService;
import com.pm.projectmanager.domain.category.dto.CreateCategoryRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.pm.projectmanager.common.RedisService;
import com.pm.projectmanager.common.response.ResponseExceptionEnum;
import com.pm.projectmanager.domain.authority.Authority;
import com.pm.projectmanager.domain.authority.AuthorityRepository;
import com.pm.projectmanager.domain.authority.AuthorityService;
import com.pm.projectmanager.domain.comment.CommentRepository;
import com.pm.projectmanager.domain.notification.NotificationService;
import com.pm.projectmanager.domain.project.dto.ChangeRoleRequestDto;
import com.pm.projectmanager.domain.project.dto.InviteDto;
import com.pm.projectmanager.domain.project.dto.ProjectCreateDto;
import com.pm.projectmanager.domain.project.dto.ProjectCreateResponseDto;
import com.pm.projectmanager.domain.project.dto.ProjectInviteDto;
import com.pm.projectmanager.domain.project.dto.ProjectInviteResponseDto;
import com.pm.projectmanager.domain.project.dto.ProjectResponseDto;
import com.pm.projectmanager.domain.project.dto.ProjectUpdateDto;
import com.pm.projectmanager.domain.project.dto.ProjectUserResponseDto;
import com.pm.projectmanager.domain.section.Section;
import com.pm.projectmanager.domain.section.SectionRepository;
import com.pm.projectmanager.domain.user.User;
import com.pm.projectmanager.domain.user.UserRepository;
import com.pm.projectmanager.domain.user.dto.UserResponseDto;
import com.pm.projectmanager.exception.AuthorityAlreadyExistsException;
import com.pm.projectmanager.exception.AuthorityNullException;
import com.pm.projectmanager.exception.InviteAlreadyExistsException;
import com.pm.projectmanager.exception.NoInviteException;
import com.pm.projectmanager.exception.ProjectNullException;
import com.pm.projectmanager.exception.UserNotFoundException;
import com.pm.projectmanager.exception.UserRoleException;
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
	private final CategoryRepository categoryRepository;
	private final SectionRepository sectionRepository;
	private final UserRepository userRepository;
	private final CardRepository cardRepository;
	private final CommentRepository commentRepository;
	private final NotificationService notificationService;

	@Transactional
	public ProjectCreateResponseDto create(ProjectCreateDto requestDto, UserDetailsImpl userDetails) {

		Project project = Project.builder()
			.name(requestDto.getName())
			.color(requestDto.getColor())
			.build();

		projectRepository.save(project);

		authorityService.create(project, userDetails.getUser(), UserRole.ADMIN);

        Color color = Color.DEFAULT;
        String categoryName = "default";
        String categoryDescription = "카테고리를 새로 생성해 주세요.";
        categoryService.createCategory(new CreateCategoryRequestDto(
                        color,
                        categoryName,
                        categoryDescription),
                        userDetails.getUser(), project.getId());

		return new ProjectCreateResponseDto(project);
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
		authorityService.adminCheck(projectId, userDetails.getUser().getId());

		Project project = projectRepository.findById(projectId)
			.orElseThrow(() -> new ProjectNullException(ResponseExceptionEnum.PROJECT_NOT_FOUND));

		if (!authorityService.adminCheck(projectId, userDetails.getUser().getId())) {
			throw new UserRoleException(ResponseExceptionEnum.ADMIN_ROLE_REQUIRED);
		}

		if (requestDto.getName() != null) {
			project.updateName(requestDto.getName());
		}
		if (requestDto.getColor() != null) {
			project.updateColor(requestDto.getColor());
		}

		projectRepository.save(project);
	}

	@Transactional
	public void delete(UserDetailsImpl userDetails, Long projectId) {
		authorityService.adminCheck(projectId, userDetails.getUser().getId());

		Project project = projectRepository.findById(projectId)
			.orElseThrow(() -> new ProjectNullException(ResponseExceptionEnum.PROJECT_NOT_FOUND));

		Authority authority = authorityRepository.findByProjectIdAndUserId(project.getId(), userDetails.getUser().getId());

		if (!authorityService.adminCheck(projectId, userDetails.getUser().getId())) {
			throw new UserRoleException(ResponseExceptionEnum.ADMIN_ROLE_REQUIRED);
		}

		List<Section> sections = sectionRepository.findAllByProjectId(projectId);
		for (Section section : sections) {
			List<Card> cards = cardRepository.findAllBySectionId(section.getId());
			for (Card card : cards) {
				commentRepository.deleteAllByCardId(card.getId());
				cardRepository.delete(card);
			}
			sectionRepository.delete(section);
		}
		categoryRepository.deleteByProjectId(projectId);
		authorityRepository.deleteAllByProjectId(projectId);
		projectRepository.delete(project);
	}

	@Transactional
	public void invite(Long projectId, List<String> emails, Long userId) {

		if (!projectRepository.existsById(projectId)) {
			throw new ProjectNullException(ResponseExceptionEnum.PROJECT_NOT_FOUND);
		}
		if (!authorityRepository.existsByProjectIdAndUserId(projectId, userId)) {
			throw new UserRoleException(ResponseExceptionEnum.AUTHORITY_NULL_EXCEPTION);
		}

		ObjectMapper objectMapper = new ObjectMapper();

		List<User> users = userRepository.findByEmailInOrNicknameIn(emails, emails);
		Map<String, User> userMap = users.stream()
			.collect(Collectors.toMap(User::getEmail, user -> user));

		List<Long> userIds = users.stream().map(User::getId).collect(Collectors.toList());
		List<Authority> authorities = authorityRepository.findByProjectIdAndUserIdIn(projectId, userIds);
		Set<Long> existingUserIds = authorities.stream()
			.map(authority -> authority.getUser().getId())
			.collect(Collectors.toSet());

		for (String email : emails) {
			User user = userMap.get(email);
			if (user == null) {
				throw new UserNotFoundException(ResponseExceptionEnum.USER_NOT_FOUND);
			}

			if (existingUserIds.contains(user.getId())) {
				throw new AuthorityAlreadyExistsException(ResponseExceptionEnum.AUTHORITY_ALREADY_EXISTS);
			}

			List<ProjectInviteResponseDto> existingInvites = redisService.getInvites(email);
			if (existingInvites != null) {
				for (ProjectInviteResponseDto inviteDto : existingInvites) {
					if (inviteDto.getId().equals(projectId)) {
						throw new InviteAlreadyExistsException(ResponseExceptionEnum.INVITE_ALREADY_EXISTS);
					}
				}
			}

			try {
				InviteDto inviteDto = new InviteDto(projectId, userId);
				String inviteJson = objectMapper.writeValueAsString(inviteDto);

				redisService.saveInvite(email, inviteJson);
				notificationService.increaseNotificationCount(user.getId());
			} catch (JsonProcessingException e) {
				throw new RuntimeException("초대 JSON 변환 중 오류 발생", e);
			}
		}
	}

	@Transactional
	public void inviteAccept(Long projectId, UserDetailsImpl userDetails) {

		if (redisService.checkInvite(userDetails.getUser().getEmail(), projectId)) {
			Project project = projectRepository.findById(projectId)
				.orElseThrow(() -> new ProjectNullException(ResponseExceptionEnum.PROJECT_NOT_FOUND));

			authorityService.create(project, userDetails.getUser(), UserRole.USER);
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

	public List<ProjectUserResponseDto> getUsers(UserDetailsImpl userDetails, Long projectId) {
		authorityCheck(projectId, userDetails);
		List<Authority> authorities = authorityRepository.findByProjectId(projectId);

		return authorities.stream()
			.map(authority -> new ProjectUserResponseDto(authority.getUser(), authority))
			.collect(Collectors.toList());
	}

	@Transactional
	public void deleteUser(Long projectId, UserDetailsImpl userDetails, Long userId) {
		String string = "asd";
		authorityCheck(projectId, userDetails);
		authorityRepository.delete(authorityRepository.findByProjectIdAndUserId(projectId, userId));
	}

	private void authorityCheck(Long projectId, UserDetailsImpl userDetails) {
		if (!authorityRepository.existsByProjectIdAndUserId(projectId, userDetails.getUser().getId())) {
			throw new AuthorityNullException(ResponseExceptionEnum.AUTHORITY_NULL_EXCEPTION);
		}
	}

	public void changeUserRole(UserDetailsImpl userDetails, Long projectId, ChangeRoleRequestDto requestDto) {
		UserRole role = authorityService.getUserRole(projectId, userDetails.getUser().getId());
		if (role != UserRole.ADMIN) {
			throw new UserRoleException(ResponseExceptionEnum.ADMIN_ROLE_REQUIRED);
		}
		User targetUser = userRepository.findByEmail(requestDto.getEmail())
			.orElseThrow(() -> new UserNotFoundException(ResponseExceptionEnum.USER_NOT_FOUND));
		Authority authority = authorityRepository.findByProjectIdAndUserId(projectId, targetUser.getId());

		authority.updateRole(requestDto.getRole());
		redisService.roleChangeNotifications(targetUser.getId(), requestDto.getRole(), projectId);
		authorityRepository.save(authority);
		notificationService.increaseNotificationCount(targetUser.getId());
	}

	public List<Project> getProjects(List<Long> ids) {
		return projectRepository.findAllById(ids);
	}
}

