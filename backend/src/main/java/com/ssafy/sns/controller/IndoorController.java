package com.ssafy.sns.controller;

import com.ssafy.sns.dto.newsfeed.FeedListResponseDto;
import com.ssafy.sns.dto.newsfeed.FeedResponseDto;
import com.ssafy.sns.dto.newsfeed.IndoorRequestDto;
import com.ssafy.sns.dto.newsfeed.IndoorResponseDto;
import com.ssafy.sns.jwt.JwtService;
import com.ssafy.sns.service.IndoorServiceImpl;
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
import java.util.List;

@RequiredArgsConstructor
@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api/indoors")
public class IndoorController {

    public static final Logger logger = LoggerFactory.getLogger(IndoorController.class);
    private final IndoorServiceImpl indoorService;
    private final JwtService jwtService;


    // 내가 쓴 게시글 불러오기
    @ApiOperation("해당 유저 작성한 꽃보다집 전체 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "사용자 ID", required = true),
            @ApiImplicitParam(name = "startNum", value = "시작 페이지 번호", required = true)
    })
    @GetMapping(value = "/user/{userId}", produces = "application/json; charset=utf8")
    public ResponseEntity<FeedListResponseDto> getFeedMyList(@PathVariable("userId") Long userId,
                                                             @RequestParam("startNum") int startNum) {
        HttpStatus status = HttpStatus.ACCEPTED;

        FeedListResponseDto feedListResponseDto = null;
        try {
            feedListResponseDto = indoorService.readMyList(userId, startNum);
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
    public ResponseEntity<FeedListResponseDto> getFeedList(@RequestParam("startNum") int startNum) {
        HttpStatus status = HttpStatus.ACCEPTED;

        FeedListResponseDto feedListResponseDto = null;
        try {
            feedListResponseDto = indoorService.readList(startNum);
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
    public ResponseEntity<FeedResponseDto> getFeed(@PathVariable("feedId") Long feedId) {
        HttpStatus status = HttpStatus.ACCEPTED;

        IndoorResponseDto indoorResponseDto = null;
        try {
            indoorResponseDto = (IndoorResponseDto) indoorService.read(feedId);
            logger.info("getFeed = 꽃보다집 글 가져오기 : {}", indoorResponseDto);
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.warn("getFeed - 꽃보다집 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(indoorResponseDto, status);
    }

    @ApiOperation("꽃보다집 글 작성")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "첨부파일")
    })
    @PostMapping
    public ResponseEntity<Long> postFeed(IndoorRequestDto indoorRequestDto,
                                         @RequestParam(name = "files", required = false) List<MultipartFile> files,
                                         HttpServletRequest request) {
        HttpStatus status = HttpStatus.ACCEPTED;
        Long result = null;

        String token = request.getHeader("Authorization");
        Long userId = jwtService.findId(token);

        try {
            result = indoorService.write(userId, indoorRequestDto, files);
            logger.info("postFeed - 꽃보다집 글 작성 : {}", indoorRequestDto);
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.warn("postFeed - 꽃보다집 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(result, status);
    }

    @ApiOperation("꽃보다집 글 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "feedId", value = "피드 번호", required = true),
            @ApiImplicitParam(name = "file", value = "첨부파일")
    })
    @PutMapping(value = "/{feedId}")
    public ResponseEntity<Long> putFeed(@PathVariable("feedId") Long feedId, IndoorRequestDto indoorRequestDto,
                                        @RequestPart(name = "files", required = false) List<MultipartFile> files, HttpServletRequest request) {
        HttpStatus status = HttpStatus.ACCEPTED;
        Long result = null;

        String token = request.getHeader("Authorization");
        Long userId = jwtService.findId(token);

        try {
            result = indoorService.modify(userId, feedId, indoorRequestDto, files);
            if (result == -1L) {
                logger.warn("putFeed - 꽃보다집 권한없는 사용자 : {}", userId);
                status = HttpStatus.UNAUTHORIZED;
            } else {
                logger.info("putFeed - 꽃보다집 글 수정 : {}", indoorRequestDto);
                status = HttpStatus.OK;
            }
        } catch (Exception e) {
            logger.warn("putFeed - 꽃보다집 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(result, status);
    }

    @ApiOperation("꽃보다집 글 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "feedId", value = "피드 번호", required = true)
    })
    @DeleteMapping(value = "{feedId}")
    public ResponseEntity<String> deleteFeed(@PathVariable("feedId") Long feedId, HttpServletRequest request) {
        HttpStatus status = HttpStatus.ACCEPTED;

        String token = request.getHeader("Authorization");
        Long userId = jwtService.findId(token);

        try {
            if (indoorService.delete(userId, feedId)) {
                logger.info("deleteFeed - 꽃보다집 글 삭제 : {}", feedId);
                status = HttpStatus.OK;
            } else {
                logger.warn("putFeed - 꽃보다집 권한없는 사용자 : {}", userId);
                status = HttpStatus.UNAUTHORIZED;
            }
        } catch (Exception e) {
            logger.warn("deleteFeed - 꽃보다집 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(status);
    }

//    @PostMapping(value = "/{post}/likes")
//    public ResponseEntity<String> postClap(@PathVariable("uno") Long uid, @PathVariable("fno") Long fid) {
//        HttpStatus status = HttpStatus.ACCEPTED;
//
//        try {
//            logger.info("postClap - 꽃보다집 박수 추가 : {} {}", uid, fid);
//        } catch (Exception e) {
//            logger.warn("postClap - 꽃보다집 에러 : {}", e.getMessage());
//        }
//        return new ResponseEntity<>(status);
//    }
}
