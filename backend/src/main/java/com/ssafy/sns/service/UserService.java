package com.ssafy.sns.service;

import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.mypage.ProfileDto;
import com.ssafy.sns.dto.user.KakaoDto;
import com.ssafy.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }

    public User findUserByKakaoId(Long id) {
        Optional<User> user = userRepository.findByKakaoId(id);
        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }

    public List<User> findAllById(Long id) {
        return userRepository.findAllById(id);
    }


    public void updateUserProfile(Long id, ProfileDto dto) { // id 가 유효할 경우 이 메소드가 실행된다
        User user = userRepository.findById(id).get();
        user.setIntroduce(dto.getIntroduce());
        user.setNickname(dto.getNickname());
        user.setImg(dto.getProfilesrc());
    }

    public User saveUser(KakaoDto dto) {
        User user = new User();
        user.setKakaoId(dto.getKakaoId());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return;
        }
        userRepository.delete(user);
    }
}
