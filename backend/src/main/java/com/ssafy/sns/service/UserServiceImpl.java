package com.ssafy.sns.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.sns.controller.UserController;
import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.mypage.ProfileRequestDto;
import com.ssafy.sns.dto.user.JwtDto;
import com.ssafy.sns.dto.user.KakaoDto;
import com.ssafy.sns.jwt.JwtService;
import com.ssafy.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final S3Service s3Service;

    private final ObjectMapper objectMapper;

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

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
        user.setIntroduce(dto.getIntroduce());
        user.setNickname(dto.getNickname());

        // file 업로드는 exception 날 수 있다.
        String filename = s3Service.upload(user.getImg(), file);
        System.out.println(filename);
        user.setImg("https://"+s3Service.CLOUD_FRONT_DOMAIN_NAME+ "/" + filename);
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

    public User joinMember(KakaoDto dto) {
        User user = new User();
        user.setKakaoId(dto.getKakaoId());
        user.setNickname(dto.getUsername());

        return userRepository.save(user);
    }

    public JwtDto makeJwt(User user) {
        String token = jwtService.create(user);
        logger.trace("로그인 토큰정보 : {}", token);

        return JwtDto.builder()
                .auth_token(token)
                .id(user.getId())
                .name(user.getNickname())
                .build();
    }

    @Override
    public User checkUserByJwt(HttpServletRequest request) {
        String jwt = request.getHeader("auth_token");
        return objectMapper.convertValue(jwtService.get(jwt).get("user"), User.class);
    }
}
