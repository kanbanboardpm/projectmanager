package com.pm.projectmanager.domain.card;

import com.pm.projectmanager.domain.category.Category;
import com.pm.projectmanager.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime completeDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

//    @ManyToOne
//    @JoinColumn(name = "section_id", nullable = false)
//    private Section section;

    @Builder
    public Card(String title,
                String content,
                LocalDateTime startDate,
                LocalDateTime endDate,
                LocalDateTime completeDate,
                User user,
                Category category)
    {
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.completeDate = completeDate;
        this.user = user;
        this.category = category;
    }
}