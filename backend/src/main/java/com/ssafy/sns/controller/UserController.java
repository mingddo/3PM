package com.ssafy.sns.controller;

import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.mypage.ProfileResponseDto;
import com.ssafy.sns.dto.mypage.SubscribeUserDto;
import com.ssafy.sns.dto.mypage.UserProfileDto;
import com.ssafy.sns.dto.user.DuplRequestDto;
import com.ssafy.sns.dto.user.JwtResponseDto;
import com.ssafy.sns.dto.user.KakaoRequestDto;
import com.ssafy.sns.jwt.JwtService;
import com.ssafy.sns.service.FollowServiceImpl;
import com.ssafy.sns.service.UserServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MyPage 관련 Controller
 */
@RequiredArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserServiceImpl userService;

    private final FollowServiceImpl followService;

    private final JwtService jwtService;

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @ApiOperation("AccessToken 재발급")
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

    @ApiOperation("로그인 여부 확인")
    @PostMapping("/login")
    public ResponseEntity isKakaoLogin(@RequestBody KakaoRequestDto dto) {

        // 해당 유저가 존재하는지 확인
        User user = userService.findUserByKakaoId(dto.getKakaoId());

        // 존재하지 않는 경우 회원 가입
        if (user == null) {
            return new ResponseEntity(false, HttpStatus.ACCEPTED);
        }

        JwtResponseDto jwtResponseDto = jwtService.login(user.getId());
        HttpStatus status = HttpStatus.ACCEPTED;

        return new ResponseEntity<>(jwtResponseDto, status);
    }

    @ApiOperation("회원 가입")
    @PostMapping("/join")
    public ResponseEntity kakaoLogin(@RequestBody KakaoRequestDto dto) {

        User user = userService.joinMember(dto);

        JwtResponseDto jwtResponseDto = jwtService.login(user.getId());
        HttpStatus status = HttpStatus.ACCEPTED;

        return new ResponseEntity<>(jwtResponseDto, status);
    }

    @ApiOperation("닉네임 중복 확인")
    @PostMapping("/check")
    public ResponseEntity dupl(@RequestBody DuplRequestDto dto) {

        boolean isDuplicated = userService.isDuplicate(dto.getUsername());

        return new ResponseEntity(isDuplicated, HttpStatus.OK);
    }


    /**
     * [마이페이지 유저 정보] [프로필 정보]
     * 모든 사용자의 프로필에 들어갔을 때 처음 받는 정보
     * username, user_id, 내가 구독하는 수, 나를 구독하는 수, 가입된 그룹 수, 프로필 소개
     */
    @ApiOperation("회원 프로필 정보 제공")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "회원 번호", required = true)
    })
    @GetMapping("/{userId}")
    public ResponseEntity myPageMain(@PathVariable("userId") Long userId) {

        User userDto = userService.findUserById(userId);
        if (userDto == null) {
            return new ResponseEntity("회원 정보가 없습니다", HttpStatus.NOT_FOUND);
        }

        // 2. 회원 정보가 있는 경우 dto 에 데이터 넣고 리턴
        int fromMeToOthersCnt = followService.fromMeToOthers(userId);
        int toMeFromOthersCnt = followService.toMeFromOthers(userId);
        int groupCnt = 0; // 나중에 Group Entity 생기면 추가 예정

        UserProfileDto result = UserProfileDto.builder()
                .username(userDto.getNickname())
                .user_id(userDto.getId())
                .user_img(userDto.getImg())
                .fromMeToOthersCnt(fromMeToOthersCnt)
                .toMeFromOthersCnt(toMeFromOthersCnt)
                .groupCnt(groupCnt)
                .introduce(userDto.getIntroduce())
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
    @ApiOperation("최근활동 정보 제공")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "회원 번호", required = true)
    })
    @GetMapping("/{userId}/history")
    public ResponseEntity myPageHistory(@PathVariable("userId") Long userId) {



        return null;
    }

    /**
     * [구독 탭 선택]
     * 내가 소식 받아보는 사람 목록 ( 구독한 사람 )
     * user profile, username, user_id
     */
    @ApiOperation("내가 구독한 사람 목록")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "회원 번호", required = true)
    })
    @GetMapping("/{userId}/following")
    public ResponseEntity subscribeList(@PathVariable("userId") Long userId) {
        // 0. jwt 로 본인인지 아닌지를 파악할 계획

        // 1. email 없는 경우
        User userDto = userService.findUserById(userId);
        if (userDto == null) {
            return new ResponseEntity("회원 정보가 없습니다", HttpStatus.NOT_FOUND);
        }

        // 2. 정상 email 인 경우
//        List<Long> followingList = followService.fromMeToOthersList(id);
        List<User> users = userService.findAllById(userId);
        List<SubscribeUserDto> userDtos = new ArrayList<>();
        users.stream().forEach(user -> userDtos.add(new SubscribeUserDto(user)));

        return new ResponseEntity(userDtos, HttpStatus.OK);
    }

    @ApiOperation("내 프로필 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "회원 번호", required = true),
    })
    @PutMapping("{userId}")
    public ResponseEntity updateUser(@PathVariable("userId") Long userId, @RequestBody KakaoRequestDto dto, HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        Long tokenId = jwtService.findId(token);
        User user = userService.findUserById(tokenId);

        if (user.getId() != userId) {
            return new ResponseEntity("사용자 접근 에러", HttpStatus.UNAUTHORIZED);
        }

        user.setNickname(dto.getUsername());
        return new ResponseEntity("성공", HttpStatus.OK);
    }

    @ApiOperation("내 프로필 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "회원 번호", required = true)
    })
    @DeleteMapping("{userId}")
    public ResponseEntity deleteUser(@PathVariable("userId") Long userId, HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        Long tokenId = jwtService.findId(token);
        User user = userService.findUserById(tokenId);

        if (user.getId() != userId) {
            return new ResponseEntity("사용자 접근 에러", HttpStatus.UNAUTHORIZED);
        }

        userService.deleteUser(userId);

        return new ResponseEntity("탈퇴 성공", HttpStatus.OK);
    }

    /**
     * [프로필 정보 수정 진입시]
     * username, user_id => X, 프로필 사진, 프로필 소개, 알림 설정 목록 => 미구현
     */
    @ApiOperation("프로필 상세 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "회원 번호", required = true)
    })
    @GetMapping("/{userId}/detail")
    public ResponseEntity getProfile(@PathVariable("userId") Long userId) {
        // 0. jwt 로 본인인지 아닌지를 파악할 계획

        User userDto = userService.findUserById(userId);
        if (userDto == null) {
            return new ResponseEntity("회원 정보가 없습니다", HttpStatus.NOT_FOUND);
        }

        // 2. 정상 email 인 경우
        ProfileResponseDto result = new ProfileResponseDto(userDto);

        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * [프로필 정보 수정] Post => Put
     * username, user_id => X, 프로필 사진, 프로필 소개, 알림 설정 목록 => 미구현
     */
//    @ApiOperation("사용자의 프로필 정보를 수정한다.")
//    @PostMapping("/{id}")
//    public ResponseEntity updateProfile(@PathVariable("id") Long id, ProfileRequestDto dto) throws IOException {
//        // 0. jwt 로 본인인지 아닌지를 파악할 계획
//
//        User userDto = userService.findUserById(id);
//        if (userDto == null) {
//            return new ResponseEntity("회원 정보가 없습니다", HttpStatus.NOT_FOUND);
//        }
//
//        userService.updateUserProfile(userDto.getId(), dto);
//
//        return new ResponseEntity("업데이트 성공", HttpStatus.OK);
//    }
//
//    @ApiOperation("사용자의 프로필 정보를 수정한다.")
//    @PostMapping("/{id}")
//    public ResponseEntity updateProfile(@PathVariable("id") Long id, @RequestPart("file") MultipartFile file, ProfileRequestDto dto) throws IOException {
//        // 0. jwt 로 본인인지 아닌지를 파악할 계획
//
//        User userDto = userService.findUserById(id);
//        if (userDto == null) {
//            return new ResponseEntity("회원 정보가 없습니다", HttpStatus.NOT_FOUND);
//        }
//
//        userService.updateUserProfile(userDto.getId(), dto, file);
//
//        return new ResponseEntity("업데이트 성공", HttpStatus.OK);
//    }
//
//    @ApiOperation(value = "팔로우 버튼을 누른다")
//    @GetMapping("/{userId}/follow")
//    public ResponseEntity<Void> followUser(HttpServletRequest request, @PathVariable("userId") Long toUserId){
//        Long fromUserId = userService.checkUserByJwt(request).getId();
//        followService.addFollow(fromUserId, toUserId);
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }
//
//    @ApiOperation(value = "팔로잉한 사람들의 리스트 가져오기")
//    @GetMapping("/{userId}/following")
//    public ResponseEntity<List<UserByFollowDto>>getFollowingList(HttpServletRequest request, @PathVariable("userId") Long toUserId){
//        Long fromUserId = userService.checkUserByJwt(request).getId();
//        return ResponseEntity.status(HttpStatus.OK).body(followService.getFollowingList(fromUserId, toUserId));
//    }
//
//
//    @ApiOperation(value = "팔로우한 사람들의 리스트가져오기")
//    @GetMapping("/{userId}/follower")
//    public ResponseEntity<List<UserByFollowDto>>getFollowerList(HttpServletRequest request, @PathVariable("userId") Long toUserId){
//        Long fromUserId =  userService.checkUserByJwt(request).getId();
//        return ResponseEntity.status(HttpStatus.OK).body(followService.getFollowerList(fromUserId, toUserId));
//    }
}
