package com.ssafy.sns.service;

import com.ssafy.sns.domain.hashtag.Hashtag;
import com.ssafy.sns.domain.newsfeed.Indoor;

import java.util.List;

public interface SearchService {

    public Hashtag searchHashtag(String keyword);

    public List<Indoor> searchIndoorAll(String hash);
}
