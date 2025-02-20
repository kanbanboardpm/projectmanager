package com.pm.projectmanager.domain.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	void deleteByProjectId(Long projectId);
    List<Category> findByProjectId(Long projectId);
}
