package com.ssafy.sns.controller;


import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.user.DuplDto;
import com.ssafy.sns.dto.user.JwtDto;
import com.ssafy.sns.dto.user.KakaoDto;
import com.ssafy.sns.jwt.JwtService;
import com.ssafy.sns.service.UserServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final JwtService jwtService;

    private final UserServiceImpl userService;

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @ApiOperation("카카오 로그인 후 해당 유저 존재하는 지 확인")
    @PostMapping("/login")
    public ResponseEntity isKakaoLogin(@RequestBody KakaoDto dto) {

        // 해당 유저가 존재하는지 확인
        User user = userService.findUserByKakaoId(dto.getKakaoId());

        // 존재하지 않는 경우 회원 가입
        if (user == null) {
            return new ResponseEntity(false, HttpStatus.OK);
        }

        JwtDto jwtDto = userService.makeJwt(user);

        HttpStatus status = HttpStatus.ACCEPTED;

        return new ResponseEntity<>(jwtDto, status);
    }

    @ApiOperation("회원이 아닌경우 닉네임을 받아서 회원가입")
    @PostMapping("/join")
    public ResponseEntity kakaoLogin(@RequestBody KakaoDto dto) {

        User user = userService.joinMember(dto);

        JwtDto jwtDto = userService.makeJwt(user);

        HttpStatus status = HttpStatus.ACCEPTED;

        return new ResponseEntity<>(jwtDto, status);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable("id") Long id, @RequestBody KakaoDto dto, HttpServletRequest request) {
        User user = userService.checkUserByJwt(request);

        if (user.getId() != id) {
            return new ResponseEntity("사용자 접근 에러", HttpStatus.UNAUTHORIZED);
        }

        user.setNickname(dto.getUsername());
        return new ResponseEntity("성공", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long id, HttpServletRequest request) {
        User user = userService.checkUserByJwt(request);

        if (user.getId() != id) {
            return new ResponseEntity("사용자 접근 에러", HttpStatus.UNAUTHORIZED);
        }

        userService.deleteUser(id);

        return new ResponseEntity("탈퇴 성공", HttpStatus.OK);
    }

    @PostMapping("/dupl")
    public ResponseEntity dupl(@RequestBody DuplDto dto) {

        boolean isDuplicated = userService.isDuplicate(dto.getUsername());

        return new ResponseEntity(isDuplicated, HttpStatus.OK);
    }


}