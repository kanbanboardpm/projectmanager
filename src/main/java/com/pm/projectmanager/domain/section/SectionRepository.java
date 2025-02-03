package com.pm.projectmanager.domain.section;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, Long> {
	List<Section> findByProjectId(Long projectId);

	boolean existsByProjectIdAndName(Long projectId, String name);
}
