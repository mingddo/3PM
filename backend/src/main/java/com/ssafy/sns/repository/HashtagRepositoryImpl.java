package com.ssafy.sns.repository;

import com.ssafy.sns.domain.hashtag.FeedHashtag;
import com.ssafy.sns.domain.hashtag.Hashtag;
import com.ssafy.sns.domain.newsfeed.Feed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class HashtagRepositoryImpl implements HashtagRepository {

    private final EntityManager em;

    @Override
    public void make(List<String> tags, Feed feed) {
        List<Hashtag> result = new ArrayList<>();

        // Hashtag 엔티티 추출
        for (String tag : tags) {
            List<Hashtag> resultTag =
                    em.createQuery("SELECT h FROM Hashtag h WHERE h.tagName = :tag", Hashtag.class)
                    .setParameter("tag", tag)
                    .getResultList();
            if (resultTag.isEmpty()) {
                Hashtag hashtag = new Hashtag(tag);
                em.persist(hashtag);
                result.add(hashtag);
            } else {
                result.add(resultTag.get(0));
            }
        }

        // FeedHashtag 생성 후 매핑
        for (Hashtag hashtag : result) {
            FeedHashtag feedHashtag = new FeedHashtag();
            feed.addFeedHashtag(feedHashtag);
            hashtag.addFeedHashtag(feedHashtag);
        }
    }

    @Override
    public void change(List<String> tags, Feed feed) {
        List<Hashtag> result = new ArrayList<>();

        // 삭제할 태그
        List<FeedHashtag> feedHashtags =
                em.createQuery("SELECT f FROM FeedHashtag f WHERE f.feed.id = :id", FeedHashtag.class)
                .setParameter("id", feed.getId())
                .getResultList();

        for (FeedHashtag feedHashtag : feedHashtags) {
            feed.deleteFeedHashtag(feedHashtag);
            em.remove(feedHashtag);
        }

        // 태그 재 등록
        make(tags, feed);
    }
}
