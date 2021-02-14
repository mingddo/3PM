package com.ssafy.sns.repository;

import com.ssafy.sns.domain.newsfeed.Insider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InsiderRepository extends JpaRepository<Insider, Long> {

    List<Insider> findAllByGroupId(Long groupId);
}
