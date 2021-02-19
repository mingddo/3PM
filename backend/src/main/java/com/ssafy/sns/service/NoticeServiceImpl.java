package com.ssafy.sns.service;

import com.ssafy.sns.domain.clap.FeedClap;
import com.ssafy.sns.domain.comment.Comment;
import com.ssafy.sns.domain.follow.Follow;
import com.ssafy.sns.domain.group.Group;
import com.ssafy.sns.domain.notice.Notice;
import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{

    private final NoticeRepositoryImpl noticeRepository;
    private final FollowRepository followRepository;
    private final CommentRepositoryImpl commentRepository;
    private final FeedClapRepositoryImpl feedClapRepository;
    private final FeedRepositoryImpl feedRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final GroupRepository groupRepository;

    @Override
    public List<Notice> followList(User user) {
        return noticeRepository.followList(user);
    }

    @Override
    public List<Notice> feedClabList(User user) {
        return noticeRepository.feedClabList(user);
    }

    @Override
    public List<Notice> commentList(User user) {
        return noticeRepository.commentList(user);
    }

    @Override
    public List<Notice> groupList(User user) {
        List<Group> groupList = noticeRepository.groupLeader(user);
        List<Notice> noticeList = new ArrayList<>();
        for (Group g : groupList) {
            noticeList = Stream.concat(noticeList.stream(), noticeRepository.groupList(g).stream())
                    .collect(Collectors.toList());
        }
        return noticeList;
    }

    @Override
    public Follow findFollow(Long followId) {
        return followRepository.findFollowById(followId);
    }

    @Override
    public Comment findComment(Long commentId) {
        return commentRepository.findById(commentId);
    }

    @Override
    public FeedClap findFeedClap(Long feedClapId) {
        return feedClapRepository.findById(feedClapId);
    }

    @Override
    public Group findGroup(Long groupId) {
        return groupRepository.findById(groupId).get();
    }

    @Override
    public Object findCategory(Long feedId) {
        return feedRepository.findCategory(feedId);
    }

}
