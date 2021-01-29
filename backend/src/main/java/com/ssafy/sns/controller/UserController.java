package com.ssafy.sns.controller;


import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.user.DuplDto;
import com.ssafy.sns.dto.user.KakaoDto;
import com.ssafy.sns.dto.user.KakaoDto2;
import com.ssafy.sns.jwt.JwtService;
import com.ssafy.sns.repository.UserRepository;
import com.ssafy.sns.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final JwtService jwtService;

    private final UserService userService;

    private final UserRepository userRepository;

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/kakao")
    public ResponseEntity isKakaoLogin(@RequestBody KakaoDto dto) {

        // 해당 유저가 존재하는지 확인
        User user = userService.findUserByKakaoId(dto.getKakaoId());

        // 존재하지 않는 경우 회원 가입
        if (user == null) {
            System.out.println("회원가입 지금 한다");
//            userService.saveUser(dto);
            return new ResponseEntity(false, HttpStatus.OK);
        } else {
            Map<String, Object> resultMap = new HashMap<>();
            String token = jwtService.create(user);
            logger.trace("로그인 토큰정보 : {}", token);

            // 토큰 정보는 response의 헤더로 보내고 나머지는 Map에 담는다.
            resultMap.put("auth-token", token);
            resultMap.put("id", user.getId());
            resultMap.put("name", user.getNickname());
            HttpStatus status = HttpStatus.ACCEPTED;

            return new ResponseEntity<>(resultMap, status);
        }
    }

    @PostMapping("/kakao2")
    public ResponseEntity kakaoLogin(@RequestBody KakaoDto2 dto) {

        User user = new User();
        user.setKakaoId(dto.getKakaoId());
        user.setNickname(dto.getUsername());
        userRepository.save(user);
        System.out.println(user);

        Map<String, Object> resultMap = new HashMap<>();
        String token = jwtService.create(user);
        logger.trace("로그인 토큰정보 : {}", token);

        // 토큰 정보는 response의 헤더로 보내고 나머지는 Map에 담는다.
        resultMap.put("auth-token", token);
        resultMap.put("id", user.getId());
        resultMap.put("name", user.getNickname());
        HttpStatus status = HttpStatus.ACCEPTED;

        return new ResponseEntity<>(resultMap, status);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long id) {

        userService.deleteUser(id);

        return new ResponseEntity("탈퇴 성공", HttpStatus.OK);

    }

    @PostMapping("/dupl")
    public ResponseEntity dupl(@RequestBody DuplDto dto) {
        User user = userRepository.findByNickname(dto.getUsername()).orElse(null);

        boolean result = true;
        if (user != null) {
            result = false;
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

}