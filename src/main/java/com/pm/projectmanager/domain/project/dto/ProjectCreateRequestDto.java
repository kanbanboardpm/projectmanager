package com.pm.projectmanager.domain.project.dto;

import java.awt.*;

import com.pm.projectmanager.domain.project.Color;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectCreateRequestDto {

	private String name;
	private Color color;
}
