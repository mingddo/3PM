package com.ssafy.sns.controller;

import com.ssafy.sns.domain.newsfeed.Indoor;
import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.mypage.ProfileDto;
import com.ssafy.sns.dto.mypage.SubscribeUserDto;
import com.ssafy.sns.dto.mypage.UserProfileDto;
import com.ssafy.sns.service.FollowService;
import com.ssafy.sns.service.IndoorService;
import com.ssafy.sns.service.MyPageService;
import com.ssafy.sns.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * MyPage 관련 Controller
 */
@RequiredArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("/mypage")
public class MyPageController {

    private final MyPageService myPageService;

    private final UserService userService;

    private final FollowService followService;

    private final IndoorService indoorService;

    /**
     * [마이페이지 유저 정보] [프로필 정보]
     * 모든 사용자의 프로필에 들어갔을 때 처음 받는 정보
     * username, user_id, 내가 구독하는 수, 나를 구독하는 수, 가입된 그룹 수, 프로필 소개,
     * 게시글 10개씩 ( 좋아요 수, 댓글 수, 게시글 id, 게시글 사진/동영상, 게시글 내용 )
     */
    @GetMapping("/{email}")
    public ResponseEntity myPageMain(@PathVariable("email") String email) {

        // 1. email 로 회원을 찾아보고 없으면 찾는 리소스가 없다고 응답 404 Not Found
        User userDto = userService.findUserByEmail(email);
        if (userDto == null) {
            return new ResponseEntity("회원 정보가 없습니다", HttpStatus.NOT_FOUND);
        }

        // 2. 회원 정보가 있는 경우 dto 에 데이터 넣고 리턴
        Long id = userDto.getId();
        int fromMeToOthersCnt = followService.fromMeToOthers(id);
        int toMeFromOthersCnt = followService.toMeFromOthers(id);
        int groupCnt = 0; // 나중에 Group Entity 생기면 추가 예정
        List<Indoor> newsfeeds = indoorService.findAll();

        UserProfileDto result = UserProfileDto.builder()
                .username(userDto.getNickname())
                .user_id(userDto.getEmail())
                .fromMeToOthersCnt(fromMeToOthersCnt)
                .toMeFromOthersCnt(toMeFromOthersCnt)
                .groupCnt(groupCnt)
                .introduce(userDto.getBio())
                .newsfeeds(newsfeeds)
                .build();

        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * [최근활동 탭 선택]
     * 알림 쏜 주체의 프로필 사진 ( 그룹이면 그룹 사진 )
     * 게시물 알림 ( 내가 좋아요 누른 게시물 id )
     * 팔로우 알림 ( 나를 팔로우하는 사람 사진 및 username )
     * 본인만 접근가능 => 접근 제한
     */
    @GetMapping("/alert/{email}")
    public ResponseEntity myPageAlert(@PathVariable("email") String email) {


        return null;
    }

    /**
     * [구독 탭 선택]
     * 내가 소식 받아보는 사람 목록 ( 구독한 사람 )
     * user profile, username, user_id
     */
    @GetMapping("/subscribe/{email}")
    public ResponseEntity subscribeList(@PathVariable("email") String email) {
        // 0. jwt 로 본인인지 아닌지를 파악할 계획

        // 1. email 없는 경우
        User userDto = userService.findUserByEmail(email);
        if (userDto == null) {
            return new ResponseEntity("회원 정보가 없습니다", HttpStatus.NOT_FOUND);
        }

        // 2. 정상 email 인 경우
        Long id = userDto.getId();
        List<Long> followingList = followService.fromMeToOthersList(id);
        List<User> users = userService.findAllById(id);
        List<SubscribeUserDto> userDtos = new ArrayList<>();
        users.stream().forEach(user -> userDtos.add(new SubscribeUserDto(user)));

        return new ResponseEntity(userDtos, HttpStatus.OK);
    }

    /**
     * [프로필 정보 수정 진입시]
     * username, user_id => X, 프로필 사진, 프로필 소개, 알림 설정 목록 => 미구현
     */
    @GetMapping("/profile/{email}")
    public ResponseEntity getProfile(@PathVariable("email") String email) {
        // 0. jwt 로 본인인지 아닌지를 파악할 계획

        // 1. email 없는 경우
        User userDto = userService.findUserByEmail(email);
        if (userDto == null) {
            return new ResponseEntity("회원 정보가 없습니다", HttpStatus.NOT_FOUND);
        }

        // 2. 정상 email 인 경우
        ProfileDto result = new ProfileDto(userDto);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * [프로필 정보 수정] Post => Put
     * username, user_id => X, 프로필 사진, 프로필 소개, 알림 설정 목록 => 미구현
     */
    @PutMapping("/profile/{email}")
    public ResponseEntity updateProfile(@PathVariable("email") String email, @RequestBody ProfileDto dto) {
        // 0. jwt 로 본인인지 아닌지를 파악할 계획

        // 1. email 없는 경우
        User userDto = userService.findUserByEmail(email);
        if (userDto == null) {
            return new ResponseEntity("회원 정보가 없습니다", HttpStatus.NOT_FOUND);
        }

        userService.updateUserProfile(userDto.getId(), dto);
        return new ResponseEntity("업데이트 성공", HttpStatus.OK);
    }
}
