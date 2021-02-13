package com.ssafy.sns.service;

import com.ssafy.sns.domain.hashtag.Hashtag;
import com.ssafy.sns.domain.newsfeed.Feed;
import com.ssafy.sns.domain.newsfeed.Indoor;
import com.ssafy.sns.domain.newsfeed.Insider;
import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.newsfeed.FeedResponseDto;
import com.ssafy.sns.repository.CommentRepositoryImpl;
import com.ssafy.sns.repository.FeedClapRepositoryImpl;
import com.ssafy.sns.dto.user.SimpleUserDto;
import com.ssafy.sns.repository.SearchRepository;
import com.ssafy.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService{

    private final SearchRepository searchRepository;
    private final FeedClapRepositoryImpl feedClapRepository;
    private final UserRepository userRepository;
    private final CommentRepositoryImpl commentRepository;

    @Override
    public List<Hashtag> searchHashtags(String keyword) {
        return searchRepository.searchHashtags(keyword);

    }

    @Override
    public List<FeedResponseDto> searchFeeds(Long userId, Hashtag hash) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        List<Feed> feedList = searchRepository.searchFeeds(hash);
        List<FeedResponseDto> indoorResponseDtoList = new ArrayList<>();
        for (Feed feed : feedList) {
            if(feed instanceof Indoor) {
                indoorResponseDtoList.add(new FeedResponseDto(feed,
                        (int) commentRepository.findListById(feed).count(),
                        feedClapRepository.findClapAll(feed).size(),
                        feedClapRepository.checkClap(feed, user).isPresent(),
                        1));
            } else if(feed instanceof Insider) {
                indoorResponseDtoList.add(new FeedResponseDto(feed,
                        (int) commentRepository.findListById(feed).count(),
                        feedClapRepository.findClapAll(feed).size(),
                        feedClapRepository.checkClap(feed, user).isPresent(),
                        2));
            }
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
