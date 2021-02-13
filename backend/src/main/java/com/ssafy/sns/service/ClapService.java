package com.ssafy.sns.service;

import com.ssafy.sns.dto.user.SimpleUserDto;

import java.util.List;

public interface ClapService {

    // 박수 토글
    public void changeClap(Long userId, Long feedId);

    // 박수 리스트
    public List<SimpleUserDto> clapUserList(Long feedId);
}
