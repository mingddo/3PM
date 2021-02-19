package com.ssafy.sns.repository;

import com.ssafy.sns.domain.clap.FeedClap;
import com.ssafy.sns.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> { // Entity 타입, Entity 식별자 타입 (id)

    Optional<User> findByKakaoId(Long kakaoId);

    Optional<User> findByNickname(String nickname);

    // 피드안에 좋아요를 누른 유저 리스트
    List<User> findByIdIn(List<Long> Ids);
}