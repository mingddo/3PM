package com.ssafy.sns.controller;

import com.ssafy.sns.dto.newsfeed.IndoorListResponseDto;
import com.ssafy.sns.dto.newsfeed.IndoorRequestDto;
import com.ssafy.sns.dto.newsfeed.IndoorResponseDto;
import com.ssafy.sns.service.IndoorServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/feed")
public class IndoorController {

    public static final Logger logger = LoggerFactory.getLogger(IndoorController.class);
    private final IndoorServiceImpl indoorService;

    @Autowired
    public IndoorController(IndoorServiceImpl indoorService) {
        this.indoorService = indoorService;
    }

    @GetMapping(value = "/list/{no}", produces = "application/json; charset=utf8")
    public ResponseEntity<IndoorListResponseDto> getFeedList(@PathVariable("no") int num) {
        HttpStatus status = HttpStatus.ACCEPTED;
        IndoorListResponseDto indoorListResponseDto = null;
        try {
            indoorListResponseDto = indoorService.readList(num);
            logger.info("getFeedList = 뉴스 피드 글 리스트 가져오기 : {}", num);
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.warn("getFeedList - 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(indoorListResponseDto, status);
    }

    @GetMapping(value = "{no}", produces = "application/json; charset=utf8")
    public ResponseEntity<IndoorResponseDto> getFeed(@PathVariable("no") Long id) {
        HttpStatus status = HttpStatus.ACCEPTED;
        IndoorResponseDto indoorResponseDto = null;
        try {
            indoorResponseDto = indoorService.read(id);
            logger.info("getFeed = 뉴스 피드 글 가져오기 : {}", indoorResponseDto);
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.warn("getFeed - 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(indoorResponseDto, status);
    }

    @PostMapping(value = "")
    public ResponseEntity<Long> postFeed(@RequestBody IndoorRequestDto indoorRequestDto) {
        HttpStatus status = HttpStatus.ACCEPTED;
        Long result = null;

        try {
            result = indoorService.write(indoorRequestDto);
            logger.info("postFeed - 뉴스 피드 글 작성 : {}", indoorRequestDto);
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.warn("postFeed - 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(result, status);
    }

    @PutMapping(value = "{no}")
    public ResponseEntity<Long> putFeed(@PathVariable("no") Long id,
                                          @RequestBody IndoorRequestDto indoorRequestDto) {
        HttpStatus status = HttpStatus.ACCEPTED;
        Long result = null;

        try {
            result = indoorService.modify(id, indoorRequestDto);
            logger.info("putFeed - 뉴스 피드 글 수정 : {}", indoorRequestDto);
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.warn("putFeed - 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(result, status);
    }

    @DeleteMapping(value = "{no}")
    public ResponseEntity<String> deleteFeed(@PathVariable("no") Long id) {
        HttpStatus status = HttpStatus.ACCEPTED;

        try {
            indoorService.delete(id);
            logger.info("deleteFeed - 뉴스 피드 글 삭제 : {}", id);
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.warn("deleteFeed - 에러 : {}", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(status);
    }
}
