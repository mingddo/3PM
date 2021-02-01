package com.ssafy.sns.service;

import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.mypage.ProfileDto;
import com.ssafy.sns.dto.user.KakaoDto;
import com.ssafy.sns.dto.user.KakaoDto2;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public User findUserById(Long id);

    public User findUserByKakaoId(Long id);

    public List<User> findAllById(Long id);

    public void updateUserProfile(Long id, ProfileDto dto);

    public User saveUser(KakaoDto dto);

    public void deleteUser(Long id);

    public boolean isDuplicate(String username);

    public User joinMember(KakaoDto2 dto);
}
