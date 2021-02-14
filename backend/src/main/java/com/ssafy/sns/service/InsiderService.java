package com.ssafy.sns.service;

import com.ssafy.sns.dto.newsfeed.FeedListResponseDto;
import com.ssafy.sns.dto.newsfeed.FeedRequestDto;
import com.ssafy.sns.dto.newsfeed.FeedResponseDto;
import com.ssafy.sns.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class InsiderService {


    private final FeedRepositoryImpl feedRepository;
    private final HashtagRepositoryImpl hashtagRepository;
    private final FeedClapRepositoryImpl feedClapRepository;
    private final CommentRepositoryImpl commentRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;
    private final FileServiceImpl fileService;

    public FeedListResponseDto findAllByGroupId(Long groupId, Long userId, int num) {

    }


    public FeedListResponseDto findMyList(Long userId, Long targetId, int num) {
        return null;
    }

    public FeedListResponseDto readList(Long userId, int num) {
        return null;
    }

    public FeedResponseDto read(Long userId, Long feedId) {
        return null;
    }

    public Long write(Long userId, FeedRequestDto feedRequestDto) {
        return null;
    }

    public void uploadFiles(Long feedId, MultipartFile file) throws IOException {

    }

    public boolean delete(Long userId, Long feedId) {
        return false;

    }

    public void modify(Long userId, Long feedId, FeedRequestDto feedRequestDto) {
    }

}
