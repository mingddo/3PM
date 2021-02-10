package com.ssafy.sns.service;

import com.ssafy.sns.dto.newsfeed.FeedListResponseDto;
import com.ssafy.sns.dto.newsfeed.FeedRequestDto;
import com.ssafy.sns.dto.newsfeed.FeedResponseDto;
import com.ssafy.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class InsiderService implements FeedService {


    private final UserRepository userRepository;
    private final S3Service s3Service;
    private final FileServiceImpl fileService;

    @Override
    public FeedListResponseDto findMyList(Long id, int num) {

        return null;
    }

    @Override
    public FeedListResponseDto readList(int num) {
        return null;
    }

    @Override
    public FeedResponseDto read(Long id) {
        return null;
    }

    @Override
    public Long write(Long userId, FeedRequestDto feedRequestDto) {
        return null;
    }

    @Override
    public void uploadFiles(Long feedId, List<MultipartFile> files) throws IOException {

    }

    @Override
    public boolean delete(Long userId, Long feedId) {
        return false;

    }

    @Override
    public Long modify(Long userId, Long feedId, FeedRequestDto feedRequestDto) {
        return null;
    }

    @Override
    public Long addClap(Long uid, Long fid) {
        return null;
    }
}
