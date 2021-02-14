package com.ssafy.sns.controller;

import com.ssafy.sns.domain.clap.FeedClap;
import com.ssafy.sns.domain.comment.Comment;
import com.ssafy.sns.domain.follow.Follow;
import com.ssafy.sns.domain.notice.Notice;
import com.ssafy.sns.domain.notice.NoticeComment;
import com.ssafy.sns.domain.notice.NoticeFeedClap;
import com.ssafy.sns.domain.notice.NoticeFollow;
import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.notice.NoticeResponseDto;
import com.ssafy.sns.dto.user.SimpleUserDto;
import com.ssafy.sns.service.NoticeServiceImpl;
import com.ssafy.sns.service.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;
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
        joined2.sort(new Comparator<Notice>() {
            @Override
            public int compare(Notice o1, Notice o2) {
                return o2.getCreatedDate().compareTo(o1.getCreatedDate());
            }
        });
        List<NoticeResponseDto> noticeResponseDto = new ArrayList<>();
        for (Notice notice : joined2) {
            System.out.println("notice.getCreatedDate() = " + notice.getCreatedDate());
            Long fromId = null;
            SimpleUserDto simpleOther = null;
            Long feedId = null;
            String category = null;
            String commentContent = null;
            StringTokenizer st = null;
            if(notice instanceof NoticeFollow) {
                Follow follow = noticeService.findFollow(((NoticeFollow) notice).getFollow_id());
                fromId = follow.getFromUser().getId();
                simpleOther = new SimpleUserDto(userService.findUserById(fromId));
                System.out.println("userId = " + userId);
                System.out.println("fromId = " + fromId);
                noticeResponseDto.add(new NoticeResponseDto(1, simpleOther));
            } else if(notice instanceof NoticeFeedClap) {
                FeedClap feedClap = noticeService.findFeedClap(((NoticeFeedClap) notice).getFeed_clab_id());
                fromId = feedClap.getUser().getId();
                simpleOther = new SimpleUserDto(userService.findUserById(fromId));
                feedId = feedClap.getFeed().getId();
                st = new StringTokenizer(noticeService.findCategory(feedId).toString(),".");
                while (st.hasMoreTokens()) {
                    category = st.nextToken();
                }
                noticeResponseDto.add(new NoticeResponseDto(2, simpleOther, feedId, category));
            } else if(notice instanceof NoticeComment) {
                Comment comment = noticeService.findComment(((NoticeComment) notice).getComment_id());
                fromId = comment.getUser().getId();
                simpleOther = new SimpleUserDto(userService.findUserById(fromId));
                feedId = comment.getFeed().getId();
                commentContent = comment.getContent();
                st = new StringTokenizer(noticeService.findCategory(feedId).toString(),".");
                while (st.hasMoreTokens()) {
                    category = st.nextToken();
                }
                noticeResponseDto.add(new NoticeResponseDto(3, simpleOther, feedId, category, commentContent));
            }
        }
        return new ResponseEntity<>(noticeResponseDto, status);
    }

}
