package com.ssafy.sns.repository;

import com.ssafy.sns.domain.clap.FeedClap;
import com.ssafy.sns.domain.newsfeed.Feed;
import com.ssafy.sns.domain.newsfeed.Indoor;
import com.ssafy.sns.domain.newsfeed.Outdoor;
import com.ssafy.sns.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public List<Feed> findIndoorRecommend() {

        return em.createQuery("SELECT f.feed " +
                "FROM FeedClap f " +
                "WHERE f.createdDate > :time " +
//                "WHERE TYPE(f) = :category ", FeedClap.class)
                "GROUP BY f.feed " +
                "ORDER BY COUNT(f.feed) DESC", Feed.class)
                .setParameter("time", LocalDateTime.now().minusDays(3))
//                .setParameter("category", Indoor.class)
                .getResultList();
    }


//    public List<Feed> findManyClapFeed() {
//        return em.createQuery("SELECT f.feed " +
//                "FROM FeedClap f " +
//                "WHERE f.createdDate > :time " +
////                "AND TYPE (f.feed) = :category " +
//                "GROUP BY f.feed " +
//                "ORDER BY COUNT(f.feed) DESC", Feed.class)
//                .setParameter("time", LocalDateTime.now().minusDays(3))
////                .setParameter("category", Outdoor.class)
//                .getResultList();
//    }
//
//    public Feed findOutdoor(Feed feed, Integer code) {
//        return em.createQuery("SELECT f " +
//                "FROM Outdoor f " +
//                "WHERE TYPE(f) = :category " +
//                "AND f.id = :feedId " +
//                "AND f.code = :code ", Feed.class)
//                .setParameter("feedId", feed.getId())
//                .setParameter("category", Outdoor.class)
//                .setParameter("code", code)
//                .getSingleResult();
//    }
}
