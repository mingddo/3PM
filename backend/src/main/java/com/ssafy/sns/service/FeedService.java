package com.ssafy.sns.service;

import com.ssafy.sns.dto.newsfeed.IndoorRequestDto;
import com.ssafy.sns.dto.newsfeed.IndoorResponseDto;

import java.util.List;

public interface FeedService {

    // 한페이지 불러오기
//    public List<IndoorRequestDto> showList(Long cnt);

    // 피드 글 불러오기
    public IndoorResponseDto read(Long id);

    // 피드 글쓰기
    public Long write(IndoorRequestDto indoorRequestDto);

    // 피드 글삭제
    public void delete(Long id);

    // 피드 글수정
    public Long modify(Long indoorId, IndoorRequestDto indoorRequestDto);
}
