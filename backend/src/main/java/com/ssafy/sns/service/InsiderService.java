package com.ssafy.sns.service;

import com.ssafy.sns.domain.group.Group;
import com.ssafy.sns.domain.hashtag.FeedHashtag;
import com.ssafy.sns.domain.hashtag.Hashtag;
import com.ssafy.sns.domain.newsfeed.Feed;
import com.ssafy.sns.domain.newsfeed.Insider;
import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.newsfeed.*;
import com.ssafy.sns.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
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
    private final FollowServiceImpl followService;


    public List<InsiderResDto> getGroupRecommend(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        List<Insider> insiders = insiderRepository.findAll();
        List<InsiderResDto> insiderResDtos = new ArrayList<>();
        List<Insider> recommends = new ArrayList<>();
        // 1. 멤버 많은 그룹 최신 글
        insiders.sort((o1, o2) -> {
            if (o1.getGroup().getGroupMembers().size() == o2.getGroup().getGroupMembers().size()) {
                return o2.getCreatedDate().compareTo(o1.getCreatedDate());
            }
            return o1.getGroup().getGroupMembers().size() - o2.getGroup().getGroupMembers().size();
        });
        recommends.add(insiders.get(0));
        // 2. 멤버 많은 그룹 좋아요 수 많은 글
        insiders.sort((o1, o2) -> {
            if (o1.getGroup().getGroupMembers().size() == o2.getGroup().getGroupMembers().size()) {
                return o2.getFeedClapList().size() - o1.getFeedClapList().size();
            }
            return o1.getGroup().getGroupMembers().size() - o2.getGroup().getGroupMembers().size();
        });
        for (Insider insider : insiders) {
            boolean isIn = false;
            for (Insider recommend: recommends) {
                if (insider.getId() == recommend.getId()) {
                    isIn = true;
                }
            }
            if (!isIn) {
                recommends.add(insider);
                break;
            }
        }
        // 3. 멤버 많은 그룹 좋아요 수 많은 글
        insiders.sort((o1, o2) -> o2.getFeedClapList().size() - o1.getFeedClapList().size());
        for (Insider insider : insiders) {
            boolean isIn = false;
            for (Insider recommend: recommends) {
                if (insider.getId() == recommend.getId()) {
                    isIn = true;
                }
            }
            if (!isIn) {
                recommends.add(insider);
                break;
            }
        }
        // 4. 가장 최근 그룹 피드
        insiders.sort((o1, o2) -> o2.getGroup().getCreatedDate().compareTo(o1.getGroup().getCreatedDate()));
        for (Insider insider : insiders) {
            boolean isIn = false;
            for (Insider recommend: recommends) {
                if (insider.getId() == recommend.getId()) {
                    isIn = true;
                }
            }
            if (!isIn) {
                recommends.add(insider);
                break;
            }
        }
        // 5. 팔로워 가장 많은 사람이 쓴 최신 글
        insiders.sort((o1, o2) -> {
            int o1Cnt = followService.toMeFromOthers(o1.getUser().getId());
            int o2Cnt = followService.toMeFromOthers(o2.getUser().getId());
            if (o1Cnt == o2Cnt) {
                return o2.getCreatedDate().compareTo(o1.getCreatedDate());
            }
            return o2Cnt - o1Cnt;
        });
        for (Insider insider : insiders) {
            boolean isIn = false;
            for (Insider recommend: recommends) {
                if (insider.getId() == recommend.getId()) {
                    isIn = true;
                }
            }
            if (!isIn) {
                recommends.add(insider);
                break;
            }
        }

        for (Insider insider : recommends) {
            insiderResDtos.add(new InsiderResDto(insider,
                    (int) commentRepository.findListById(insider).count(),
                    feedClapRepository.findClapAll(insider).size(),
                    feedClapRepository.findClap(user, insider).isPresent(),
                    2,
                    followService.isFollow(userId, insider),
                    insider.getGroup().getId(),
                    insider.getGroup().getName()));
        }

        return insiderResDtos;
    }

    // 그룹 카테고리의 모든 게시물 출력 (10개씩)
    public FeedListResponseDto findAll(Long userId, int num) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        PageRequest pageRequest = PageRequest.of(num, 10, Sort.by("createdDate").descending());
        Page<Insider> insiders = insiderRepository.findAll(pageRequest);
        List<InsiderResDto> insiderResDtos = new ArrayList<>();
        for (Insider insider : insiders) {
            insiderResDtos.add(new InsiderResDto(insider,
                    (int) commentRepository.findListById(insider).count(),
                    feedClapRepository.findClapAll(insider).size(),
                    feedClapRepository.findClap(user, insider).isPresent(),
                    2,
                    followService.isFollow(userId, insider),
                    insider.getGroup().getId(),
                    insider.getGroup().getName()));
        }
        return new FeedListResponseDto(insiderResDtos, num + 1);
    }

    // 그룹아이디에 속하는 모든 게시물 출력 (10개씩)
    public FeedListResponseDto findAllByGroupId(Long userId, Long groupId, int num) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        PageRequest pageRequest = PageRequest.of(num, 10, Sort.by("createdDate").descending());
        List<Insider> insiders = insiderRepository.findAllByGroupId(groupId, pageRequest);
        List<InsiderResDto> insiderResDtos = new ArrayList<>();
        Group group = groupRepository.findById(groupId).orElseThrow();
        for (Insider insider : insiders) {
            insiderResDtos.add(new InsiderResDto(insider,
                    (int) commentRepository.findListById(insider).count(),
                    feedClapRepository.findClapAll(insider).size(),
                    feedClapRepository.findClap(user, insider).isPresent(),
                    2,
                    followService.isFollow(userId, insider),
                    groupId,
                    group.getName()));
        }
        return new FeedListResponseDto(insiderResDtos, num + 1);
    }

    // 해당 유저가 작성한 모든 그룹 게시물
    public FeedListResponseDto findAllByUserIdAndGroupId(Long viewerId, Long userId, Long groupId, int num) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        User viewer = userRepository.findById(viewerId).orElseThrow(NoSuchElementException::new);
        PageRequest pageRequest = PageRequest.of(num, 10, Sort.by("createdDate").descending());
        List<Insider> insiders = insiderRepository.findAllByUserIdAndGroupId(userId, groupId, pageRequest);
        List<InsiderResDto> insiderResDtos = new ArrayList<>();
        Group group = groupRepository.findById(groupId).orElseThrow();
        for (Insider insider : insiders) {
            insiderResDtos.add(new InsiderResDto(insider,
                    (int) commentRepository.findListById(insider).count(),
                    feedClapRepository.findClapAll(insider).size(),
                    feedClapRepository.findClap(viewer, insider).isPresent(),
                    2,
                    followService.isFollow(userId, insider),
                    groupId,
                    group.getName()));
        }
        return new FeedListResponseDto(insiderResDtos, num + 1);
    }

    // 피드 하나의 detail
    public InsiderResDto findByGroupIdAndFeedId(Long userId, Long feedId, Long groupId) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        Insider insider = insiderRepository.findByGroupIdAndId(groupId, feedId).orElseThrow();
        Group group = groupRepository.findById(groupId).orElseThrow();
        return new InsiderResDto(insider,
                (int) commentRepository.findListById(insider).count(),
                feedClapRepository.findClapAll(insider).size(),
                feedClapRepository.findClap(user, insider).isPresent(),
                2,
                followService.isFollow(userId, insider),
                groupId,
                group.getName());
    }

    // 피드 저장
    public Long save(Long userId, Long groupId, InsiderReqDto insiderReqDto) {
        // 유저 정보
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);

        // 그룹 정보
        Group group = groupRepository.findById(groupId).orElseThrow();

        // 글 등록
        Insider insider = new Insider(insiderReqDto, user, group);
        feedRepository.save(insider); // insiderRepository 인지 뭔지 헷갈리네,,,

        // 파일 저장하기
        for (String fileName : insiderReqDto.getFilePaths()) {
            fileService.addFile(fileName, insider);
        }

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

    // 한 유저가 작성한 그룹 카테고리 내 모든 게시물 (10개 단위)
    public FeedListResponseDto findAllByUser(Long viewerId, Long userId, int num) {
//        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        User viewer = userRepository.findById(viewerId).orElseThrow(NoSuchElementException::new);
        PageRequest pageRequest = PageRequest.of(num, 10, Sort.by("createdDate").descending());
        List<Insider> insiders = insiderRepository.findAllByUserId(userId, pageRequest);
        List<InsiderResDto> insiderResDtos = new ArrayList<>();
        for (Insider insider : insiders) {
            insiderResDtos.add(new InsiderResDto(insider,
                    (int) commentRepository.findListById(insider).count(),
                    feedClapRepository.findClapAll(insider).size(),
                    feedClapRepository.findClap(viewer, insider).isPresent(),
                    2,
                    followService.isFollow(viewerId, insider),
                    insider.getGroup().getId(),
                    insider.getGroup().getName()));
        }
        return new FeedListResponseDto(insiderResDtos, num + 1);
    }

    public boolean isMyGroup(User user, Long feedId) {
        Insider insider = insiderRepository.findById(feedId).orElseThrow();
        List<Long> groupIds = user.getGroupMembers().stream()
                .map(groupMember -> groupMember.getGroup().getId())
                .collect(Collectors.toList());
        Long feedGroupId = insider.getGroup().getId();

        for (Long groupId : groupIds) {
            if (groupId == feedGroupId) {
                return true;
            }
        }
        return false;
    }
}
