package com.ssafy.sns.controller;

import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.group.GroupReqDto;
import com.ssafy.sns.dto.group.GroupCreateResDto;
import com.ssafy.sns.dto.group.GroupResDto;
import com.ssafy.sns.dto.newsfeed.InsiderResDto;
import com.ssafy.sns.jwt.JwtService;
import com.ssafy.sns.service.FeedService;
import com.ssafy.sns.service.GroupService;
import com.ssafy.sns.service.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
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

    private final JwtService jwtService;
    private final UserServiceImpl userService;
    private final GroupService groupService;
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
    public ResponseEntity<Void> exitGroup(@PathVariable("groupId") Long groupId, HttpServletRequest request) {
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

    @ApiOperation(value = "그룹 게시글 조회", response = InsiderResDto.class)
    @GetMapping("/{groupId}/feeds")
    public ResponseEntity<List<InsiderResDto>> getGroupFeeds(@PathVariable("groupId") Long groupId) {
        return null;
    }

    @ApiOperation(value = "그룹 게시글 작성")
    @PostMapping("/{groupId}/feeds")
    public ResponseEntity<Void> saveGroupFeeds(@PathVariable("groupId") Long groupId,
                                               @RequestBody InsiderResDto resDto,
                                               HttpServletRequest request) {
        User user = userService.findUserById(jwtService.findId(request.getHeader("Authorization")));

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "그룹 게시글 수정")
    @PostMapping("/{groupId}/feeds/{feedId}")
    public ResponseEntity<Void> updateGroupFeeds(@PathVariable("groupId") Long groupId,
                                                 @PathVariable("feedId") Long feedId,
                                                 @RequestBody InsiderResDto resDto,
                                                 HttpServletRequest request) {
        User user = userService.findUserById(jwtService.findId(request.getHeader("Authorization")));

        return ResponseEntity.ok().build();
    }
}
