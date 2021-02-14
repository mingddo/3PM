package com.ssafy.sns.repository;

import com.ssafy.sns.domain.follow.Follow;
import com.ssafy.sns.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl {

    private final EntityManager em;

    public List<User> search(User user, String text) {
        return em.createQuery("SELECT f.toUser " +
                "FROM Follow f " +
                "WHERE f.fromUser.id = :userId " +
                "AND f.toUser.nicknameSplit LIKE CONCAT(:text, '%') " +
                "ORDER BY f.toUser.nickname ASC", User.class)
                .setParameter("userId", user.getId())
                .setParameter("text", text)
                .setFirstResult(0)
                .setMaxResults(5)
                .getResultList();
    }
}
