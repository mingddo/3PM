package com.ssafy.sns.service;

import com.ssafy.sns.domain.group.Group;
import com.ssafy.sns.domain.hashtag.FeedHashtag;
import com.ssafy.sns.domain.hashtag.Hashtag;
import com.ssafy.sns.domain.newsfeed.Feed;
import com.ssafy.sns.domain.newsfeed.Indoor;
import com.ssafy.sns.domain.newsfeed.Insider;
import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.newsfeed.*;
import com.ssafy.sns.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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
    private final InsiderRepository insiderRepository;
    private final GroupRepository groupRepository;

    // 그룹아이디에 속하는 모든 게시물 출력 (10개씩)
    public FeedListResponseDto findAllByGroupId(Long userId, Long groupId, int num) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        PageRequest pageRequest = PageRequest.of(num, 10, Sort.by("createdDate").descending());
        List<Insider> insiders = insiderRepository.findAllByGroupId(groupId, pageRequest);
        List<InsiderResDto> insiderResDtos = new ArrayList<>();
        for (Insider insider : insiders) {
            insiderResDtos.add(new InsiderResDto(insider,
                    (int) commentRepository.findListById(insider).count(),
                    feedClapRepository.findClapAll(insider).size(),
                    feedClapRepository.findClap(user, insider).isPresent(),
                    3,
                    groupId));
        }
        return new FeedListResponseDto(insiderResDtos, num + insiders.size());
    }

    // 해당 유저가 작성한 모든 그룹 게시물
    public FeedListResponseDto findAllByUserIdAndGroupId(Long viewerId, Long userId, Long groupId, int num) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        User viewer = userRepository.findById(viewerId).orElseThrow(NoSuchElementException::new);
        PageRequest pageRequest = PageRequest.of(num, 10, Sort.by("createdDate").descending());
        List<Insider> insiders = insiderRepository.findAllByUserIdAndGroupId(userId, groupId, pageRequest);
        List<InsiderResDto> insiderResDtos = new ArrayList<>();
        for (Insider insider : insiders) {
            insiderResDtos.add(new InsiderResDto(insider,
                    (int) commentRepository.findListById(insider).count(),
                    feedClapRepository.findClapAll(insider).size(),
                    feedClapRepository.findClap(viewer, insider).isPresent(),
                    3,
                    groupId));
        }
        return new FeedListResponseDto(insiderResDtos, num + insiders.size());
    }

    // 피드 하나의 detail
    public FeedResponseDto findByGroupIdAndFeedId(Long userId, Long feedId, Long groupId) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        Insider insider = insiderRepository.findByGroupIdAndId(groupId, feedId).orElseThrow();
        return new FeedResponseDto(insider,
                (int) commentRepository.findListById(insider).count(),
                feedClapRepository.findClapAll(insider).size(),
                feedClapRepository.findClap(user, insider).isPresent(),
                3);
    }

    // 피드 저장
    public Long save(Long userId, Long groupId, InsiderReqDto insiderReqDto) {
        // 유저 정보
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);

        // 그룹 정보
        Group group = groupRepository.findById(groupId).orElseThrow();

        // 글 등록
        Insider insider = new Insider(insiderReqDto.getContent(), user, group);
        feedRepository.save(insider); // insiderRepository 인지 뭔지 헷갈리네,,,

        // 태그 등록
        List<Hashtag> hashtags = new ArrayList<>();

        for (String tag : insiderReqDto.getTags()) {
            hashtagRepository.findByTag(tag).ifPresentOrElse(
                    hashtags::add,
                    () -> hashtags.add(hashtagRepository.save(new Hashtag(tag))));
        }

        for (Hashtag hashtag : hashtags) {
            FeedHashtag feedHashtag = new FeedHashtag();
            insider.addFeedHashtag(feedHashtag);
            hashtag.addFeedHashtag(feedHashtag);
        }

        return insider.getId();
    }

    // 파일 업로드
    public void uploadFiles(Long feedId, MultipartFile file) throws IOException {
        Feed feed = feedRepository.findById(feedId);

        // 파일 업로드
        String fileName = s3Service.uploadFile(file);
        // 파일 등록
        fileService.addFile(fileName, feed);
    }

    // 피드 수정
    public void updateFeed(Long userId, Long groupId, Long feedId, InsiderReqDto insiderReqDto) throws IOException {
        // 유저 정보
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);

        // 그룹 정보
        Group group = groupRepository.findById(groupId).orElseThrow();

        // 피드 정보
        Insider insider = insiderRepository.findById(feedId).orElseThrow();

        if (!insider.getUser().getId().equals(user.getId())) {
            throw new NoSuchElementException();
        }

        // 컨텐츠 수정
        insider.update(insiderReqDto.getContent());

        // 태그 찾고 삭제
        List<FeedHashtag> feedHashtags = hashtagRepository.findFeedHashTag(insider);
        for (FeedHashtag feedHashtag : feedHashtags) {
            insider.deleteFeedHashtag(feedHashtag);
            feedHashtag.getHashtag().deleteFeedHashtag(feedHashtag);
            hashtagRepository.remove(feedHashtag);
        }

        List<Hashtag> hashtags = new ArrayList<>();

        for (String tag : insiderReqDto.getTags()) {
            hashtagRepository.findByTag(tag).ifPresentOrElse(
                    hashtags::add,
                    () -> hashtags.add(hashtagRepository.save(new Hashtag(tag))));
        }

        for (Hashtag hashtag : hashtags) {
            FeedHashtag feedHashtag = new FeedHashtag();
            insider.addFeedHashtag(feedHashtag);
            hashtag.addFeedHashtag(feedHashtag);
        }

        // 파일 변경 사항 삭제
        List<String> prevFileNames =  insider.getFileList().stream()
                .map(file -> file.getFileName())
                .collect(Collectors.toList());
        List<String> curFileNames = insiderReqDto.getFilePaths();
        fileService.modifyFiles(prevFileNames, curFileNames);
    }

    // 피드 삭제
    public boolean deleteFeed(Long userId, Long groupId, Long feedId) throws IOException {

        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        Insider insider = insiderRepository.findById(feedId).orElseThrow();
        if (!insider.getUser().getId().equals(user.getId())) {
            throw new NoSuchElementException();
        }

        // 피드에 저장된 파일들 전부 삭제
        List<String> fileNames = fileService.findFileNameList(feedId);
        for (String fileName : fileNames) {
            s3Service.deleteFile(fileName);
        }

//        feedRepository.remove(insider); // 이게 정석일 거 같지만
        insiderRepository.delete(insider); // 이거로 해도 될듯?
        return true;
    }

}
