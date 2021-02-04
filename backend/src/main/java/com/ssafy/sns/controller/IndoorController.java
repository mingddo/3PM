package com.ssafy.sns.controller;

import com.ssafy.sns.dto.newsfeed.FeedListResponseDto;
import com.ssafy.sns.dto.newsfeed.FeedResponseDto;
import com.ssafy.sns.dto.newsfeed.IndoorRequestDto;
import com.ssafy.sns.dto.newsfeed.IndoorResponseDto;
import com.ssafy.sns.service.IndoorServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/indoor")
public class IndoorController {

    public static final Logger logger = LoggerFactory.getLogger(IndoorController.class);
    private final IndoorServiceImpl indoorService;

    @Autowired
    public IndoorController(IndoorServiceImpl indoorService) {
        this.indoorService = indoorService;
    }

    // 내가 쓴 게시글 불러오기
    @GetMapping(value = "/list/{id}/{no}", produces = "application/json; charset=utf8")
    public ResponseEntity<FeedListResponseDto> getFeedMyList(@PathVariable("id") Long id, @PathVariable("no") int num) {
        HttpStatus status = HttpStatus.ACCEPTED;

        FeedListResponseDto feedListResponseDto = null;
        try {
            feedListResponseDto = indoorService.readMyList(id, num);
            logger.info("getFeedMyList = 꽃보다집 내 글 리스트 가져오기 : {}", num);
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.warn("getFeedMyList - 꽃보다집 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(feedListResponseDto, status);
    }

    // 꽃보다집 게시글 불러오기
    @GetMapping(value = "/list/{no}", produces = "application/json; charset=utf8")
    public ResponseEntity<FeedListResponseDto> getFeedList(@PathVariable("no") int num) {
        HttpStatus status = HttpStatus.ACCEPTED;

        FeedListResponseDto feedListResponseDto = null;
        try {
            feedListResponseDto = indoorService.readList(num);
            logger.info("getFeedList = 꽃보다집 글 리스트 가져오기 : {}", num);
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.warn("getFeedList - 꽃보다집 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(feedListResponseDto, status);
    }

    @GetMapping(value = "{no}", produces = "application/json; charset=utf8")
    public ResponseEntity<FeedResponseDto> getFeed(@PathVariable("no") Long id) {
        HttpStatus status = HttpStatus.ACCEPTED;

        IndoorResponseDto indoorResponseDto = null;
        try {
            indoorResponseDto = (IndoorResponseDto) indoorService.read(id);
            logger.info("getFeed = 꽃보다집 글 가져오기 : {}", indoorResponseDto);
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.warn("getFeed - 꽃보다집 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(indoorResponseDto, status);
    }

    @PostMapping(value = "")
    public ResponseEntity<Long> postFeed(IndoorRequestDto indoorRequestDto, @RequestParam(name = "file", required = false) MultipartFile file) {
        HttpStatus status = HttpStatus.ACCEPTED;
        Long result = null;



        try {
            result = indoorService.write(indoorRequestDto, file);
            logger.info("postFeed - 꽃보다집 글 작성 : {}", indoorRequestDto);
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.warn("postFeed - 꽃보다집 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(result, status);
    }

    @PutMapping(value = "{no}")
    public ResponseEntity<Long> putFeed(@PathVariable("no") Long id,
                                           IndoorRequestDto indoorRequestDto, @RequestPart(name = "file", required = false) MultipartFile file) {
        HttpStatus status = HttpStatus.ACCEPTED;
        Long result = null;

        try {
            result = indoorService.modify(id, indoorRequestDto, file);
            logger.info("putFeed - 꽃보다집 글 수정 : {}", indoorRequestDto);
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.warn("putFeed - 꽃보다집 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(result, status);
    }

    @DeleteMapping(value = "{no}")
    public ResponseEntity<String> deleteFeed(@PathVariable("no") Long id) {
        HttpStatus status = HttpStatus.ACCEPTED;

        try {
            indoorService.delete(id);
            logger.info("deleteFeed - 꽃보다집 글 삭제 : {}", id);
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.warn("deleteFeed - 꽃보다집 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(status);
    }

    @PostMapping(value = "/clap/{uno}/{fno}")
    public ResponseEntity<String> postClap(@PathVariable("uno") Long uid, @PathVariable("fno") Long fid) {
        HttpStatus status = HttpStatus.ACCEPTED;

        try {
            logger.info("postClap - 꽃보다집 박수 추가 : {} {}", uid, fid);
        } catch (Exception e) {
            logger.warn("postClap - 꽃보다집 에러 : {}", e.getMessage());
        }
        return new ResponseEntity<>(status);
    }

}
