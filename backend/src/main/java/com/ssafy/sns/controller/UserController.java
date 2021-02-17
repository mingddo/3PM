package com.ssafy.sns.controller;

import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.group.GroupResDto;
import com.ssafy.sns.dto.mypage.ProfileRequestDto;
import com.ssafy.sns.dto.mypage.ProfileResponseDto;
import com.ssafy.sns.dto.mypage.UserProfileDto;
import com.ssafy.sns.dto.newsfeed.FeedListResponseDto;
import com.ssafy.sns.dto.newsfeed.InsiderResDto;
import com.ssafy.sns.dto.user.*;
import com.ssafy.sns.jwt.JwtService;
import com.ssafy.sns.service.FollowServiceImpl;
import com.ssafy.sns.service.InsiderService;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MyPage 관련 Controller
 */
@RequiredArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;
    private final FollowServiceImpl followService;
    private final JwtService jwtService;
    private final InsiderService insiderService;

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
    public ResponseEntity isKakaoLogin(@RequestBody KakaoLoginRequestDto dto) {

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

    @ApiOperation("유저 전체 조회")
    @GetMapping
    public ResponseEntity findAllUsers() {
        List<UserProfileDto> allUser = userService.findAllUser();
        return new ResponseEntity(allUser, HttpStatus.OK);
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

        User user = userService.findUserById(userId);
        if (user == null) {
            return new ResponseEntity("회원 정보가 없습니다", HttpStatus.NOT_FOUND);
        }

        // 2. 회원 정보가 있는 경우 dto 에 데이터 넣고 리턴
        int fromMeToOthersCnt = followService.fromMeToOthers(userId);
        int toMeFromOthersCnt = followService.toMeFromOthers(userId);
        int groupCnt = user.getGroupMembers().size(); // 나중에 Group Entity 생기면 추가 예정


        UserProfileDto result = UserProfileDto.builder()
                .username(user.getNickname())
                .user_id(user.getId())
                .user_img(user.getImg())
                .fromMeToOthersCnt(fromMeToOthersCnt)
                .toMeFromOthersCnt(toMeFromOthersCnt)
                .groupCnt(groupCnt)
                .introduce(user.getIntroduce())
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
    @ApiOperation("사용자의 프로필 정보를 수정한다.")
    @PutMapping("/{userId}")
    public ResponseEntity updateProfile(@PathVariable("userId") Long userId,
                                        @RequestPart(value = "file", required = false) MultipartFile file,
                                        ProfileRequestDto dto,
                                        HttpServletRequest request) throws IOException {
        User user = userService.findUserById(jwtService.findId(request.getHeader("Authorization")));

        if (userId != user.getId()) {
            return new ResponseEntity("잘못된 접근입니다", HttpStatus.UNAUTHORIZED);
        }
        userService.updateUserProfile(user.getId(), dto, file);

        return new ResponseEntity("업데이트 성공", HttpStatus.OK);
    }

    @ApiOperation(value = "팔로우 버튼을 누른다")
    @GetMapping("/{userId}/follow")
    public ResponseEntity<Void> followUser(HttpServletRequest request, @PathVariable("userId") Long toUserId){
        String token = request.getHeader("Authorization");
        Long tokenId = jwtService.findId(token);
        User user = userService.findUserById(tokenId);

        followService.addFollow(user.getId(), toUserId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "팔로잉한 사람들의 리스트 가져오기")
    @GetMapping("/{userId}/following")
    public ResponseEntity<List<UserFollowDto>>getFollowingList(HttpServletRequest request, @PathVariable("userId") Long toUserId){
        String token = request.getHeader("Authorization");
        Long tokenId = jwtService.findId(token);
        User user = userService.findUserById(tokenId);
        return ResponseEntity.status(HttpStatus.OK).body(followService.getFollowingList(user.getId(), toUserId));
    }


    @ApiOperation(value = "팔로우한 사람들의 리스트가져오기")
    @GetMapping("/{userId}/follower")
    public ResponseEntity<List<UserFollowDto>>getFollowerList(HttpServletRequest request, @PathVariable("userId") Long toUserId){
        String token = request.getHeader("Authorization");
        Long tokenId = jwtService.findId(token);
        User user = userService.findUserById(tokenId);
        return ResponseEntity.status(HttpStatus.OK).body(followService.getFollowerList(user.getId(), toUserId));
    }

    @ApiOperation("유저가 가입한 그룹 정보 보여주기")
    @GetMapping("/{userId}/groups")
    public ResponseEntity<List<GroupResDto>> getGroupsByUser(@PathVariable("userId") Long userId) {
        List<GroupResDto> userGroupsDtos = userService.findUserGroups(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userGroupsDtos);
    }

    @ApiOperation("유저가 그룹 카테고리에서 작성한 피드 리스트 가져오기")
    @GetMapping("/{userId}/groups/feeds")
    public ResponseEntity<InsiderResDto> getGroupFeedsByUser(@PathVariable("userId") Long userId,
                                                             @RequestParam("startNum") int startNum,
                                                             HttpServletRequest request) {
        User viewer = userService.findUserById(jwtService.findId(request.getHeader("Authorization")));
        FeedListResponseDto feedListResponseDto =  insiderService.findAllByUser(viewer.getId(), userId, startNum);
        return new ResponseEntity(feedListResponseDto, HttpStatus.OK);
    }
}
