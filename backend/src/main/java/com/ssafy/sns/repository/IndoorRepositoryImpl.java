package com.ssafy.sns.repository;

import com.ssafy.sns.domain.newsfeed.Indoor;
import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.newsfeed.IndoorRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class IndoorRepositoryImpl implements FeedRepository {

    private final EntityManager em;

    @Override
    public Indoor findOne(Long id) {
        return em.find(Indoor.class, id);
    }

    @Override
    public Long save(Indoor indoor) {
        em.persist(indoor);
        return indoor.getId();
    }


    // 임시 메소드 (merge시 제거)
    public User findUser(Long id) {
        return em.find(User.class, id);
    }
}
