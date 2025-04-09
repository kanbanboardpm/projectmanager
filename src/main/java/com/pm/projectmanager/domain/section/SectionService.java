package com.pm.projectmanager.domain.section;

import static com.pm.projectmanager.common.response.ResponseUtils.of;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pm.projectmanager.aop.activityLog.ActionType;
import com.pm.projectmanager.aop.activityLog.LogActivity;
import com.pm.projectmanager.common.response.ResponseExceptionEnum;
import com.pm.projectmanager.domain.authority.AuthorityRepository;
import com.pm.projectmanager.domain.authority.AuthorityService;
import com.pm.projectmanager.domain.card.Card;
import com.pm.projectmanager.domain.card.CardRepository;
import com.pm.projectmanager.domain.comment.CommentRepository;
import com.pm.projectmanager.domain.project.Project;
import com.pm.projectmanager.domain.project.ProjectRepository;
import com.pm.projectmanager.domain.section.dto.SectionCreateDto;
import com.pm.projectmanager.domain.section.dto.SectionResponseDto;
import com.pm.projectmanager.domain.section.dto.SectionUpdateDto;
import com.pm.projectmanager.exception.AuthorityNullException;
import com.pm.projectmanager.exception.ProjectNullException;
import com.pm.projectmanager.exception.SectionException;
import com.pm.projectmanager.exception.UserRoleException;
import com.pm.projectmanager.security.UserDetailsImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SectionService {

	private final SectionRepository sectionRepository;
	private final ProjectRepository projectRepository;
	private final AuthorityRepository authorityRepository;
	private final AuthorityService authorityService;
	private final CommentRepository commentRepository;
	private final CardRepository cardRepository;

	@Transactional
	@LogActivity(value = ActionType.SECTION, detail = "섹션 생성: #{#requestDto.name}, in #{#projectId} 프로젝트")
	public void create(Long projectId, SectionCreateDto requestDto, UserDetailsImpl userDetails) {

		Project project = projectRepository.findById(projectId)
			.orElseThrow(() -> new ProjectNullException(ResponseExceptionEnum.PROJECT_NOT_FOUND));

		authorityCheck(projectId, userDetails);

		if (sectionRepository.existsByProjectIdAndName(projectId, requestDto.getName())) {
			throw new SectionException(ResponseExceptionEnum.SECTION_ALREADY_EXISTS);
		}

		Section section = Section.builder()
			.name(requestDto.getName())
			.project(project)
			.build();

		sectionRepository.save(section);

	}

	public SectionResponseDto get(Long projectId, Long sectionId, UserDetailsImpl userDetails) {

		authorityCheck(projectId, userDetails);

		Section section = sectionRepository.findById(sectionId)
			.orElseThrow(() -> new SectionException(ResponseExceptionEnum.SECTION_NOT_FOUND));

		return new SectionResponseDto(section);
	}

	public List<SectionResponseDto> getAll(Long projectId, UserDetailsImpl userDetails) {

		authorityCheck(projectId, userDetails);

		List<Section> sections = sectionRepository.findByProjectId(projectId);

		return sections.stream()
			.map(SectionResponseDto::new)
			.collect(Collectors.toList());
	}

	@Transactional
	@LogActivity(value = ActionType.SECTION, detail = "섹션 수정: #{#requestDto.name}, in #{#projectId} 프로젝트")
	public void update(Long projectId, Long sectionId, SectionUpdateDto requestDto, UserDetailsImpl userDetails) {
		if (!authorityService.adminCheck(projectId, userDetails.getUser().getId())) {
			throw new UserRoleException(ResponseExceptionEnum.ADMIN_ROLE_REQUIRED);
		}
		Section section = sectionRepository.findById(sectionId)
			.orElseThrow(() -> new SectionException(ResponseExceptionEnum.SECTION_NOT_FOUND));

		section.update(requestDto.getName());
		sectionRepository.save(section);
	}

	@Transactional
	@LogActivity(value = ActionType.SECTION, detail = "섹션 삭제: #{#sectionId}, in #{#projectId} 프로젝트")
	public void delete(Long projectId, Long sectionId, UserDetailsImpl userDetails) {
		if (!authorityService.adminCheck(projectId, userDetails.getUser().getId())) {
			throw new UserRoleException(ResponseExceptionEnum.ADMIN_ROLE_REQUIRED);
		}
		Section section = sectionRepository.findById(sectionId)
			.orElseThrow(() -> new SectionException(ResponseExceptionEnum.SECTION_NOT_FOUND));

		List<Card> cards = cardRepository.findAllBySectionId(sectionId);
		for (Card card : cards) {
			commentRepository.deleteAllByCardId(card.getId());
		}
		cardRepository.deleteAll(cards);
		sectionRepository.delete(section);
	}

	private void authorityCheck(Long projectId, UserDetailsImpl userDetails) {
		if (!authorityRepository.existsByProjectIdAndUserId(projectId, userDetails.getUser().getId())) {
			throw new AuthorityNullException(ResponseExceptionEnum.AUTHORITY_NULL_EXCEPTION);
		}
	}

}
