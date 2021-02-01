package com.ssafy.sns.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
