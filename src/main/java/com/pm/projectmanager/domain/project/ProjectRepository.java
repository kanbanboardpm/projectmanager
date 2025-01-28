package com.pm.projectmanager.domain.project;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pm.projectmanager.domain.user.User;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
