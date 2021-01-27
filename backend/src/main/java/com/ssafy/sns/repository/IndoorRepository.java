package com.ssafy.sns.repository;

import com.ssafy.sns.domain.newsfeed.Indoor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndoorRepository extends JpaRepository<Indoor, Long> {

}
