package com.pm.projectmanager.domain.card;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findAllByCompleteDateIsNull();
    List<Card> findAllBySectionId(Long sectionId);
    Optional<Card> findByIdAndUserId(Long cardId, Long userId);
    Page<Card> findByUserIdAndCompleteDateIsNull(Long userId, Pageable pageable);
    Page<Card> findByUserIdAndCompleteDateIsNotNull(Long userId, Pageable pageable);
}
