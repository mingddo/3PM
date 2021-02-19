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
public class FeedClapRepositoryImpl implements FeedClapRepository {

    private final EntityManager em;

    @Override
    public FeedClap save(FeedClap feedClap) {
        em.persist(feedClap);
        return feedClap;
    }

    @Override
    public Optional<FeedClap> findClap(User user, Feed feed) {

        return em.createQuery("SELECT f FROM FeedClap f WHERE f.user.id = :userId AND f.feed.id = :feedId", FeedClap.class)
                .setParameter("userId", user.getId())
                .setParameter("feedId", feed.getId())
                .getResultStream()
                .findFirst();
    }

    @Override
    public void delete(FeedClap feedClap) {
        em.remove(feedClap);
    }

    @Override
    public List<FeedClap> findClapAll(Feed feed) {
        return em.createQuery("SELECT f FROM FeedClap f WHERE f.feed.id = :feedId", FeedClap.class)
                .setParameter("feedId", feed.getId())
                .getResultList();
    }

    @Override
    public FeedClap findById(Long feedClapId) {
        return em.find(FeedClap.class, feedClapId);
    }
}
