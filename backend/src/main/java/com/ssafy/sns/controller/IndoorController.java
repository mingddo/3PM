package com.ssafy.sns.controller;

import com.ssafy.sns.dto.FeedDto;
import com.ssafy.sns.service.FeedServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feed")
public class FeedController {

    private final FeedServiceImpl feedService;

    @Autowired
    public FeedController(FeedServiceImpl feedService) {
        this.feedService = feedService;
    }


    @GetMapping(value = "list")
    public void getFeedList() {

    }

    @GetMapping(value = "{no}")
    public ResponseEntity<FeedDto> getFeed() {
        return null;
    }

    @PostMapping(value = "")
    public ResponseEntity<String> postFeed() {
        return null;
    }

    @PutMapping(value = "{no}")
    public ResponseEntity<String> putFeed() {
        return null;
    }

    @DeleteMapping(value = "{no}")
    public ResponseEntity<String> deleteFeed() {
        return null;
    }
}
