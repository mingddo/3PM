package com.ssafy.sns.service;

import com.ssafy.sns.domain.group.Group;
import com.ssafy.sns.domain.hashtag.Hashtag;
import com.ssafy.sns.domain.newsfeed.*;
import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.group.GroupResDto;
import com.ssafy.sns.dto.newsfeed.InsiderResDto;
import com.ssafy.sns.dto.search.SearchHashtagDto;
import com.ssafy.sns.dto.search.SearchUserDto;
import com.ssafy.sns.repository.*;
import com.ssafy.sns.dto.user.SimpleUserDto;
import com.ssafy.sns.util.UnicodeHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService{

    private final SearchRepository searchRepository;
    private final FeedClapRepositoryImpl feedClapRepository;
    private final UserRepository userRepository;
    private final CommentRepositoryImpl commentRepository;
    private final HashtagRepositoryImpl hashtagRepository;
    private final UserRepositoryImpl userRepositoryImpl;
    private final UnicodeHandler unicodeHandler;
    private final FollowServiceImpl followService;

    @Override
    public List<Hashtag> searchHashtags(String keyword) {
        return searchRepository.searchHashtags(keyword);

    }

    @Override
    public List<InsiderResDto> searchFeeds(Long userId, Hashtag hash) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        List<Feed> feedList = searchRepository.searchFeeds(hash);
        List<InsiderResDto> feedResponseDtoList = new ArrayList<>();
        for (Feed feed : feedList) {
            if(feed instanceof Indoor) {
                feedResponseDtoList.add(new InsiderResDto(feed,
                        (int) commentRepository.findListById(feed).count(),
                        feedClapRepository.findClapAll(feed).size(),
                        feedClapRepository.findClap(user, feed).isPresent(),
                        1,
                        followService.isFollow(userId, feed), null, null));
            } else if(feed instanceof Insider) {
                feedResponseDtoList.add(new InsiderResDto(feed,
                        (int) commentRepository.findListById(feed).count(),
                        feedClapRepository.findClapAll(feed).size(),
                        feedClapRepository.findClap(user, feed).isPresent(),
                        2,
                        followService.isFollow(userId, feed),
                        ((Insider) feed).getGroup().getId(),
                        ((Insider) feed).getGroup().getName()
                        ));
            } else if (feed instanceof Outdoor) {
                feedResponseDtoList.add(new InsiderResDto(feed,
                        (int) commentRepository.findListById(feed).count(),
                        feedClapRepository.findClapAll(feed).size(),
                        feedClapRepository.findClap(user, feed).isPresent(),
                        3,
                        followService.isFollow(userId, feed), null, null));
            }
            else if(feed instanceof Worker) {
                feedResponseDtoList.add(new InsiderResDto(feed,
                        (int) commentRepository.findListById(feed).count(),
                        feedClapRepository.findClapAll(feed).size(),
                        feedClapRepository.findClap(user, feed).isPresent(),
                        4,
                        followService.isFollow(userId, feed), null, null));
            }
        }
        return feedResponseDtoList;
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
    public List<SearchHashtagDto> hashtagAutocomplete(String text) {
        List search = hashtagRepository.search(unicodeHandler.splitHangeulToConsonant(text));
        List<SearchHashtagDto> list = new ArrayList<>();
        for (Object o : search) {
            Object[] result = (Object[]) o;
            list.add(new SearchHashtagDto(((Hashtag)result[0]).getTagName(), ((Long)result[1]).intValue()));
        }
        return list;
    }

    @Override
    public List<SearchUserDto> userAutocomplete(Long userId, String text) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        return userRepositoryImpl.search(user, unicodeHandler.splitHangeulToConsonant(text))
                .stream()
                .map(u -> new SearchUserDto(u.getNickname()))
                .collect(Collectors.toList());
    }

    @Override
    public List<GroupResDto> searchGroup(String keyword) {
        List<Group> groupList = searchRepository.searchGroups(keyword);
        List<GroupResDto> groupResDtoList = new ArrayList<>();
        for (Group g : groupList) {
            groupResDtoList.add(new GroupResDto(g));
        }
        return groupResDtoList;
    }
}
