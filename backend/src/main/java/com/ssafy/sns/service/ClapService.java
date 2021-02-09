package com.ssafy.sns.service;

import com.ssafy.sns.domain.user.User;

public interface ClapService {

    // 좋아요 토글
    public void changeClap(Long userId, Long feedId);
}
