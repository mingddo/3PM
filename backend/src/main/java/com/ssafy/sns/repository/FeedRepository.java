package com.ssafy.sns.repository;

import com.ssafy.sns.domain.newsfeed.Feed;

import java.util.List;

public interface FeedRepository {

    // 내가 쓴글 불러오기
    List<Feed> findMyList(Long id, int num, Object category);

    // 한페이지 불러오기
    List<Feed> findList(int num, Object category);

    // 피드 글 불러오기
    Feed findById(Long feedId);

    // 피드 글쓰기
    Feed save(Feed feed);

    // 피드 글삭제
    void remove(Feed feed);

    Object findCategory(Long feedId);
}
