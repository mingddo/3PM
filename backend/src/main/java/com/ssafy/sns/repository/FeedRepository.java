package com.ssafy.sns.repository;

import com.ssafy.sns.domain.newsfeed.Indoor;
import com.ssafy.sns.dto.newsfeed.IndoorRequestDto;

import java.util.List;

public interface FeedRepository {

    // 한페이지 불러오기
    public List<Indoor> findList(int num);

    // 피드 글 불러오기
    public Indoor findOne(Long id);

    // 피드 글쓰기
    public Long save(Indoor indoor);

    // 피드 글삭제
    public void remove(Long id);

    // 피드 글수정
    public Long update(Long indoorId, IndoorRequestDto indoorDto);
}
