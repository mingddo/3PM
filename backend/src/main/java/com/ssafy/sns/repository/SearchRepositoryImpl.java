package com.ssafy.sns.repository;

import com.ssafy.sns.domain.group.Group;
import com.ssafy.sns.domain.hashtag.Hashtag;
import com.ssafy.sns.domain.newsfeed.Feed;
import com.ssafy.sns.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SearchRepositoryImpl implements SearchRepository{

    private final EntityManager em;

    @Override
    public List<Hashtag> searchHashtags(String keyword) {
        return em.createQuery("select h from Hashtag h where h.tagName like :tagName", Hashtag.class)
                .setParameter("tagName", "%"+keyword+"%")
                .getResultList();
    }

    @Override
    public List<Feed> searchFeeds(Hashtag hash) {
        return em.createQuery("select f from Feed f where f.id in (select fh.feed.id from FeedHashtag fh where fh.hashtag.id = :hash)", Feed.class)
                .setParameter("hash", hash.getId())
                .getResultList();
    }

    @Override
    public List<User> searchUsers(String keyword) {
        return em.createQuery("select u from User u where u.nickname like :nickname", User.class)
                .setParameter("nickname", "%"+keyword+"%")
                .getResultList();
    }

    @Override
    public List<Group> searchGroups(String keyword) {
        return em.createQuery("select g from Group g where g.name like :name", Group.class)
                .setParameter("name", "%"+keyword+"%")
                .getResultList();
    }

}
