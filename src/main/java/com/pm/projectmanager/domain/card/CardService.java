package com.pm.projectmanager.domain.card;

import com.pm.projectmanager.common.response.ResponseExceptionEnum;
import com.pm.projectmanager.domain.authority.Authority;
import com.pm.projectmanager.domain.authority.AuthorityRepository;
import com.pm.projectmanager.domain.card.dto.CreateCardRequestDto;
import com.pm.projectmanager.domain.card.dto.SelectAllCardRequestDto;
import com.pm.projectmanager.domain.card.dto.SelectAllCardResponseDto;
import com.pm.projectmanager.domain.category.Category;
import com.pm.projectmanager.domain.category.CategoryRepository;
import com.pm.projectmanager.domain.category.dto.SelectCategoryResponseDto;
import com.pm.projectmanager.domain.user.User;
import com.pm.projectmanager.exception.AuthorityNullException;
import com.pm.projectmanager.exception.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final AuthorityRepository authorityRepository;
    private final CategoryRepository categoryRepository;

    public void createCard(CreateCardRequestDto requestDto, User user) {
        hasProjectAndUser(requestDto.getProjectId(), user.getId());
        Category category = categoryRepository.findById(requestDto.getCategoryId()).orElseThrow(
                () -> new CategoryNotFoundException(ResponseExceptionEnum.CATEGORY_NOT_FOUND));
        Card card = Card.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .startDate(requestDto.getStartDate())
                .endDate(requestDto.getEndDate())
                .completeDate(null)
                .user(user)
                .category(category).build();
        cardRepository.save(card);
    }

    public List<SelectAllCardResponseDto> selectAllCard(SelectAllCardRequestDto requestDto, User user) {
        hasProjectAndUser(requestDto.getProjectId(), user.getId());
        return cardRepository.findAllByCompleteDateIsNull()
                .stream()
                .map(SelectAllCardResponseDto::new)
                .toList();
    }

    private Authority findByProjectIdAndUserId(Long projectId, Long userId) {
        Authority authority = authorityRepository.findByProjectIdAndUserId(projectId, userId);
        if (authority == null) {
            throw new AuthorityNullException(ResponseExceptionEnum.AUTHORITY_NULL_EXCEPTION);
        }
        return authority;
    }

    private void hasProjectAndUser(Long projectId, Long userId) {
        Authority authority = authorityRepository.findByProjectIdAndUserId(projectId, userId);
        if (authority == null) {
            throw new AuthorityNullException(ResponseExceptionEnum.AUTHORITY_NULL_EXCEPTION);
        }
    }
}
