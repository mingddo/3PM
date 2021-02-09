package com.ssafy.sns.repository;

import com.ssafy.sns.domain.clap.FeedClap;
import com.ssafy.sns.domain.newsfeed.Feed;
import com.ssafy.sns.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface FeedClapRepository {

    public FeedClap save(FeedClap feedClap);

    public Optional<FeedClap> findClap(User user, Feed feed);

    public void delete(FeedClap feedClap);

    public List<FeedClap> findClapAll(Feed feed);
}
