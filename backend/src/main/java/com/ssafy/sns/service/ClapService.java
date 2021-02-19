package com.ssafy.sns.service;

import com.ssafy.sns.dto.clap.ClapResponseDto;

public interface ClapService {

    // 박수 토글
    public void changeClap(Long userId, Long targetId);

    // 박수 리스트
    public ClapResponseDto clapUserList(Long targetId);
}
