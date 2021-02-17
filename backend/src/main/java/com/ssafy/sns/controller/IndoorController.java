package com.ssafy.sns.controller;

import com.ssafy.sns.dto.newsfeed.FeedListResponseDto;
import com.ssafy.sns.dto.newsfeed.FeedResponseDto;
import com.ssafy.sns.dto.newsfeed.IndoorRequestDto;
import com.ssafy.sns.dto.newsfeed.IndoorResponseDto;
import com.ssafy.sns.jwt.JwtService;
import com.ssafy.sns.service.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/indoors")
public class IndoorController {

    public static final Logger logger = LoggerFactory.getLogger(IndoorController.class);
    private final IndoorServiceImpl indoorService;
    private final JwtService jwtService;

    @ApiOperation("꽃보다집 추천 게시물")
    @GetMapping(value = "/recommend", produces = "application/json; charset=utf8")
    public ResponseEntity<FeedListResponseDto> getRecommendList(HttpServletRequest request) {

        HttpStatus status;
        FeedListResponseDto feedListResponseDto = null;

        try {
            feedListResponseDto = indoorService.feedRecommend(jwtService.findId(request.getHeader("Authorization")));
            logger.info("getFeedMyList = 꽃보다집 추천 리스트 가져오기");
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.warn("getFeedMyList - 꽃보다집 추천 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(feedListResponseDto, status);
    }


    // 내가 쓴 게시글 불러오기
    @ApiOperation("해당 유저 작성한 꽃보다집 전체 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "targetId", value = "사용자 ID", required = true),
            @ApiImplicitParam(name = "startNum", value = "시작 페이지 번호", required = true)
    })
    @GetMapping(value = "/user/{targetId}", produces = "application/json; charset=utf8")
    public ResponseEntity<FeedListResponseDto> getFeedMyList(@PathVariable("targetId") Long targetId,
                                                             @RequestParam("startNum") int startNum,
                                                             HttpServletRequest request) {
        HttpStatus status = HttpStatus.ACCEPTED;

        FeedListResponseDto feedListResponseDto = null;
        try {
            feedListResponseDto = indoorService.findMyList(
                    jwtService.findId(request.getHeader("Authorization")), targetId, startNum);
            logger.info("getFeedMyList = 꽃보다집 내 글 리스트 가져오기 : {}", startNum);
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.warn("getFeedMyList - 꽃보다집 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(feedListResponseDto, status);
    }

    // 꽃보다집 게시글 불러오기
    @ApiOperation("꽃보다집 전체 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startNum", value = "시작 페이지 번호", required = true)
    })
    @GetMapping
    public ResponseEntity<FeedListResponseDto> getFeedList(@RequestParam("startNum") int startNum,
                                                           HttpServletRequest request) {
        HttpStatus status = HttpStatus.ACCEPTED;

        FeedListResponseDto feedListResponseDto = null;
        try {
            feedListResponseDto = indoorService.readList(
                    jwtService.findId(request.getHeader("Authorization")), startNum);
            logger.info("getFeedList = 꽃보다집 글 리스트 가져오기 : {}", startNum);
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.warn("getFeedList - 꽃보다집 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(feedListResponseDto, status);
    }

    @ApiOperation("꽃보다집 상세 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "feedId", value = "피드 번호", required = true)
    })
    @GetMapping(value = "/{feedId}", produces = "application/json; charset=utf8")
    public ResponseEntity<FeedResponseDto> getFeed(@PathVariable("feedId") Long feedId, HttpServletRequest request) {
        HttpStatus status = HttpStatus.ACCEPTED;

        IndoorResponseDto indoorResponseDto = null;
        try {
            indoorResponseDto = (IndoorResponseDto) indoorService.read(
                    jwtService.findId(request.getHeader("Authorization")), feedId);
            logger.info("getFeed = 꽃보다집 글 가져오기 : {}", indoorResponseDto);
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.warn("getFeed - 꽃보다집 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(indoorResponseDto, status);
    }

    @ApiOperation("꽃보다집 글 작성 (파일 첨부 제외)")
    @PostMapping
    public ResponseEntity<Long> postFeed(@RequestBody IndoorRequestDto indoorRequestDto, HttpServletRequest request) {
        HttpStatus status = HttpStatus.ACCEPTED;
        Long result = null;
        try {
            result = indoorService.write(jwtService.findId(request.getHeader("Authorization")), indoorRequestDto);
            logger.info("postFeed - 꽃보다집 글 작성 : {}", indoorRequestDto);
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.warn("postFeed - 꽃보다집 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(result, status);
    }

    @ApiOperation("꽃보다집 글 파일 첨부")
    @PostMapping("/{feedId}")
    public ResponseEntity<Void> postFiles(@PathVariable("feedId") Long feedId, @RequestPart(name = "file", required = false) MultipartFile file,
                                         HttpServletRequest request) throws IOException {
        HttpStatus status = HttpStatus.ACCEPTED;
        Long result = null;

        String token = request.getHeader("Authorization");
        Long userId = jwtService.findId(token);

        indoorService.uploadFiles(feedId, file);

        return new ResponseEntity<>(status);
    }

    @ApiOperation("꽃보다집 글 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "feedId", value = "피드 번호", required = true),
            @ApiImplicitParam(name = "file", value = "첨부파일")
    })
    @PutMapping(value = "/{feedId}")
    public ResponseEntity<Long> putFeed(@PathVariable("feedId") Long feedId,
                                        @RequestBody IndoorRequestDto indoorRequestDto,
                                        HttpServletRequest request) {
        HttpStatus status = HttpStatus.ACCEPTED;

        Long userId = jwtService.findId(request.getHeader("Authorization"));

        try {
            indoorService.modify(userId, feedId, indoorRequestDto);
            logger.info("putFeed - 꽃보다집 글 수정 : {}", indoorRequestDto);
            status = HttpStatus.OK;
        } catch(NoSuchElementException e) {
            logger.warn("putFeed - 꽃보다집 권한없는 사용자 : {}", userId);
            status = HttpStatus.UNAUTHORIZED;
        } catch (Exception e) {
            logger.warn("putFeed - 꽃보다집 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(status);
    }

    @ApiOperation("꽃보다집 글 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "feedId", value = "피드 번호", required = true)
    })
    @DeleteMapping(value = "{feedId}")
    public ResponseEntity<Void> deleteFeed(@PathVariable("feedId") Long feedId, HttpServletRequest request) {
        HttpStatus status = HttpStatus.ACCEPTED;

        Long userId = jwtService.findId(request.getHeader("Authorization"));

        try {
            indoorService.delete(userId, feedId);
            status = HttpStatus.OK;
            logger.info("putFeed - 꽃보다집 글 수정 : {}", feedId);
        } catch (Exception e) {
            logger.warn("deleteFeed - 꽃보다집 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(status);
    }
}
