package com.ssafy.sns.repository;

import com.ssafy.sns.domain.clap.FeedClap;
import com.ssafy.sns.domain.newsfeed.Feed;
import com.ssafy.sns.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface FeedClapRepository {

    FeedClap save(FeedClap feedClap);

    Optional<FeedClap> findClap(User user, Feed feed);

    void delete(FeedClap feedClap);

    List<FeedClap> findClapAll(Feed feed);

    FeedClap findById(Long feedClapId);

}
