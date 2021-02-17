package com.ssafy.sns.service;

import com.ssafy.sns.domain.clap.FeedClap;
import com.ssafy.sns.domain.comment.Comment;
import com.ssafy.sns.domain.follow.Follow;
import com.ssafy.sns.domain.group.Group;
import com.ssafy.sns.domain.notice.Notice;
import com.ssafy.sns.domain.user.User;

import java.util.List;

public interface NoticeService {

    // 팔로우 알림 전체목록 불러오기
    List<Notice> followList(User user);

    // 좋아요 알림 전체목록 불러오기
    List<Notice> feedClabList(User user);

    // 댓글 알림 전체목록 불러오기
    List<Notice> commentList(User user);

    // 그룹 알림 전체목록 불러오기
    List<Notice> groupList(User user);

    Follow findFollow(Long followId);

    Comment findComment(Long commentId);

    FeedClap findFeedClap(Long feedClapId);

    Group findGroup(Long groupId);

    Object findCategory(Long feedId);
    
}
