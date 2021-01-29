package com.ssafy.sns.controller;


import com.ssafy.sns.domain.hashtag.Hashtag;
import com.ssafy.sns.domain.newsfeed.Indoor;
import com.ssafy.sns.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Search 관련 Controller
 */
@Controller
@RequestMapping("/search")
public class SearchController {

//    @Autowired
//    private SearchService searchService;
//
//    @GetMapping("/all")
//    public ResponseEntity search(@RequestParam(value = "keyword") String keyword){
//        Hashtag hash = searchService.searchHashtag(keyword);
////        System.out.println(hash.getTagName());
//        List<Indoor> IndoorList = new ArrayList<>();
//        IndoorList = searchService.searchIndoorAll(hash.getTagName());
//        System.out.println(IndoorList.toString());
//        return new ResponseEntity(IndoorList, HttpStatus.OK);
//    }
}
