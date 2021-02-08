package com.ssafy.sns.service;

import com.ssafy.sns.domain.hashtag.Hashtag;
import com.ssafy.sns.domain.newsfeed.Feed;
import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.newsfeed.FeedResponseDto;
import com.ssafy.sns.dto.user.SimpleUserDto;
import com.ssafy.sns.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService{

    private final SearchRepository searchRepository;

    @Override
    public List<Hashtag> searchHashtags(String keyword) {
        return searchRepository.searchHashtags(keyword);

    }

    @Override
    public List<FeedResponseDto> searchFeeds(Hashtag hash) {
        List<Feed> feedList = searchRepository.searchFeeds(hash);
        List<FeedResponseDto> indoorResponseDtoList = new ArrayList<>();
        for (Feed feed : feedList) {
            indoorResponseDtoList.add(new FeedResponseDto(feed));
        }
        return indoorResponseDtoList;
    }

    @Override
    public List<SimpleUserDto> searchUsers(String keyword) {
        List<User> userList = searchRepository.searchUsers(keyword);
        List<SimpleUserDto> userDtoList = new ArrayList<>();
        for (User u : userList) {
            userDtoList.add(new SimpleUserDto(u));
        }
        return userDtoList;
    }

}
