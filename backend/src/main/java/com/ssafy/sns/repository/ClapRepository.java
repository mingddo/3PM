package com.ssafy.sns.repository;

import com.ssafy.sns.domain.clap.FeedClap;
import com.ssafy.sns.domain.newsfeed.Feed;
import com.ssafy.sns.domain.user.User;

import java.util.List;

public interface ClapRepository {

    public FeedClap save(FeedClap feedClap);

    public List<FeedClap> findClap(User user, Feed feed);

    public void delete(FeedClap feedClap);

    public List<FeedClap> findClapAll(Feed feed);
}
