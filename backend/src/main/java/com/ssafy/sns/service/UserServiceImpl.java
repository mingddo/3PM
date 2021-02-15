package com.ssafy.sns.service;

import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.mypage.ProfileRequestDto;
import com.ssafy.sns.dto.mypage.UserProfileDto;
import com.ssafy.sns.dto.user.KakaoRequestDto;
import com.ssafy.sns.repository.UserRepository;
import com.ssafy.sns.util.UnicodeHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final S3Service s3Service;
    private final UnicodeHandler unicodeHandler;
    private final FollowServiceImpl followService;


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


    @Override
    public void updateUserProfile(Long id, ProfileRequestDto dto, MultipartFile file) throws IOException { // id 가 유효할 경우 이 메소드가 실행된다
        User user = userRepository.findById(id).get();

        if (dto.getIntroduce() != null) {
            user.setIntroduce(dto.getIntroduce());
        }
        if (dto.getNickname() != null) {
            user.setNickname(dto.getNickname());
            user.setNicknameSplit(unicodeHandler.splitHangeulToConsonant(dto.getNickname()));
        }

        // file 업로드는 exception 날 수 있다.
        if (file == null) {
            return;
        }
        String filename = s3Service.uploadProfile(user.getImg(), file);
        user.setImg(filename);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return;
        }
        userRepository.delete(user);
    }

    public boolean isDuplicate(String username) {

        User user = userRepository.findByNickname(username).orElse(null);

        return user != null;
    }

    public User joinMember(KakaoRequestDto dto) {
        User user = new User();
        user.setKakaoId(dto.getKakaoId());
        user.setNickname(dto.getUsername());
        user.setNicknameSplit(unicodeHandler.splitHangeulToConsonant(dto.getUsername()));

        return userRepository.save(user);
    }

    public List<UserProfileDto> findAllUser() {
         return userRepository.findAll().stream()
                 .map(user -> {
                     Long userId = user.getId();
                     int fromMeToOthersCnt = followService.fromMeToOthers(userId);
                     int toMeFromOthersCnt = followService.toMeFromOthers(userId);
                     int groupCnt = user.getGroupMembers().size(); // 나중에 Group Entity 생기면 추가 예정

                     return UserProfileDto.builder()
                             .username(user.getNickname())
                             .user_id(user.getId())
                             .user_img(user.getImg())
                             .fromMeToOthersCnt(fromMeToOthersCnt)
                             .toMeFromOthersCnt(toMeFromOthersCnt)
                             .groupCnt(groupCnt)
                             .introduce(user.getIntroduce())
                             .build();
                 }).collect(Collectors.toList());
    }
}
