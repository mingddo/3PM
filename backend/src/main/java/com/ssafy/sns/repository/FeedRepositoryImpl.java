package com.ssafy.sns.repository;

import com.ssafy.sns.domain.newsfeed.Feed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FeedRepositoryImpl implements FeedRepository {

    private final EntityManager em;

    @Override
    public List<Feed> findMyList(Long id, int num, Object category) {
        int readPageCnt = 10;
        return em.createQuery("SELECT f " +
                "FROM Feed f " +
                "WHERE f.user.id = :id AND TYPE(f) = :category " +
                "ORDER BY f.createdDate DESC", Feed.class)
                .setParameter("id", id)
                .setParameter("category", category)
                .setFirstResult(num)
                .setMaxResults(readPageCnt)
                .getResultList();
    }

    @Override
    public List<Feed> findList(int num, Object category) {
        int readPageCnt = 10;
        return em.createQuery("SELECT f " +
                "FROM Feed f " +
                "WHERE TYPE(f) = :category " +
                "ORDER BY f.createdDate DESC", Feed.class)
                .setParameter("category", category)
                .setFirstResult(num)
                .setMaxResults(readPageCnt)
                .getResultList();
    }

    @Override
    public Feed findById(Long id) {
        return em.find(Feed.class, id);
    }

    @Override
    public Feed save(Feed feed) {
        em.persist(feed);
        return feed;
    }

    @Override
    public void remove(Feed feed) {
        em.remove(feed);
    }

    @Override
    public Object findCategory(Long feedId) {
        return em.createQuery("select type(f) from Feed f where f.id = :feed")
                .setParameter("feed", feedId)
                .getSingleResult();
    }
}
