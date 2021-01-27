package com.ssafy.sns.controller;

import com.ssafy.sns.domain.newsfeed.Indoor;
import com.ssafy.sns.dto.mypage.UserProfileDto;
import com.ssafy.sns.dto.user.UserDto;
import com.ssafy.sns.service.FollowService;
import com.ssafy.sns.service.IndoorService;
import com.ssafy.sns.service.MyPageService;
import com.ssafy.sns.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

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
     * [마이페이지 유저 정보]
     * 모든 사용자의 프로필에 들어갔을 때 처음 받는 정보
     *
     */
    @GetMapping("/{email}")
    public ResponseEntity myPageMain(@PathVariable("email") String email) {

        // 1. email 로 회원을 찾아보고 없으면 찾는 리소스가 없다고 응답 404 Not Found
        UserDto userDto = userService.findUserByEmail(email);
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
     * 알림 쏜 주체의 프로필 사진
     * 게시물 알림
     * 팔로우 알림
     * 본인만 접근가능 => 접근 제한
     */
    @GetMapping("/alert/{email}")
    public ResponseEntity myPageAlert(@PathVariable("email") String email) {



        return null;
    }
}
