package com.ssafy.sns.repository;

import com.ssafy.sns.domain.hashtag.Hashtag;
import com.ssafy.sns.domain.newsfeed.Indoor;
import com.ssafy.sns.domain.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class SearchRepositoryImpl implements SearchRepository{
    @PersistenceContext
    private EntityManager em;

    @Override
    public Hashtag findHashtag(String keyword) {
//        return em.find(Hashtag.class, (long)1234);
        return em.createQuery("select h from Hashtag h where h.tagName like :tagName", Hashtag.class)
                .setParameter("tagName", "%"+keyword+"%")
                .getSingleResult();
    }

    @Override
    public List<Indoor> findIndoorAll(Hashtag hash) {
        return em.createQuery("select i from Indoor i where i in (select ih.indoor from IndoorHashtag ih where ih.hashtag = :id)")
                .setParameter("id", hash)
                .getResultList();
    }

    @Override
    public User findUserOne(String keyword) {
        return em.find(User.class, keyword);
    }

    @Override
    public List<User> findUserAll(String keyword) {
        return em.createQuery("select u from User u where u.nickname like :nickname", User.class)
                .setParameter("nickname", "%"+keyword+"%")
                .getResultList();
    }
}
