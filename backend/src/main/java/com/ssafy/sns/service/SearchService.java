package com.ssafy.sns.service;

import com.ssafy.sns.domain.hashtag.Hashtag;
import com.ssafy.sns.dto.group.GroupResDto;
import com.ssafy.sns.dto.newsfeed.InsiderResDto;
import com.ssafy.sns.dto.search.SearchHashtagDto;
import com.ssafy.sns.dto.search.SearchUserDto;
import com.ssafy.sns.dto.user.SimpleUserDto;

import java.util.List;

public interface SearchService {

    List<Hashtag> searchHashtags(String keyword);

    List<InsiderResDto> searchFeeds(Long userId, Hashtag hash);

    List<SimpleUserDto> searchUsers(String keyword);

    List<SearchHashtagDto> hashtagAutocomplete(String text);

    List<SearchUserDto> userAutocomplete(Long userId, String text);

    List<GroupResDto> searchGroup(String keyword);
}
