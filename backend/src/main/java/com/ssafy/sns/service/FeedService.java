package com.ssafy.sns.service;

import com.ssafy.sns.dto.newsfeed.IndoorListResponseDto;
import com.ssafy.sns.dto.newsfeed.IndoorRequestDto;
import com.ssafy.sns.dto.newsfeed.IndoorResponseDto;

public interface FeedService {

    // 내가 쓴 글 불러오기
    public IndoorListResponseDto readMyList(Long id, int num);

    // 한페이지 불러오기
    public IndoorListResponseDto readList(int num);

    // 피드 글 불러오기
    public IndoorResponseDto read(Long id);

    // 피드 글쓰기
    public Long write(IndoorRequestDto indoorRequestDto);

    // 피드 글삭제
    public void delete(Long id);

    // 피드 글수정
    public Long modify(Long indoorId, IndoorRequestDto indoorRequestDto);
}
