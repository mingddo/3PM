package com.ssafy.sns.service;

import com.ssafy.sns.domain.hashtag.Hashtag;
import com.ssafy.sns.domain.newsfeed.Feed;
import com.ssafy.sns.dto.newsfeed.FeedResponseDto;
import com.ssafy.sns.repository.FeedClapRepositoryImpl;
import com.ssafy.sns.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchServiceImpl {

    private final SearchRepository searchRepository;
    private final FeedClapRepositoryImpl feedClapRepository;

    public List<Hashtag> searchHashtag(String keyword) {
        return searchRepository.findHashtag(keyword);

    }
    public List<FeedResponseDto> searchIndoorAll(Hashtag hash) {
        List<Feed> feedList = searchRepository.findIndoorAll(hash);
        List<FeedResponseDto> indoorResponseDtoList = new ArrayList<>();
        for (Feed feed : feedList) {
            indoorResponseDtoList.add(new FeedResponseDto(feed, feedClapRepository.findClapAll(feed).size()));
        }

        return indoorResponseDtoList;

    }

}
