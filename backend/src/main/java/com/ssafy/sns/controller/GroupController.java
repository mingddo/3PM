package com.ssafy.sns.controller;

import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.group.GroupReqDto;
import com.ssafy.sns.dto.group.GroupCreateResDto;
import com.ssafy.sns.dto.group.GroupResDto;
import com.ssafy.sns.dto.newsfeed.FeedListResponseDto;
import com.ssafy.sns.dto.newsfeed.InsiderReqDto;
import com.ssafy.sns.dto.newsfeed.InsiderResDto;
import com.ssafy.sns.jwt.JwtService;
import com.ssafy.sns.service.GroupService;
import com.ssafy.sns.service.InsiderService;
import com.ssafy.sns.service.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {

    public static final Logger logger = LoggerFactory.getLogger(IndoorController.class);
    private final JwtService jwtService;
    private final UserServiceImpl userService;
    private final GroupService groupService;
    private final InsiderService insiderService;
//    private final FeedService feedService;

    @ApiOperation(value = "그룹을 생성한다", response = GroupCreateResDto.class)
    @PostMapping
    public ResponseEntity<GroupCreateResDto> makeGroup(@RequestBody GroupReqDto reqDto,
                                                       HttpServletRequest request) throws IOException {
        User user = userService.findUserById(jwtService.findId(request.getHeader("Authorization")));
        GroupCreateResDto resDto = groupService.create(user, reqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resDto);
    }

    @ApiOperation(value = "그룹 이미지를 저장한다.")
    @PostMapping("/{groupId}/image")
    public ResponseEntity<Void> saveGroupImage(@PathVariable("groupId") Long groupId,
                                               @RequestParam MultipartFile file) throws IOException {
        groupService.saveGroupImg(groupId, file);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "그룹에 가입한다")
    @PostMapping("/{groupId}/join")
    public ResponseEntity<Void> joinGroup(@PathVariable("groupId") Long groupId, HttpServletRequest request) {
        User user = userService.findUserById(jwtService.findId(request.getHeader("Authorization")));
        groupService.joinGroup(user, groupId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "그룹 전체 조회", response = GroupResDto.class)
    @GetMapping
    public ResponseEntity<List<GroupResDto>> getGroups() {
        List<GroupResDto> groupResDtos = groupService.getGroupList();
        return ResponseEntity.status(HttpStatus.OK).body(groupResDtos);
    }

    @ApiOperation(value = "그룹 상세 조회", response = GroupResDto.class)
    @GetMapping("/{groupId}")
    public ResponseEntity<GroupResDto> getGroupDetail(@PathVariable("groupId") Long groupId) {
        GroupResDto groupResDto = groupService.getGroupDetail(groupId);
        return ResponseEntity.status(HttpStatus.OK).body(groupResDto);
    }

    @ApiOperation(value = "그룹 정보를 수정한다")
    @PutMapping("/{groupId}")
    public ResponseEntity<Void> updateGroup(@PathVariable("groupId") Long groupId,
                                            @RequestBody GroupReqDto reqDto,
                                            HttpServletRequest request) throws Exception {
        User user = userService.findUserById(jwtService.findId(request.getHeader("Authorization")));
        groupService.updateGroup(user, groupId, reqDto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "그룹에서 탈퇴한다")
    @DeleteMapping("/{groupId}/members")
    public ResponseEntity<Void> exitGroup(@PathVariable("groupId") Long groupId, HttpServletRequest request) throws Exception {
        User user = userService.findUserById(jwtService.findId(request.getHeader("Authorization")));
        groupService.exitGroup(groupId, user);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "그룹을 삭제한다")
    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> deleteGroup(@PathVariable("groupId") Long groupId,
                                            HttpServletRequest request) throws Exception {
        User user = userService.findUserById(jwtService.findId(request.getHeader("Authorization")));
        groupService.deleteGroup(groupId, user);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "그룹 카테고리 내 모든 피드 조회")
    @GetMapping("/feeds")
    public ResponseEntity<List<InsiderResDto>> getGroupFeeds(@RequestParam("startNum") int startNum,
                                                             HttpServletRequest request) {
        User user = userService.findUserById(jwtService.findId(request.getHeader("Authorization")));

        HttpStatus status = HttpStatus.ACCEPTED;

        FeedListResponseDto feedListResponseDto = null;
        try {
            feedListResponseDto = insiderService.findAll(user.getId(), startNum);
            logger.info("getGroupFeedsByUser = 핵인싸 그룹 해당 유저 게시글 리스트 가져오기 : {}", startNum);
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.warn("getGroupFeedsByUser - 핵인싸 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity(feedListResponseDto, status);
    }


    @ApiOperation(value = "해당 유저가 작성한 그룹 게시글 조회")
    @GetMapping("/{groupId}/feeds/users/{userId}")
    public ResponseEntity<List<InsiderResDto>> getGroupFeedsByUser(@PathVariable("groupId") Long groupId,
                                                                   @PathVariable("userId") Long userId,
                                                                   @RequestParam("startNum") int startNum,
                                                                   HttpServletRequest request) {
        User user = userService.findUserById(jwtService.findId(request.getHeader("Authorization")));

        HttpStatus status = HttpStatus.ACCEPTED;

        FeedListResponseDto feedListResponseDto = null;
        try {
            feedListResponseDto = insiderService.findAllByUserIdAndGroupId(user.getId(), userId, groupId, startNum);
            logger.info("getGroupFeedsByUser = 핵인싸 그룹 해당 유저 게시글 리스트 가져오기 : {}", startNum);
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.warn("getGroupFeedsByUser - 핵인싸 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity(feedListResponseDto, status);
    }

    @ApiOperation(value = "그룹 추천 게시글 5개")
    @GetMapping("/feeds/recommend")
    public ResponseEntity<List<InsiderResDto>> getGroupRecommend(HttpServletRequest request) {
        User user = userService.findUserById(jwtService.findId(request.getHeader("Authorization")));

        HttpStatus status = HttpStatus.ACCEPTED;

        List<InsiderResDto> insiderResDtos = null;
        try {
            insiderResDtos = insiderService.getGroupRecommend(user.getId());
            logger.info("getGroupFeedsByUser = 핵인싸 그룹 추천 피드 가져오기 : {}");
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.warn("getGroupFeedsByUser - 핵인싸 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity(insiderResDtos, status);
    }


    @ApiOperation(value = "그룹 게시글 조회", response = InsiderResDto.class)
    @GetMapping("/{groupId}/feeds")
    public ResponseEntity<List<InsiderResDto>> getGroupFeeds(@PathVariable("groupId") Long groupId,
                                                             @RequestParam("startNum") int startNum,
                                                             HttpServletRequest request) {
        User user = userService.findUserById(jwtService.findId(request.getHeader("Authorization")));

        HttpStatus status = HttpStatus.ACCEPTED;

        FeedListResponseDto feedListResponseDto = null;
        try {
            feedListResponseDto = insiderService.findAllByGroupId(user.getId(), groupId, startNum);
            logger.info("getGroupFeeds = 핵인싸 그룹 게시글 리스트 가져오기 : {}", startNum);
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.warn("getGroupFeeds - 핵인싸 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity(feedListResponseDto, status);
    }

    @ApiOperation(value = "그룹 중 피드 디테일 조회", response = InsiderResDto.class)
    @GetMapping("/{groupId}/feeds/{feedId}")
    public ResponseEntity<InsiderResDto> getGroupFeedOne(@PathVariable("groupId") Long groupId,
                                                           @PathVariable("feedId") Long feedId,
                                                           HttpServletRequest request) {
        User user = userService.findUserById(jwtService.findId(request.getHeader("Authorization")));

        HttpStatus status = HttpStatus.ACCEPTED;

        InsiderResDto insiderResDto = null;
        try {
            insiderResDto = insiderService.findByGroupIdAndFeedId(user.getId(), feedId, groupId);
            logger.info("getGroupFeeds = 핵인싸 그룹 게시글 리스트 가져오기 : {}", insiderResDto);
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.warn("getGroupFeeds - 핵인싸 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity(insiderResDto, status);
    }

    @ApiOperation(value = "그룹 게시글 작성 (파일 첨부 제외)")
    @PostMapping("/{groupId}/feeds")
    public ResponseEntity<Long> saveGroupFeeds(@PathVariable("groupId") Long groupId,
                                               @RequestBody InsiderReqDto insiderReqDto,
                                               HttpServletRequest request) {
        User user = userService.findUserById(jwtService.findId(request.getHeader("Authorization")));
        Long result = insiderService.save(user.getId(), groupId, insiderReqDto);

        return new ResponseEntity(result, HttpStatus.OK);
    }

    @ApiOperation(value = "그룹 게시글 파일 첨부")
    @PostMapping("/{groupId}/feeds/{feedId}")
    public ResponseEntity<Void> uploadFile(@PathVariable("groupId") Long groupId,
                                           @PathVariable("feedId") Long feedId,
                                           @RequestPart(name = "file", required = false) MultipartFile file,
                                          HttpServletRequest request) throws IOException {
        HttpStatus status = HttpStatus.ACCEPTED;
        Long result = null;

        String token = request.getHeader("Authorization");
        Long userId = jwtService.findId(token);

        insiderService.uploadFiles(feedId, file);

        return new ResponseEntity<>(status);
    }

    @ApiOperation(value = "그룹 게시글 수정")
    @PutMapping("/{groupId}/feeds/{feedId}")
    public ResponseEntity<Void> updateGroupFeeds(@PathVariable("groupId") Long groupId,
                                                 @PathVariable("feedId") Long feedId,
                                                 @RequestBody InsiderReqDto insiderReqDto,
                                                 HttpServletRequest request) throws IOException {
        User user = userService.findUserById(jwtService.findId(request.getHeader("Authorization")));
        insiderService.updateFeed(user.getId(), groupId, feedId, insiderReqDto);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "그룹 게시글 삭제")
    @DeleteMapping("/{groupId}/feeds/{feedId}")
    public ResponseEntity<Void> deleteGroupFeed(@PathVariable("groupId") Long groupId,
                                                @PathVariable("feedId") Long feedId,
                                                HttpServletRequest request) throws IOException {
        User user = userService.findUserById(jwtService.findId(request.getHeader("Authorization")));
        insiderService.deleteFeed(user.getId(), groupId, feedId);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "해당 그룹 게시글이 내가 가입한 그룹인지 여부 리턴")
    @GetMapping("/feeds/{feedId}")
    public ResponseEntity<Boolean> isMyGroup(@PathVariable("feedId") Long feedId, HttpServletRequest request) {
        User user = userService.findUserById(jwtService.findId(request.getHeader("Authorization")));
        boolean isGroup = insiderService.isMyGroup(user, feedId);
        return new ResponseEntity<>(isGroup, HttpStatus.OK);
    }

}
