package com.pm.projectmanager.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndUserId(Long commentId, Long userId);
    List<Comment> findByCardId(Long cardId);

	void deleteAllByCardId(Long id);
}
