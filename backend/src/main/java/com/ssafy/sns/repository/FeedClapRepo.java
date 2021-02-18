package com.ssafy.sns.repository;

import com.ssafy.sns.domain.clap.FeedClap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedClapRepo extends JpaRepository<FeedClap, Long> {

    List<FeedClap> findAllByFeedId(Long feedId);
}
