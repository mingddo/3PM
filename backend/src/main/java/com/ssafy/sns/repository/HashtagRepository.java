package com.ssafy.sns.repository;

import com.ssafy.sns.domain.hashtag.FeedHashtag;
import com.ssafy.sns.domain.hashtag.Hashtag;
import com.ssafy.sns.domain.newsfeed.Feed;

import java.util.List;
import java.util.Optional;

public interface HashtagRepository {

    Optional<Hashtag> findByTag(String tag);

    Hashtag save(Hashtag hashtag);

    List<FeedHashtag> findFeedHashTag(Feed feed);

    void remove(FeedHashtag feedHashtag);

    void change(List<String> tags, Feed feed);

    List search(String text);
}
