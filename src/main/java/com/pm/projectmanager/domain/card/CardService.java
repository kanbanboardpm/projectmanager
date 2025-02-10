package com.pm.projectmanager.domain.card;

import com.pm.projectmanager.common.response.ResponseExceptionEnum;
import com.pm.projectmanager.domain.authority.Authority;
import com.pm.projectmanager.domain.authority.AuthorityRepository;
import com.pm.projectmanager.domain.card.dto.*;
import com.pm.projectmanager.domain.category.Category;
import com.pm.projectmanager.domain.category.CategoryRepository;
import com.pm.projectmanager.domain.project.ProjectRepository;
import com.pm.projectmanager.domain.section.Section;
import com.pm.projectmanager.domain.section.SectionRepository;
import com.pm.projectmanager.domain.user.User;
import com.pm.projectmanager.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final AuthorityRepository authorityRepository;
    private final CategoryRepository categoryRepository;
    private final SectionRepository sectionRepository;
    private final ProjectRepository projectRepository;

    public void createCard(CreateCardRequestDto requestDto, User user) {
        validateUserInProject(requestDto.getProjectId(), user.getId());
        Category category = categoryRepository.findById(requestDto.getCategoryId()).orElseThrow(
                () -> new CategoryNotFoundException(ResponseExceptionEnum.CATEGORY_NOT_FOUND));
        Section section = sectionRepository.findById(requestDto.getSectionId())
                .orElseThrow(() -> new SectionException(ResponseExceptionEnum.SECTION_NOT_FOUND));
        Card card = Card.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .startDate(requestDto.getStartDate())
                .endDate(requestDto.getEndDate())
                .completeDate(null)
                .user(user)
                .category(category)
                .section(section)
                .build();
        cardRepository.save(card);
    }

    public List<SelectAllCardResponseDto> selectAllCard(SelectAllCardRequestDto requestDto, User user) {
        validateUserInProject(requestDto.getProjectId(), user.getId());
        return cardRepository.findAllByCompleteDateIsNull()
                .stream()
                .map(SelectAllCardResponseDto::new)
                .toList();
    }

    public List<SelectSectionCardResponseDto> selectSectionCard(SelectSectionCardRequestDto requestDto, User user) {
        validateUserInProject(requestDto.getProjectId(), user.getId());
        hasSection(requestDto.getSectionId());
        return cardRepository.findAllBySectionId(requestDto.getSectionId()).stream().map(SelectSectionCardResponseDto::new).toList();
    }

    public GetCardDetailResponseDto getCardDetail(GetCardDetailRequestDto requestDto, User user, Long cardId) {
        validateUserInProject(requestDto.getProjectId(), user.getId());
        hasSection(requestDto.getSectionId());
        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new CardNotFoundException(ResponseExceptionEnum.CARD_NOT_FOUND)
        );
        return new GetCardDetailResponseDto(card);
    }

    @Transactional
    public void updateCard(UpdateCardRequestDto requestDto, User user, Long cardId) {
        Card card = cardRepository.findByIdAndUserId(cardId, user.getId()).orElseThrow(
                () -> new CardNotFoundException(ResponseExceptionEnum.CARD_NOT_FOUND)
        );
        Category category = categoryRepository.findById(requestDto.getCategoryId()).orElseThrow(
                () -> new CategoryNotFoundException(ResponseExceptionEnum.CATEGORY_NOT_FOUND)
        );
        card.update(requestDto, category);
    }

    public void deleteCard(DeleteCardRequestDto requestDto, User user, Long cardId) {
        validateUserInProject(requestDto.getProjectId(), user.getId());
        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new CardNotFoundException(ResponseExceptionEnum.CARD_NOT_FOUND)
        );
        cardRepository.delete(card);
    }

    // 카드 완료
    @Transactional
    public void completeCard(CompleteCardRequestDto requestDto, User user, Long cardId) {
        Card card = cardRepository.findByIdAndUserId(cardId, user.getId()).orElseThrow(
                () -> new CardNotFoundException(ResponseExceptionEnum.CARD_NOT_FOUND)
        );
        card.updateCompleteDate(requestDto.getCompleteDate());
    }

    // 프로젝트에 유저가 속해있는지 여부 검증
    private void validateUserInProject(Long projectId, Long userId) {
        hasProject(projectId);
        Authority authority = authorityRepository.findByProjectIdAndUserId(projectId, userId);
        if (authority == null) {
            throw new AuthorityNullException(ResponseExceptionEnum.AUTHORITY_NULL_EXCEPTION);
        }
    }

    // 프로젝트가 존재하는지 여부 검증
    private void hasProject(Long projectId) {
        if(projectRepository.findById(projectId).isEmpty()) {
            throw new ProjectNullException(ResponseExceptionEnum.PROJECT_NOT_FOUND);
        }
    }

    // 섹션이 존재하는지 여부 검증
    private void hasSection(Long sectionId) {
        if(sectionRepository.findById(sectionId).isEmpty()) {
            throw new SectionException(ResponseExceptionEnum.SECTION_NOT_FOUND);
        }
    }


}
