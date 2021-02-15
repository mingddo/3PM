package com.ssafy.sns.service;

import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.mypage.ProfileRequestDto;
import com.ssafy.sns.dto.mypage.UserProfileDto;
import com.ssafy.sns.dto.user.KakaoRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    public User findUserById(Long id);

    public User findUserByKakaoId(Long id);

    public UserProfileDto findById(Long id);

    public void deleteUser(Long id);

    public boolean isDuplicate(String username);

    public User joinMember(KakaoRequestDto dto);

    void updateUserProfile(Long id, ProfileRequestDto dto, MultipartFile file) throws IOException;
}
