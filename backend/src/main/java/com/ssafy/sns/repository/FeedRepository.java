package com.ssafy.sns.repository;

import com.ssafy.sns.domain.hashtag.Hashtag;
import com.ssafy.sns.domain.newsfeed.Feed;
import com.ssafy.sns.domain.newsfeed.Indoor;
import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.newsfeed.FeedRequestDto;
import com.ssafy.sns.dto.newsfeed.IndoorRequestDto;

import java.util.List;

public interface FeedRepository {

    // 내가 쓴글 불러오기
    public List<Feed> findMyList(Long id, int num);

    // 한페이지 불러오기
    public List<Feed> findList(int num);

    // 피드 글 불러오기
    public Feed findById(Long feedId);

    // 피드 글쓰기
    Feed save(FeedRequestDto feedRequestDto, User user);

    // 피드 글삭제
    public void remove(Long feedId);

    // 피드 글수정
    public void update(Long feedId, FeedRequestDto feedRequestDto);
}
