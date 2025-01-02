package com.pm.projectmanager.common;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageableResponse<T> {
    private List<T> content; // 현재 페이지 데이터
    private int totalPages; // 총 페이지 수
    private long totalElements; // 총 데이터 수
    private int currentPage; // 현재 페이지 번호

    public PageableResponse(Page<T> page) {
        this.content = page.getContent();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.currentPage = page.getNumber();
    }
}
