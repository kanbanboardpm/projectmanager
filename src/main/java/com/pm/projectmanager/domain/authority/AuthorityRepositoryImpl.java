package com.pm.projectmanager.domain.authority;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthorityRepositoryImpl implements AuthorityRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    QAuthority authority = QAuthority.authority;

    @Override
    public List<Authority> findByProjectIdAndUserIds(Long projectId, List<Long> userIds) {
        return queryFactory
                .selectFrom(authority)
                .join(authority.user).fetchJoin()
                .where(
                    authority.project.id.eq(projectId),
                    authority.user.id.in(userIds)
                )
                .fetch();
    }
}
