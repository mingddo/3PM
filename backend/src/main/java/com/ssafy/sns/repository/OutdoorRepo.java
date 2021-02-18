package com.ssafy.sns.repository;

import com.ssafy.sns.domain.newsfeed.Outdoor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OutdoorRepo extends JpaRepository<Outdoor, Long> {

    List<Outdoor> findAllByCreatedDateIsGreaterThan(LocalDateTime time);
}
