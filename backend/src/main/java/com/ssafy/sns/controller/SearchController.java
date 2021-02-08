package com.ssafy.sns.controller;

import com.ssafy.sns.domain.hashtag.Hashtag;
import com.ssafy.sns.dto.search.SearchResponseDto;
import com.ssafy.sns.service.SearchServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Search 관련 Controller
 */
@RestController
@RequestMapping("/search")
public class SearchController {
    public static final Logger logger = LoggerFactory.getLogger(IndoorController.class);

    @Autowired
    private SearchServiceImpl searchService;

    // return 형태 List<SearchResponseDto> -> (tagname, List<IndoorResponseDto>)
    @GetMapping("/all")
    public ResponseEntity<List<SearchResponseDto>> search(@RequestParam("keyword") String keyword){
        List<Hashtag> hashtagList = searchService.searchHashtag(keyword);
        List<SearchResponseDto> searchResponseDto = new ArrayList<>(); //  태그명, 태그게시물 List
        for (Hashtag h : hashtagList) {
            searchResponseDto.add(new SearchResponseDto(h.getTagName(), searchService.searchIndoorAll(h)));
        }
        return new ResponseEntity<>(searchResponseDto, HttpStatus.OK);
    }
}
