package com.pm.projectmanager.domain.authority;

import java.util.List;

public interface AuthorityRepositoryCustom {
    List<Authority> findByProjectIdAndUserIds(Long projectId, List<Long> userIds);
}
