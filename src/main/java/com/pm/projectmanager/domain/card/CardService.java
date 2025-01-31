package com.pm.projectmanager.domain.card;

import com.pm.projectmanager.common.response.ResponseExceptionEnum;
import com.pm.projectmanager.domain.authority.Authority;
import com.pm.projectmanager.domain.authority.AuthorityRepository;
import com.pm.projectmanager.domain.card.dto.CreateCardRequestDto;
import com.pm.projectmanager.domain.user.User;
import com.pm.projectmanager.exception.AuthorityNullException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final AuthorityRepository authorityRepository;

    public void createCard(CreateCardRequestDto requestDto, User user) {
        hasProjectAndUser(requestDto.getProjectId(), user.getId());
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
