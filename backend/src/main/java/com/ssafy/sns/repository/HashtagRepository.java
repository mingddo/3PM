package com.ssafy.sns.repository;

import com.ssafy.sns.domain.hashtag.Hashtag;

import java.util.List;

public interface HashtagRepository {

    public List<Hashtag> make(List<String> tags);
}
