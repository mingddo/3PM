package com.ssafy.sns.repository;

import com.ssafy.sns.domain.newsfeed.Insider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InsiderRepository extends JpaRepository<Insider, Long> {

    List<Insider> findAllByGroupId(Long groupId, Pageable pageable);

    List<Insider> findAllByUserIdAndGroupId(Long userId, Long groupId, Pageable pageable);

    Optional<Insider> findByGroupIdAndId(Long groupId, Long id);

    List<Insider> findAllByUserId(Long userId, Pageable pageable);
}
