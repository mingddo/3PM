package com.ssafy.sns.service;

import com.ssafy.sns.domain.user.User;

public interface ClapService {

    // 좋아요 추가
    public void changeClap(Long userId, Long feedId);
}
