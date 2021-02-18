package com.ssafy.sns.controller;

import com.ssafy.sns.dto.newsfeed.*;
import com.ssafy.sns.jwt.JwtService;
import com.ssafy.sns.service.OutdoorServiceImpl;
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
@RequestMapping("/outdoors")
public class OutdoorController {

    public static final Logger logger = LoggerFactory.getLogger(OutdoorController.class);
    private final OutdoorServiceImpl outdoorService;
    private final JwtService jwtService;

    @ApiOperation("청산별곡 추천 게시물")
    @GetMapping(value = "/recommend", produces = "application/json; charset=utf8")
    public ResponseEntity<FeedListResponseDto> getRecommendList(HttpServletRequest request) {

        HttpStatus status;
        FeedListResponseDto feedListResponseDto = null;

        try {
            feedListResponseDto = outdoorService.feedRecommend(jwtService.findId(request.getHeader("Authorization")));
            logger.info("getFeedMyList = 청산별곡 추천 리스트 가져오기");
            status = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("getFeedMyList - 청산별곡 추천 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(feedListResponseDto, status);
    }

    // 내가 쓴 게시글 불러오기
    @ApiOperation("해당 유저 작성한 청산별곡 전체 조회")
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
            feedListResponseDto = outdoorService.findMyList(
                    jwtService.findId(request.getHeader("Authorization")), targetId, startNum);
            logger.info("getFeedMyList = 청산별곡 내 글 리스트 가져오기 : {}", startNum);
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.warn("getFeedMyList - 청산별곡 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(feedListResponseDto, status);
    }

    // 꽃보다집 게시글 불러오기
    @ApiOperation("청산별곡 전체 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startNum", value = "시작 페이지 번호", required = true)
    })
    @GetMapping
    public ResponseEntity<FeedListResponseDto> getFeedList(@RequestParam("startNum") int startNum,
                                                           HttpServletRequest request) {
        HttpStatus status = HttpStatus.ACCEPTED;

        FeedListResponseDto feedListResponseDto = null;
        try {
            feedListResponseDto = outdoorService.readList(
                    jwtService.findId(request.getHeader("Authorization")), startNum);
            logger.info("getFeedList = 청산별곡 글 리스트 가져오기 : {}", startNum);
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.warn("getFeedList - 청산별곡 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(feedListResponseDto, status);
    }

    @ApiOperation("청산별곡 상세 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "feedId", value = "피드 번호", required = true)
    })
    @GetMapping(value = "/{feedId}", produces = "application/json; charset=utf8")
    public ResponseEntity<FeedResponseDto> getFeed(@PathVariable("feedId") Long feedId, HttpServletRequest request) {
        HttpStatus status = HttpStatus.ACCEPTED;

        OutdoorResponseDto outdoorResponseDto = null;
        try {
            outdoorResponseDto = (OutdoorResponseDto) outdoorService.read(
                    jwtService.findId(request.getHeader("Authorization")), feedId);
            logger.info("getFeed = 청산별곡 글 가져오기 : {}", outdoorResponseDto);
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.warn("getFeed - 청산별곡 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(outdoorResponseDto, status);
    }

    @ApiOperation("청산별곡 글 작성 (파일 첨부 제외)")
    @PostMapping
    public ResponseEntity<Long> postFeed(@RequestBody OutdoorRequestDto outdoorRequestDto, HttpServletRequest request) {
        HttpStatus status = HttpStatus.ACCEPTED;
        Long result = null;
        try {
            result = outdoorService.write(jwtService.findId(request.getHeader("Authorization")), outdoorRequestDto);
            logger.info("postFeed - 청산별곡 글 작성 : {}", outdoorRequestDto);
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.warn("postFeed - 청산별곡 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(result, status);
    }

    @ApiOperation("청산별곡 글 파일 첨부")
    @PostMapping("/{feedId}")
    public ResponseEntity<Void> postFiles(@PathVariable("feedId") Long feedId, @RequestPart(name = "file", required = false) MultipartFile file,
                                          HttpServletRequest request) throws IOException {
        HttpStatus status = HttpStatus.ACCEPTED;
        Long result = null;

        String token = request.getHeader("Authorization");
        Long userId = jwtService.findId(token);

        outdoorService.uploadFiles(feedId, file);

        return new ResponseEntity<>(status);
    }

    @ApiOperation("청산별곡 글 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "feedId", value = "피드 번호", required = true),
            @ApiImplicitParam(name = "file", value = "첨부파일")
    })
    @PutMapping(value = "/{feedId}")
    public ResponseEntity<Long> putFeed(@PathVariable("feedId") Long feedId,
                                        @RequestBody OutdoorRequestDto outdoorRequestDto,
                                        HttpServletRequest request) {
        HttpStatus status = HttpStatus.ACCEPTED;

        Long userId = jwtService.findId(request.getHeader("Authorization"));

        try {
            outdoorService.modify(userId, feedId, outdoorRequestDto);
            logger.info("putFeed - 청산별곡 글 수정 : {}", outdoorRequestDto);
            status = HttpStatus.OK;
        } catch(NoSuchElementException e) {
            logger.warn("putFeed - 청산별곡 권한없는 사용자 : {}", userId);
            status = HttpStatus.UNAUTHORIZED;
        } catch (Exception e) {
            logger.warn("putFeed - 청산별곡 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(status);
    }

    @ApiOperation("청산별곡 글 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "feedId", value = "피드 번호", required = true)
    })
    @DeleteMapping(value = "{feedId}")
    public ResponseEntity<Void> deleteFeed(@PathVariable("feedId") Long feedId, HttpServletRequest request) {
        HttpStatus status = HttpStatus.ACCEPTED;

        Long userId = jwtService.findId(request.getHeader("Authorization"));

        try {
            outdoorService.delete(userId, feedId);
            status = HttpStatus.OK;
            logger.info("putFeed - 청산별곡 글 수정 : {}", feedId);
        } catch (Exception e) {
            logger.warn("deleteFeed - 청산별곡 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(status);
    }
}
