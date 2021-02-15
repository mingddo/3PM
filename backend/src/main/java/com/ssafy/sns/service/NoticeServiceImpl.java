package com.ssafy.sns.service;

import com.ssafy.sns.domain.clap.FeedClap;
import com.ssafy.sns.domain.comment.Comment;
import com.ssafy.sns.domain.follow.Follow;
import com.ssafy.sns.domain.newsfeed.Feed;
import com.ssafy.sns.domain.notice.Notice;
import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{

    private final NoticeRepositoryImpl noticeRepository;
    private final FollowRepository followRepository;
    private final CommentRepositoryImpl commentRepository;
    private final FeedClapRepositoryImpl feedClapRepository;
    private final FeedRepositoryImpl feedRepository;

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
    public Object findCategory(Long feedId) {
        return feedRepository.findCategory(feedId);
    }

}
