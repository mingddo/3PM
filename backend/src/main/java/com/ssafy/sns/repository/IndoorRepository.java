package com.ssafy.sns.repository;

import com.ssafy.sns.domain.newsfeed.Indoor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IndoorRepository extends JpaRepository<Indoor, Long> {

    List<Indoor> findAllByUserId(Long userid);

}
