package com.ssafy.sns.service;

import com.ssafy.sns.dto.newsfeed.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FeedService {

    // 내가 쓴 글 불러오기
    FeedListResponseDto findMyList(Long id, int num);

    // 한페이지 불러오기
    FeedListResponseDto readList(int num);

    // 피드 글 불러오기
    FeedResponseDto read(Long id);

    // 피드 글쓰기

    public Long write(Long userId, FeedRequestDto feedRequestDto);

    // 피드 파일 첨부
    public void uploadFiles(Long feedId, List<MultipartFile> files) throws IOException;

    // 피드 글삭제
    public boolean delete(Long userId, Long feedId) throws IOException;

    // 피드 글수정
    public Long modify(Long userId, Long feedId, FeedRequestDto feedRequestDto);

    // 박수 추가
    public Long addClap(Long uid, Long fid);

}
