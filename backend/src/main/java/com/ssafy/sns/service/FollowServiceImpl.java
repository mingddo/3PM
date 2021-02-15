package com.ssafy.sns.service;

import com.ssafy.sns.domain.follow.Follow;
import com.ssafy.sns.domain.newsfeed.Feed;
import com.ssafy.sns.domain.notice.Notice;
import com.ssafy.sns.domain.notice.NoticeFollow;
import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.user.UserFollowDto;
import com.ssafy.sns.repository.FeedRepository;
import com.ssafy.sns.repository.FollowRepository;
import com.ssafy.sns.repository.NoticeRepositoryImpl;
import com.ssafy.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class FollowServiceImpl {

    private final UserRepository userRepository;

    private final FollowRepository followRepository;

    private final FeedRepository feedRepository;

    public int fromMeToOthers(Long id) {
        return (int) followRepository.countByFromUserId(id);
    }

    public int toMeFromOthers(Long id) {
        return (int) followRepository.countByToUserId(id);
    }

    private final NoticeRepositoryImpl noticeRepository;

//    public List<Long> fromMeToOthersList(Long id) {
//        List<Follow> follows = followRepository.findByFromId(id);
//
//        List<Long> result = new ArrayList<>();
//        follows.stream().forEach(follow -> result.add(follow.getFromId()));
//        return result;
//    }
//
//    public List<Long> toMeFromOthersList(Long id) {
//        List<Follow> follows = followRepository.findByToId(id);
//
//        List<Long> result = new ArrayList<>();
//        follows.stream().forEach(follow -> result.add(follow.getToId()));
//        return result;
//    }

    // 피드를 보고 팔로우 여부를 알아내기
    public boolean isFollow(Long fromUserId, Feed feed) {
        Long toUserId = feed.getUser().getId();
        return followRepository.findByToUserAndFromUser(fromUserId, toUserId).isPresent();
    }

    // 팔로윙 토글
    public void addFollow(Long fromUserId, Long toUserId) {
        User toUser = userRepository.findById(toUserId)
                .orElseThrow();

        User fromUser = userRepository.findById(fromUserId)
                .orElseThrow();

        if (followRepository.findByToUserAndFromUser(fromUser.getId(), toUser.getId()).isPresent()) {
            // 팔로윙 취소
            followRepository.deleteByUser(fromUser.getId(), toUser.getId());
        } else {
            Follow follow = new Follow(fromUser, toUser);
            // 팔로잉
            Follow save = followRepository.save(follow);
            noticeRepository.save(new NoticeFollow(save.getId()));
        }
    }

    // 팔로잉 리스트를 가져온다
    public List<UserFollowDto> getFollowingList(Long fromUserId, Long toUserId) {

        User fromUser = userRepository.findById(fromUserId)
                .orElseThrow(); // 토큰 (접속자)

        User toUser = userRepository.findById(toUserId)
                .orElseThrow(); // 페이지 주인

        // 페이지 주인의 팔로잉 리스트를 가져온다
        List<UserFollowDto> followingList = followRepository.findAllByFromUser(toUser).stream()
                .map(Follow::getToUser)
                .map(UserFollowDto::new)
                .collect(Collectors.toList());

        for (UserFollowDto users : followingList) {
            if (followRepository.findByToUserAndFromUser(fromUser.getId(), users.getId()).isPresent()) {
                users.setIsFollow(true);
            }
        }

        return followingList;
    }

    // 팔로워 리스트 가져오기
    public List<UserFollowDto> getFollowerList(Long fromUserId, Long toUserId) {
        User fromUser = userRepository.findById(fromUserId)
                .orElseThrow();

        User toUser = userRepository.findById(toUserId)
                .orElseThrow();

        List<UserFollowDto> followerList = followRepository.findAllByToUser(toUser).stream()
                .map(Follow::getFromUser)
                .map(UserFollowDto::new)
                .collect(Collectors.toList());

        for (UserFollowDto users : followerList) {
            if (followRepository.findByToUserAndFromUser(fromUser.getId(), users.getId()).isPresent()) {
                users.setIsFollow(true);
            }
        }

        return followerList;
    }
}
