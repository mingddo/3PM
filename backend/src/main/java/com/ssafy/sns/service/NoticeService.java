package com.ssafy.sns.service;

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
    
}
