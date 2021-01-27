package com.ssafy.sns.repository;

import com.ssafy.sns.domain.newsfeed.Indoor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class FeedRepositoryImpl implements FeedRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Indoor selectFeed(Long id) {
        return null;
    }

    @Override
    public void insertFeed(Indoor indoor) {
        em.persist(indoor);
    }

    @Override
    public void deleteFeed(Long id) {

    }

    @Override
    public void updateFeed(Indoor indoor) {

    }
}
