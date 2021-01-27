package com.ssafy.sns.service;

import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.mypage.ProfileDto;
import com.ssafy.sns.dto.user.KakaoDto;
import com.ssafy.sns.dto.user.UserDto;
import com.ssafy.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User findUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
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
        user.setBio(dto.getIntroduce());
        user.setNickname(dto.getUsername());
        user.setEmail(dto.getUser_id());
        user.setImg(dto.getImgAddr());
    }

    public void saveUser(KakaoDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        userRepository.save(user);
    }

    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return;
        }
        userRepository.delete(user);
    }
}
