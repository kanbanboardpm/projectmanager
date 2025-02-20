package com.pm.projectmanager.domain.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	void deleteByProjectId(Long projectId);
    List<Category> findByProjectId(Long projectId);
    Optional<Category> findByProjectIdAndName(Long projectId, String name);
}
