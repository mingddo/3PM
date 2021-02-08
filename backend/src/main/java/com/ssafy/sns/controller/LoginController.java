package com.ssafy.sns.controller;


import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.user.DuplDto;
import com.ssafy.sns.dto.user.JwtDto;
import com.ssafy.sns.dto.user.KakaoDto;
import com.ssafy.sns.jwt.JwtService;
import com.ssafy.sns.service.UserServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
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
public class LoginController {

    private final JwtService jwtService;

    private final UserServiceImpl userService;

    public static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/token")
    public ResponseEntity<Map<String, String>> getToken(HttpServletRequest request) {
        HttpStatus status = HttpStatus.ACCEPTED;
        Map<String, String> result = new HashMap<>();

        String token = request.getHeader("Authorization");
        String accToken = jwtService.createAccessToken(token);
        result.put("result", accToken);
        logger.info("Access Token 재발급 완료 {}", accToken);

        status = HttpStatus.OK;

        return new ResponseEntity<>(result ,status);
    }

    @ApiOperation("카카오 로그인 후 해당 유저 존재하는지 확인")
    @PostMapping("/login")
    public ResponseEntity isKakaoLogin(@RequestBody KakaoDto dto) {

        // 해당 유저가 존재하는지 확인
        User user = userService.findUserByKakaoId(dto.getKakaoId());

        // 존재하지 않는 경우 회원 가입
        if (user == null) {
            return new ResponseEntity(false, HttpStatus.OK);
        }

        JwtDto jwtDto = jwtService.login(user.getId());
        HttpStatus status = HttpStatus.ACCEPTED;

        return new ResponseEntity<>(jwtDto, status);
    }

    @ApiOperation("회원이 아닌경우 닉네임을 받아서 회원가입")
    @PostMapping("/join")
    public ResponseEntity kakaoLogin(@RequestBody KakaoDto dto) {

        User user = userService.joinMember(dto);

        JwtDto jwtDto = jwtService.login(user.getId());
        HttpStatus status = HttpStatus.ACCEPTED;

        return new ResponseEntity<>(jwtDto, status);
    }

    @PostMapping("/dupl")
    public ResponseEntity dupl(@RequestBody DuplDto dto) {

        boolean isDuplicated = userService.isDuplicate(dto.getUsername());

        return new ResponseEntity(isDuplicated, HttpStatus.OK);
    }


}