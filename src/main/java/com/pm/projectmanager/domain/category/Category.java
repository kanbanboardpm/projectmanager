package com.pm.projectmanager.domain.category;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String color;
    private String name;
    private String description;

    @Builder
    public Category(String color, String name, String description) {
        this.color = color;
        this.name = name;
        this.description = description;
    }

//    @ManyToOne
//    @JoinColumn(name = "project_id", nullable = false)
//    private Project project;

}
