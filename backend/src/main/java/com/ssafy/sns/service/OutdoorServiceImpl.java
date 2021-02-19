package com.ssafy.sns.service;

import com.ssafy.sns.domain.hashtag.FeedHashtag;
import com.ssafy.sns.domain.hashtag.Hashtag;
import com.ssafy.sns.domain.newsfeed.Feed;
import com.ssafy.sns.domain.newsfeed.Indoor;
import com.ssafy.sns.domain.newsfeed.Outdoor;
import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.newsfeed.*;
import com.ssafy.sns.repository.*;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OutdoorServiceImpl implements FeedService {

    private final FeedRepositoryImpl feedRepository;
    private final HashtagRepositoryImpl hashtagRepository;
    private final FeedClapRepositoryImpl feedClapRepository;
    private final CommentRepositoryImpl commentRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;
    private final FileServiceImpl fileService;
    private final FollowServiceImpl followService;
    private final OutdoorRepo outdoorRepo;
    private final FeedClapRepo feedClapRepo;

    @Override
    public FeedListResponseDto findMyList(Long userId, Long targetId, int num) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        List<Feed> outdoorList = feedRepository.findMyList(targetId, num, Outdoor.class);
        List<OutdoorResponseDto> outdoorResponseDtoList = new ArrayList<>();
        for (Feed feed : outdoorList) {
            outdoorResponseDtoList.add(new OutdoorResponseDto((Outdoor) feed,
                    (int) commentRepository.findListById(feed).count(),
                    feedClapRepository.findClapAll(feed).size(),
                    feedClapRepository.findClap(user, feed).isPresent(),
                    followService.isFollow(userId, feed)));
        }
        return new FeedListResponseDto<>(outdoorResponseDtoList, num + outdoorList.size());
    }

    @Override
    public FeedListResponseDto readList(Long userId, int num) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        List<Feed> outdoorList = feedRepository.findList(num, Outdoor.class);
        List<OutdoorResponseDto> outdoorResponseDtoList = new ArrayList<>();
        for (Feed feed : outdoorList) {
            outdoorResponseDtoList.add(new OutdoorResponseDto((Outdoor) feed,
                    (int) commentRepository.findListById(feed).count(),
                    feedClapRepository.findClapAll(feed).size(),
                    feedClapRepository.findClap(user, feed).isPresent(),
                    followService.isFollow(userId, feed)));
        }
        return new FeedListResponseDto<>(outdoorResponseDtoList, num + outdoorList.size());
    }

    @Override
    public FeedResponseDto read(Long userId, Long feedId) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        Feed feed = feedRepository.findById(feedId);
        if (!(feed instanceof Outdoor)) throw new NoSuchElementException();
        return new OutdoorResponseDto((Outdoor) feed,
                (int) commentRepository.findListById(feed).count(),
                feedClapRepository.findClapAll(feed).size(),
                feedClapRepository.findClap(user, feed).isPresent(),
                followService.isFollow(userId, feed));
    }

    @Override
    public Long write(Long userId, FeedRequestDto feedRequestDto) {
        // 유저 정보
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);

        // 글 등록
        Outdoor outdoor = ((Outdoor) feedRepository.save(new Outdoor(feedRequestDto, user)));
        user.addFeed(outdoor);

        // 파일 저장하기
        for (String fileName : feedRequestDto.getFilePaths()) {
            fileService.addFile(fileName, outdoor);
        }

        // 태그 등록
        List<Hashtag> hashtags = new ArrayList<>();

        for (String tag : feedRequestDto.getTags()) {
            hashtagRepository.findByTag(tag).ifPresentOrElse(
                    hashtags::add,
                    () -> hashtags.add(hashtagRepository.save(new Hashtag(tag))));
        }

        for (Hashtag hashtag : hashtags) {
            FeedHashtag feedHashtag = new FeedHashtag();
            outdoor.addFeedHashtag(feedHashtag);
            hashtag.addFeedHashtag(feedHashtag);
        }

        return outdoor.getId();
    }

    @Override
    public void uploadFiles(Long feedId, MultipartFile file) throws IOException {
        Feed feed = feedRepository.findById(feedId);

        // 파일 업로드
        String fileName = s3Service.uploadFile(file);
        // 파일 등록
        fileService.addFile(fileName, feed);
    }

    @Override
    public void delete(Long userId, Long feedId) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        Outdoor outdoor = (Outdoor) feedRepository.findById(feedId);
        if (!outdoor.getUser().getId().equals(user.getId())) {
            throw new NoSuchElementException();
        }

        // 피드에 저장된 파일들 전부 삭제
        List<String> fileNames = fileService.findFileNameList(feedId);
        for (String fileName : fileNames) {
            s3Service.deleteFile(fileName);
        }

        user.deleteFeed(outdoor);
        feedRepository.remove(outdoor);
    }

    @Override
    public void modify(Long userId, Long feedId, FeedRequestDto feedRequestDto) throws IOException {
        // 유저 정보
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        Outdoor outdoor = (Outdoor) feedRepository.findById(feedId);

        if (!outdoor.getUser().getId().equals(user.getId())) {
            throw new NoSuchElementException();
        }

        // 글 수정
        outdoor.update((OutdoorRequestDto) feedRequestDto);

        // 태그 찾고 삭제
        List<FeedHashtag> feedHashtags = hashtagRepository.findFeedHashTag(outdoor);
        for (FeedHashtag feedHashtag : feedHashtags) {
            outdoor.deleteFeedHashtag(feedHashtag);
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
            outdoor.addFeedHashtag(feedHashtag);
            hashtag.addFeedHashtag(feedHashtag);
        }

        // 파일 변경 사항 삭제
        List<String> prevFileNames =  outdoor.getFileList().stream()
                .map(file -> file.getFileName())
                .collect(Collectors.toList());
        List<String> curFileNames = feedRequestDto.getFilePaths();
        fileService.modifyFiles(prevFileNames, curFileNames);
    }

    public FeedListResponseDto feedRecommend(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        // 3일 이내 피드들 리스트
        System.out.println(LocalDateTime.now().minusDays(3));
        List<Outdoor> outdoors = outdoorRepo.findAll().stream()
                .filter(outdoor -> outdoor.getCreatedDate().isAfter(LocalDateTime.now().minusDays(3)))
                .sorted((o1, o2) -> o2.getFeedClapList().size() - o1.getFeedClapList().size())
                .collect(Collectors.toList());

        Outdoor[] recommends = new Outdoor[18]; // 1 ~ 17 사용
        int cnt = 0;
        for (Outdoor outdoor : outdoors) {
            if (outdoor.getCode() == null) {
                continue;
            }
            int code = outdoor.getCode();

            if (recommends[code] == null) {
                recommends[code] = outdoor;
                cnt++;
            }
            if (cnt == 17) {
                break;
            }
        }

        List<OutdoorResponseDto> outdoorResponseDtoList = new ArrayList<>();
        for (Outdoor outdoor : recommends) {
            if(outdoor == null) {
                continue;
            }
            outdoorResponseDtoList.add(new OutdoorResponseDto(outdoor,
                    (int) commentRepository.findListById(outdoor).count(),
                    feedClapRepository.findClapAll(outdoor).size(),
                    feedClapRepository.findClap(user, outdoor).isPresent(),
                    followService.isFollow(userId, outdoor)));
        }

        System.out.println(11111111);
        return new FeedListResponseDto<>(outdoorResponseDtoList, 0);
    }
}
