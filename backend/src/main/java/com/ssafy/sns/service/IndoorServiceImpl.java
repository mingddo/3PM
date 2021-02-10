package com.ssafy.sns.service;

import com.ssafy.sns.domain.clap.FeedClap;
import com.ssafy.sns.domain.newsfeed.Feed;
import com.ssafy.sns.domain.newsfeed.Indoor;
import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.newsfeed.*;
import com.ssafy.sns.repository.FeedClapRepositoryImpl;
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
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class IndoorServiceImpl implements FeedService {

    private final IndoorRepositoryImpl indoorRepository;
    private final HashtagRepositoryImpl hashtagRepository;
    private final FeedClapRepositoryImpl feedClapRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;
    private final FileServiceImpl fileService;

    @Override
    public FeedListResponseDto readMyList(Long id, int num) {
        List<Feed> indoorList = indoorRepository.findMyList(id, num);
        List<IndoorResponseDto> indoorResponseDtoList = new ArrayList<>();
        for (Feed feed : indoorList) {
            indoorResponseDtoList.add(new IndoorResponseDto((Indoor) feed, feedClapRepository.findClapAll(feed).size()));
        }
        return new FeedListResponseDto<>(indoorResponseDtoList, num + indoorList.size());
    }

    @Override
    public FeedListResponseDto readList(int num) {
        List<Feed> indoorList = indoorRepository.findList(num);
        List<IndoorResponseDto> indoorResponseDtoList = new ArrayList<>();
        for (Feed feed : indoorList) {
            indoorResponseDtoList.add(new IndoorResponseDto((Indoor) feed, feedClapRepository.findClapAll(feed).size()));
        }
        return new FeedListResponseDto<>(indoorResponseDtoList, num + indoorList.size());
    }

    @Override
    public FeedResponseDto read(Long id) {
        Indoor indoor = (Indoor) indoorRepository.findOne(id);
        return new IndoorResponseDto(indoor, feedClapRepository.findClapAll(indoor).size());
    }

    @Override
    public Long write(Long userId, FeedRequestDto feedRequestDto) {
        // 유저 정보
        User user = userRepository.findById(userId).orElse(null);

        // 글 등록
        Indoor indoor = (Indoor) indoorRepository.save(feedRequestDto, user);

        // 태그 등록
        hashtagRepository.make(feedRequestDto.getTags(), indoor);

        return indoor.getId();
    }

    @Override
    public void uploadFiles(Long feedId, List<MultipartFile> files) throws IOException {
        Feed feed = indoorRepository.findOne(feedId);

        // 파일 업로드
        for (MultipartFile file : files) {
            String fileName = s3Service.uploadFile(file);
            // 파일 등록
            fileService.addFile(fileName, feed);
        }
    }

    @Override
    public Long modify(Long userId, Long feedId, FeedRequestDto feedRequestDto) {
        // 글 가져오기
        Indoor indoor = (Indoor) indoorRepository.findOne(feedId);
        if (indoor.getUser().getId().equals(userId)) {
            // 글 수정
            indoorRepository.update(feedId, feedRequestDto);
            // 태그 찾고 삭제
            hashtagRepository.change(feedRequestDto.getTags(), indoor);

            // 파일 찾기
            List<String> curFileNames = feedRequestDto.getFilePaths();
            List<String> prevFileNames = fileService.findFileNameList(feedId);

            // 원래 파일 리스트와 비교 삭제
            fileService.modifyFiles(prevFileNames, curFileNames);

            return indoor.getId();
        }

        return -1L;
    }

    @Override
    public boolean delete(Long userId, Long feedId) throws IOException {
        // 글 가져오기
        Indoor indoor = (Indoor) indoorRepository.findOne(feedId);
        if (indoor.getUser().getId().equals(userId)) {
            // 피드에 저장된 파일들 전부 삭제
            List<String> fileNames = fileService.findFileNameList(feedId);
            for (String fileName : fileNames) {
                s3Service.deleteFile(fileName);
            }
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
