package com.pm.projectmanager.domain.category;

import jakarta.persistence.*;
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

    private String color;
    private String name;
    private String description;
    private Long projectId;

    @Builder
    public Category(String color, String name, String description, Long projectId) {
        this.color = color;
        this.name = name;
        this.description = description;
        this.projectId = projectId;
    }

//    @ManyToOne
//    @JoinColumn(name = "project_id", nullable = false)
//    private Project project;

}
