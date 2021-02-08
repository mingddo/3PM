package com.ssafy.sns.service;

import com.ssafy.sns.domain.file.File;
import com.ssafy.sns.domain.hashtag.Hashtag;
import com.ssafy.sns.domain.newsfeed.Feed;
import com.ssafy.sns.domain.newsfeed.Indoor;
import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.newsfeed.*;
import com.ssafy.sns.repository.FileRepository;
import com.ssafy.sns.repository.HashtagRepositoryImpl;
import com.ssafy.sns.repository.IndoorRepositoryImpl;
import com.ssafy.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class IndoorServiceImpl implements FeedService {

    private final IndoorRepositoryImpl indoorRepository;
    private final HashtagRepositoryImpl hashtagRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;
    private final FileServiceImpl fileService;

    @Override
    public FeedListResponseDto readMyList(Long id, int num) {
        List<Feed> indoorList = indoorRepository.findMyList(id, num);
        List<IndoorResponseDto> indoorResponseDtoList = new ArrayList<>();
        for (Feed feed : indoorList) {
            indoorResponseDtoList.add(new IndoorResponseDto((Indoor) feed));
        }
        return new FeedListResponseDto<>(indoorResponseDtoList, num + indoorList.size());
    }

    @Override
    public FeedListResponseDto readList(int num) {
        List<Feed> indoorList = indoorRepository.findList(num);
        List<IndoorResponseDto> indoorResponseDtoList = new ArrayList<>();
        for (Feed feed : indoorList) {
            indoorResponseDtoList.add(new IndoorResponseDto((Indoor) feed));
        }
        return new FeedListResponseDto<>(indoorResponseDtoList, num + indoorList.size());
    }

    @Override
    public FeedResponseDto read(Long id) {
        Indoor indoor = (Indoor) indoorRepository.findOne(id);
        return new IndoorResponseDto(indoor);
    }

    @Override
    public Long write(Long userId, FeedRequestDto feedRequestDto, MultipartFile file) throws IOException {
        // 유저 정보
        User user = userRepository.findById(userId).orElse(null);
        // 파일 업로드
        String fileName = "";
        if (file != null) {
            fileName = s3Service.uploadFile(file);
        }
        // 글 등록
        Indoor indoor = (Indoor) indoorRepository.save(feedRequestDto, user);
        // 태그 등록
        hashtagRepository.make(feedRequestDto.getTags(), indoor);
        // 파일 등록
        fileService.addFile(fileName, indoor);
        return indoor.getId();
    }

    @Override
    public Long modify(Long userId, Long feedId, FeedRequestDto feedRequestDto, MultipartFile file) {
        // 글 가져오기
        Indoor indoor = (Indoor) indoorRepository.findOne(feedId);
        if (indoor.getUser().getId().equals(userId)) {
            // 글 수정
            indoorRepository.update(feedId, feedRequestDto);
            // 태그 찾고 삭제
            hashtagRepository.change(feedRequestDto.getTags(), indoor);
            // 파일 찾기
            List<String> filePaths = feedRequestDto.getFilePaths();
            return indoor.getId();
        }

        return -1L;
    }

    @Override
    public boolean delete(Long userId, Long feedId) {
        // 글 가져오기
        Indoor indoor = (Indoor) indoorRepository.findOne(feedId);
        if (indoor.getUser().getId().equals(userId)) {
            indoorRepository.remove(feedId);
            return true;
        }
        return false;
    }

    @Override
    public Long addClap(Long uid, Long fid) {
//        // 글 찾기
//        Indoor indoor = (Indoor) indoorRepository.findOne(fid);
        return null;

    }
}
