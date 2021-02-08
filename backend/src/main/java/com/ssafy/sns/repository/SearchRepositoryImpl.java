package com.ssafy.sns.repository;

import com.ssafy.sns.domain.hashtag.Hashtag;
import com.ssafy.sns.domain.newsfeed.Feed;
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
    public List<Hashtag> findHashtag(String keyword) {
        return em.createQuery("select h from Hashtag h where h.tagName like :tagName", Hashtag.class)
                .setParameter("tagName", "%"+keyword+"%")
                .getResultList();
    }

    @Override
    public List<Feed> findIndoorAll(Hashtag hash) {
        return em.createQuery("select f from Feed f where f.id = (select fh.feed.id from FeedHashtag fh where fh.hashtag.id = :hash)", Feed.class)
                .setParameter("hash", hash.getId())
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
