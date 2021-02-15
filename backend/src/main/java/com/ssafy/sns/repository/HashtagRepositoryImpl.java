package com.ssafy.sns.repository;

import com.ssafy.sns.domain.hashtag.FeedHashtag;
import com.ssafy.sns.domain.hashtag.Hashtag;
import com.ssafy.sns.domain.newsfeed.Feed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class HashtagRepositoryImpl implements HashtagRepository {

    private final EntityManager em;

    @Override
    public Optional<Hashtag> findByTag(String tag) {
        return  em.createQuery("SELECT h FROM Hashtag h WHERE h.tagName = :tag", Hashtag.class)
                .setParameter("tag", tag)
                .getResultStream()
                .findFirst();
    }

    @Override
    public Hashtag save(Hashtag hashtag) {
        em.persist(hashtag);
        return hashtag;
    }

    @Override
    public List<FeedHashtag> findFeedHashTag(Feed feed) {
        return em.createQuery("SELECT f FROM FeedHashtag f WHERE f.feed.id = :id", FeedHashtag.class)
                .setParameter("id", feed.getId())
                .getResultList();
    }

    @Override
    public void remove(FeedHashtag feedHashtag) {
        em.remove(feedHashtag);
    }

    @Override
    public void change(List<String> tags, Feed feed) {

        // 삭제할 태그
        List<FeedHashtag> feedHashtags =
                em.createQuery("SELECT f FROM FeedHashtag f WHERE f.feed.id = :id", FeedHashtag.class)
                .setParameter("id", feed.getId())
                .getResultList();

        for (FeedHashtag feedHashtag : feedHashtags) {
            feed.deleteFeedHashtag(feedHashtag);
            em.remove(feedHashtag);
        }
    }

    @Override
    public List search(String text) {
        return em.createQuery("SELECT f.hashtag, COUNT(f.hashtag) " +
                "FROM FeedHashtag f " +
                "WHERE f.hashtag.splitName LIKE CONCAT(:text, '%') " +
                "GROUP BY f.hashtag " +
                "ORDER BY COUNT(f.hashtag) DESC, f.hashtag.tagName ASC")
                .setParameter("text", text)
                .setFirstResult(0)
                .setMaxResults(5)
                .getResultList();
    }
}
