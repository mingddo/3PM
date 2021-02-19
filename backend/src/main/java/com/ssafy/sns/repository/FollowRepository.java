package com.ssafy.sns.repository;

import com.ssafy.sns.domain.follow.Follow;
import com.ssafy.sns.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    // fromId or toId 기준 count(*)
    long countByFromUserId(Long fromId);

    long countByToUserId(Long toId);

    List<Follow> findAllByFromUser(User user);

    List<Follow> findAllByToUser(User user);

    @Query("SELECT f FROM Follow AS f WHERE f.fromUser.id= ?1 AND f.toUser.id= ?2")
    Optional<Follow> findByToUserAndFromUser(Long fromUserId, Long toUserId);

    @Query("SELECT f FROM Follow AS f WHERE f.id= ?1")
    Follow findFollowById(Long followId);

    @Modifying
    @Query("DELETE FROM Follow WHERE fromUser.id= :fromUser AND toUser.id= :toUser")
    void deleteByUser(@Param("fromUser")Long fromUser, @Param("toUser") Long toUser);
}
