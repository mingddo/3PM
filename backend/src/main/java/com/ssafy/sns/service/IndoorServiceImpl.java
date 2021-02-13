package com.ssafy.sns.service;

import com.ssafy.sns.domain.hashtag.FeedHashtag;
import com.ssafy.sns.domain.hashtag.Hashtag;
import com.ssafy.sns.domain.newsfeed.Feed;
import com.ssafy.sns.domain.newsfeed.Indoor;
import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.newsfeed.*;
import com.ssafy.sns.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@Service
@Transactional
@RequiredArgsConstructor
public class IndoorServiceImpl implements FeedService {

    private final FeedRepositoryImpl feedRepository;
    private final HashtagRepositoryImpl hashtagRepository;
    private final FeedClapRepositoryImpl feedClapRepository;
    private final CommentRepositoryImpl commentRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;
    private final FileServiceImpl fileService;

    @Override
    public FeedListResponseDto findMyList(Long userId, Long targetId, int num) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        List<Feed> indoorList = feedRepository.findMyList(targetId, num);
        List<IndoorResponseDto> indoorResponseDtoList = new ArrayList<>();
        for (Feed feed : indoorList) {
            indoorResponseDtoList.add(new IndoorResponseDto((Indoor) feed,
                    (int) commentRepository.findListById(feed).count(),
                    feedClapRepository.findClapAll(feed).size(),
                    feedClapRepository.findClap(user, feed).isPresent()));
        }
        return new FeedListResponseDto<>(indoorResponseDtoList, num + indoorList.size());
    }

    @Override
    public FeedListResponseDto readList(Long userId, int num) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        List<Feed> indoorList = feedRepository.findList(num);
        List<IndoorResponseDto> indoorResponseDtoList = new ArrayList<>();
        for (Feed feed : indoorList) {
            indoorResponseDtoList.add(new IndoorResponseDto((Indoor) feed,
                    (int) commentRepository.findListById(feed).count(),
                    feedClapRepository.findClapAll(feed).size(),
                    feedClapRepository.findClap(user, feed).isPresent()));
        }
        return new FeedListResponseDto<>(indoorResponseDtoList, num + indoorList.size());
    }

    @Override
    public FeedResponseDto read(Long userId, Long feedId) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        Feed feed = feedRepository.findById(feedId);
        if (!(feed instanceof Indoor)) throw new NoSuchElementException();
        return new IndoorResponseDto((Indoor) feed,
                (int) commentRepository.findListById(feed).count(),
                feedClapRepository.findClapAll(feed).size(),
                feedClapRepository.findClap(user, feed).isPresent());
    }

    @Override
    public Long write(Long userId, FeedRequestDto feedRequestDto) {
        // 유저 정보
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);

        // 글 등록
        Indoor indoor = (Indoor) feedRepository.save(Indoor.builder()
                .content(feedRequestDto.getContent())
                .user(user)
                .build());

        // 태그 등록
        List<Hashtag> hashtags = new ArrayList<>();

        for (String tag : feedRequestDto.getTags()) {
            hashtagRepository.findByTag(tag).ifPresentOrElse(
                    hashtags::add,
                    () -> hashtags.add(hashtagRepository.save(new Hashtag(tag))));
        }

        for (Hashtag hashtag : hashtags) {
            FeedHashtag feedHashtag = new FeedHashtag();
            indoor.addFeedHashtag(feedHashtag);
            hashtag.addFeedHashtag(feedHashtag);
        }

        return indoor.getId();
    }

    @Override
    public void uploadFiles(Long feedId, List<MultipartFile> files) throws IOException {
        Feed feed = feedRepository.findById(feedId);

        // 파일 업로드
        for (MultipartFile file : files) {
            String fileName = s3Service.uploadFile(file);
            // 파일 등록
            fileService.addFile(fileName, feed);
        }
    }

    @Override
    public void modify(Long userId, Long feedId, FeedRequestDto feedRequestDto) {
        // 유저 정보
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        Indoor indoor = (Indoor) feedRepository.findById(feedId);

        if (!indoor.getUser().getId().equals(user.getId())) {
            throw new NoSuchElementException();
        }

        // 글 수정
        indoor.update((IndoorRequestDto) feedRequestDto);

        // 태그 찾고 삭제
        List<FeedHashtag> feedHashtags = hashtagRepository.findFeedHashTag(indoor);
        for (FeedHashtag feedHashtag : feedHashtags) {
            indoor.deleteFeedHashtag(feedHashtag);
            feedHashtag.getHashtag().deleteFeedHashtag(feedHashtag);
            hashtagRepository.remove(feedHashtag);
        }

        List<Hashtag> hashtags = new ArrayList<>();

        for (String tag : feedRequestDto.getTags()) {
            hashtagRepository.findByTag(tag).ifPresentOrElse(
                    hashtags::add,
                    () -> hashtags.add(hashtagRepository.save(new Hashtag(tag))));
        }

        for (Hashtag hashtag : hashtags) {
            FeedHashtag feedHashtag = new FeedHashtag();
            indoor.addFeedHashtag(feedHashtag);
            hashtag.addFeedHashtag(feedHashtag);
        }

        // 파일 찾기
        List<String> filePaths = feedRequestDto.getFilePaths();
    }

    @Override
    public boolean delete(Long userId, Long feedId) throws IOException {

        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        Indoor indoor = (Indoor) feedRepository.findById(feedId);
        if (!indoor.getUser().getId().equals(user.getId())) {
            throw new NoSuchElementException();
        }

        // 피드에 저장된 파일들 전부 삭제
        List<String> fileNames = fileService.findFileNameList(feedId);
        for (String fileName : fileNames) {
            s3Service.deleteFile(fileName);
        }

        feedRepository.remove(indoor);
        return true;
    }

}
