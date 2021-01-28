package com.ssafy.sns.repository;

import com.ssafy.sns.domain.follow.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    // fromId or toId 기준 count(*)
    long countByFromId(Long fromId);

    long countByToId(Long toId);

    List<Follow> findByToId(Long toId);

    List<Follow> findByFromId(Long fromId);
}
