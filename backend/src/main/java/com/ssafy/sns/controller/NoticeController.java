package com.ssafy.sns.controller;

import com.ssafy.sns.domain.newsfeed.Feed;
import com.ssafy.sns.domain.notice.Notice;
import com.ssafy.sns.domain.notice.NoticeComment;
import com.ssafy.sns.domain.notice.NoticeFeedClap;
import com.ssafy.sns.domain.notice.NoticeFollow;
import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.newsfeed.FeedResponseDto;
import com.ssafy.sns.dto.notice.NoticeResponseDto;
import com.ssafy.sns.dto.user.SimpleUserDto;
import com.ssafy.sns.service.NoticeServiceImpl;
import com.ssafy.sns.service.UserServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@RestController
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeServiceImpl noticeService;
    private final UserServiceImpl userService;

    @ApiOperation(value = "알림 목록 불러오기")
    @GetMapping("/list/{userId}")
    public ResponseEntity<List<NoticeResponseDto>> listAll(@PathVariable("userId") Long userId) {
        HttpStatus status = HttpStatus.ACCEPTED;
        User me =  userService.findUserById(userId);
        List<Notice> followList = noticeService.followList(me);
        List<Notice> feedClabList = noticeService.feedClabList(me);
        List<Notice> commentList = noticeService.commentList(me);
        List<Notice> joined1 = Stream.concat(followList.stream(), feedClabList.stream())
                .collect(Collectors.toList());
        List<Notice> joined2 = Stream.concat(joined1.stream(), commentList.stream())
                .collect(Collectors.toList());
        List<NoticeResponseDto> noticeResponseDto = new ArrayList<>();
        for (Notice notice : joined2) {
            Long fromId = null;
            SimpleUserDto simpleOther = null;
            Feed feed = null;
            if(notice instanceof NoticeFollow) {
//                fromId = ((NoticeFollow) notice).getFollow().getFromUser().getId();
                simpleOther = new SimpleUserDto(userService.findUserById(fromId));
                System.out.println("userId = " + userId);
                System.out.println("fromId = " + fromId);
                noticeResponseDto.add(new NoticeResponseDto(1, simpleOther));
            } else if(notice instanceof NoticeFeedClap) {
//                fromId = ((NoticeFeedClap)notice).getFeedClap().getUser().getId();
//                feed = ((NoticeFeedClap)notice).getFeedClap().getFeed();
                noticeResponseDto.add(new NoticeResponseDto(2, simpleOther, feed));
            } else if(notice instanceof NoticeComment) {
//                fromId = ((NoticeComment) notice).getComment().getUser().getId();
//                noticeResponseDto.add(new NoticeResponseDto(3, fromId, userId));
            }
        }
        return new ResponseEntity<>(noticeResponseDto, status);
    }

}
