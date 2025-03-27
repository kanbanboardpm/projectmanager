package com.pm.projectmanager;

import com.pm.projectmanager.domain.authority.Authority;
import com.pm.projectmanager.domain.authority.AuthorityRepository;
import com.pm.projectmanager.domain.authority.UserRole;
import com.pm.projectmanager.domain.project.Project;
import com.pm.projectmanager.domain.project.ProjectRepository;
import com.pm.projectmanager.domain.user.User;
import com.pm.projectmanager.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
@Transactional
public class AuthorityRepositoryTest {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    private static boolean initialized = false;

    @BeforeAll
    static void setUp(@Autowired UserRepository userRepo,
                      @Autowired ProjectRepository projectRepo,
                      @Autowired AuthorityRepository authorityRepo) {
        if (initialized) return;

        // 1. 프로젝트 하나 생성
        Project project = projectRepo.save(Project.builder().name("더미 프로젝트").build());

        // 2. 유저 1만명 생성
        // 나눠서 저장 (예: 1000명씩)
        for (int i = 0; i < 10000000; i += 1000) {
            List<User> users = IntStream.range(i, i + 1000)
                .mapToObj(j -> User.builder()
                    .email("usersa" + j + "@test.com")
                    .password("pass" + j)
                    .nickname("nick" + j)
                    .build())
                .toList();
            userRepo.saveAll(users);

            // 3. 권한 1만건 생성
            List<Authority> authorities = users.stream()
                .map(user -> Authority.builder()
                    .project(project)
                    .user(user)
                    .userRole(UserRole.USER)
                    .build())
                .toList();
            authorityRepo.saveAll(authorities);

            initialized = true;
        }
    }

    @Test
    void test1() {
        Long projectId = projectRepository.findAll().get(0).getId();
        List<Long> userIds = userRepository.findAll().stream()
            .limit(1000)
            .map(User::getId)
            .collect(Collectors.toList());

        long start = System.currentTimeMillis();
        List<Authority> authorities = authorityRepository.findByProjectIdAndUserIdIn(projectId, userIds);
        long end = System.currentTimeMillis();

        System.out.println("권한 조회 결과 개수: " + authorities.size());
        System.out.println("실행 시간: " + (end - start) + "ms");
    }
}
