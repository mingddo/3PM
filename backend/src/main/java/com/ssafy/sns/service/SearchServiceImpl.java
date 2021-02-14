package com.ssafy.sns.service;

import com.ssafy.sns.domain.hashtag.Hashtag;
import com.ssafy.sns.domain.newsfeed.Feed;
import com.ssafy.sns.domain.newsfeed.Indoor;
import com.ssafy.sns.domain.newsfeed.Insider;
import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.newsfeed.FeedResponseDto;
import com.ssafy.sns.dto.search.SearchHashtagDto;
import com.ssafy.sns.repository.*;
import com.ssafy.sns.dto.user.SimpleUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService{

    private final SearchRepository searchRepository;
    private final FeedClapRepositoryImpl feedClapRepository;
    private final UserRepository userRepository;
    private final CommentRepositoryImpl commentRepository;
    private final HashtagRepositoryImpl hashtagRepository;

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
                        feedClapRepository.findClap(user, feed).isPresent(),
                        1));
            } else if(feed instanceof Insider) {
                indoorResponseDtoList.add(new FeedResponseDto(feed,
                        (int) commentRepository.findListById(feed).count(),
                        feedClapRepository.findClapAll(feed).size(),
                        feedClapRepository.findClap(user, feed).isPresent(),
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

    @Override
    public List<SearchHashtagDto> hashtagautoComplete(String text) {
        List search = hashtagRepository.search(text);
        List<SearchHashtagDto> list = new ArrayList<>();
        for (Object o : search) {
            Object[] result = (Object[]) o;
            list.add(new SearchHashtagDto(((Hashtag)result[0]).getTagName(), ((Long)result[1]).intValue()));
        }
        return list;
    }
}
