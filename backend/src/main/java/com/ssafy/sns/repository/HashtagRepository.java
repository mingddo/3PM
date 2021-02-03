package com.ssafy.sns.repository;

import com.ssafy.sns.domain.hashtag.Hashtag;
import com.ssafy.sns.domain.newsfeed.Feed;

import java.util.List;

public interface HashtagRepository {

    public void make(List<String> tags, Feed feed);

    public void change(List<String> tags, Feed feed);

}
