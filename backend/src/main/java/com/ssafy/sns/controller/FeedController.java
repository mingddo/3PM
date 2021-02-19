package com.ssafy.sns.controller;

import com.ssafy.sns.dto.clap.ClapResponseDto;
import com.ssafy.sns.dto.comment.CommentRequestDto;
import com.ssafy.sns.dto.comment.CommentResponseDto;
import com.ssafy.sns.jwt.JwtService;
import com.ssafy.sns.service.CommentClapServiceImpl;
import com.ssafy.sns.service.CommentServiceImpl;
import com.ssafy.sns.service.FeedClapServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/feeds")
public class FeedController {

    public static final Logger logger = LoggerFactory.getLogger(FeedController.class);
    private final FeedClapServiceImpl feedClapService;
    private final JwtService jwtService;
    private final CommentServiceImpl commentService;
    private final CommentClapServiceImpl commentClapService;

    @ApiOperation("뉴스피드 박수 토글")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "feedId", value = "피드 번호", required = true)
    })
    @PostMapping(value = "/{feedId}/claps")
    public ResponseEntity<Void> postClap(@PathVariable("feedId") Long feedId, HttpServletRequest request) {
        HttpStatus status = HttpStatus.ACCEPTED;

        // 박수 토글
        try {
            feedClapService.changeClap(jwtService.findId(request.getHeader("Authorization")), feedId);
            logger.info("postClap - 뉴스피드 박수 토글");
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.warn("postClap - 뉴스피드 박수 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(status);
    }

    @ApiOperation("뉴스피드 박수 명단 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "feedId", value = "피드 번호", required = true)
    })
    @GetMapping(value = "/{feedId}/claps")
    public ResponseEntity<ClapResponseDto> getClap(@PathVariable("feedId") Long feedId) {
        HttpStatus status = HttpStatus.ACCEPTED;
        ClapResponseDto clapResponseDto = null;
        // 박수 불러오기
        try {
            clapResponseDto = feedClapService.clapUserList(feedId);
            logger.info("getClap - 뉴스피드 박수 조회");
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.warn("getClap - 뉴스피드 박수 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(clapResponseDto, status);
    }

    @ApiOperation("뉴스피드 댓글 작성")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "feedId", value = "피드 번호", required = true)
    })
    @PostMapping(value = "/{feedId}/comments")
    public ResponseEntity<Void> postComment(@PathVariable("feedId") Long feedId,
                                            @RequestBody CommentRequestDto commentRequestDto,
                                            HttpServletRequest request) {

        HttpStatus status = HttpStatus.ACCEPTED;

        try {
            commentService.write(commentRequestDto, jwtService.findId(request.getHeader("Authorization")), feedId);
            logger.info("postComment - 뉴스피드 댓글 작성");
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.warn("postComment - 뉴스피드 댓글 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(status);
    }

    @ApiOperation("뉴스피드 댓글 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "feedId", value = "피드 번호", required = true),
            @ApiImplicitParam(name = "commentId", value = "댓글 번호", required = true)
    })
    @PutMapping(value = "/{feedId}/comments/{commentId}")
    public ResponseEntity<Void> putComment(@PathVariable("feedId") Long feedId,
                                           @PathVariable("commentId") Long commentId,
                                           @RequestBody CommentRequestDto commentRequestDto,
                                           HttpServletRequest request) {

        HttpStatus status = HttpStatus.ACCEPTED;

        try {
            commentService.modify(commentRequestDto, jwtService.findId(request.getHeader("Authorization")), feedId, commentId);
            logger.info("putComment - 뉴스피드 댓글 수정");
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.warn("puttComment - 뉴스피드 댓글 수정 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(status);
    }

    @ApiOperation("뉴스피드 댓글 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "feedId", value = "피드 번호", required = true),
            @ApiImplicitParam(name = "commentId", value = "댓글 번호", required = true)
    })
    @DeleteMapping(value = "/{feedId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("feedId") Long feedId,
                                              @PathVariable("commentId") Long commentId,
                                              HttpServletRequest request) {

        HttpStatus status = HttpStatus.ACCEPTED;

        try {
            commentService.remove(jwtService.findId(request.getHeader("Authorization")), feedId, commentId);
            logger.info("putComment - 뉴스피드 댓글 삭제");
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.warn("puttComment - 뉴스피드 댓글 삭제 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(status);
    }

    @ApiOperation("뉴스피드 댓글 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "feedId", value = "피드 번호", required = true),
            @ApiImplicitParam(name = "startNum", value = "시작 댓글", required = true)
    })
    @GetMapping(value = "/{feedId}/comments")
    public ResponseEntity<CommentResponseDto> getComment(@PathVariable("feedId") Long feedId,
                                                         @RequestParam("startNum") int startNum,
                                                         HttpServletRequest request) {

        HttpStatus status = HttpStatus.ACCEPTED;
        CommentResponseDto comments = null;
        try {
            comments = commentService.getList(jwtService.findId(request.getHeader("Authorization")), feedId, startNum);
            logger.info("getComment - 뉴스피드 댓글 리스트");
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.warn("getComment - 뉴스피드 댓글 리스트 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(comments, status);
    }

    @ApiOperation("뉴스피드 댓글 박수 토글")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentId", value = "댓글 번호", required = true)
    })
    @PostMapping(value = "/comments/{commentId}/claps")
    public ResponseEntity<Void> postCommentClap(@PathVariable("commentId") Long commentId, HttpServletRequest request) {
        HttpStatus status = HttpStatus.ACCEPTED;

        // 박수 토글
        try {
            commentClapService.changeClap(jwtService.findId(request.getHeader("Authorization")), commentId);
            logger.info("postClap - 뉴스피드 댓글 박수 토글");
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.warn("postClap - 뉴스피드 댓글 박수 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(status);
    }

    @ApiOperation("뉴스피드 댓글 박수 명단 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentId", value = "댓글 번호", required = true)
    })
    @GetMapping(value = "/comments/{commentId}/claps")
    public ResponseEntity<ClapResponseDto> getCommentClap(@PathVariable("commentId") Long commentId) {
        HttpStatus status = HttpStatus.ACCEPTED;
        ClapResponseDto clapResponseDto = null;
        // 박수 불러오기
        try {
            clapResponseDto = commentClapService.clapUserList(commentId);
            logger.info("getClap - 뉴스피드 댓글 박수 조회");
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.warn("getClap - 뉴스피드 댓글 박수 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(clapResponseDto, status);
    }
}