package com.ssafy.sns.service;

import com.ssafy.sns.dto.newsfeed.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FeedService {

    // 내가 쓴 글 불러오기
    FeedListResponseDto findMyList(Long userId, Long targetId, int num);

    // 한페이지 불러오기
    FeedListResponseDto readList(Long userId, int num);

    // 피드 글 불러오기
    FeedResponseDto read(Long userId, Long feedId);

    Long write(Long userId, FeedRequestDto feedRequestDto);

    // 피드 파일 첨부
    void uploadFiles(Long feedId, MultipartFile file) throws IOException;

    // 피드 글삭제
    void delete(Long userId, Long feedId) throws IOException;

    // 피드 글수정
    void modify(Long userId, Long feedId, FeedRequestDto feedRequestDto) throws IOException;
}
