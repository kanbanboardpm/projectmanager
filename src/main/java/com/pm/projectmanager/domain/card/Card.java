package com.pm.projectmanager.domain.card;

import com.pm.projectmanager.domain.card.dto.UpdateCardRequestDto;
import com.pm.projectmanager.domain.category.Category;
import com.pm.projectmanager.domain.section.Section;
import com.pm.projectmanager.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

import jakarta.persistence.*;

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

    @ManyToOne
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;

    @Builder
    public Card(String title,
                String content,
                LocalDateTime startDate,
                LocalDateTime endDate,
                LocalDateTime completeDate,
                User user,
                Category category,
                Section section)
    {
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.completeDate = completeDate;
        this.user = user;
        this.category = category;
        this.section = section;
    }

    public void update(UpdateCardRequestDto requestDto, Category category) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.startDate = requestDto.getStartDate();
        this.endDate = requestDto.getEndDate();
        this.category = category;
    }

    public Optional<LocalDateTime> optionalCompleteDate() {
        return Optional.ofNullable(completeDate);
    }

    public void complete(LocalDateTime completeDate) {
        this.completeDate = completeDate;
    }

    public void progress() {
        this.completeDate = null;
    }
}