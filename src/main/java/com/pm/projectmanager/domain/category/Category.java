package com.pm.projectmanager.domain.category;

import com.pm.projectmanager.common.Color;
import com.pm.projectmanager.domain.project.Project;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"project_Id", "name"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Color color;

    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Builder
    public Category(Color color, String name, String description, Project project) {
        this.color = color;
        this.name = name;
        this.description = description;
        this.project = project;
    }

    public void update(Color color, String name, String description) {
        this.color = color;
        this.name = name;
        this.description = description;
    }
}
