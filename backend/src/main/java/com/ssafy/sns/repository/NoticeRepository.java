package com.ssafy.sns.repository;

import com.ssafy.sns.domain.clap.FeedClap;
import com.ssafy.sns.domain.comment.Comment;
import com.ssafy.sns.domain.follow.Follow;
import com.ssafy.sns.domain.notice.Notice;
import com.ssafy.sns.domain.notice.NoticeComment;
import com.ssafy.sns.domain.notice.NoticeFeedClap;
import com.ssafy.sns.domain.notice.NoticeFollow;
import com.ssafy.sns.domain.user.User;

import java.util.List;


public interface NoticeRepository {

    Notice save(Notice notice);

    void delete(Notice notice);

    List<Notice> followList(User user);

    List<Notice> commentList(User user);

    List<Notice> feedClabList(User user);
}
