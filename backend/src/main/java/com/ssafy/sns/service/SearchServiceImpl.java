package com.ssafy.sns.service;

import com.ssafy.sns.domain.hashtag.Hashtag;
import com.ssafy.sns.domain.hashtag.IndoorHashtag;
import com.ssafy.sns.domain.newsfeed.Indoor;
import com.ssafy.sns.dto.newsfeed.IndoorResponseDto;
import com.ssafy.sns.dto.search.SearchResponseDto;
import com.ssafy.sns.repository.IndoorHashtagRepository;
import com.ssafy.sns.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchServiceImpl {


    private final SearchRepository searchRepository;

    private final IndoorHashtagRepository indoorHashtagRepository;


    public List<Hashtag> searchHashtag(String keyword) {
//        System.out.println(keyword);
        List<Hashtag> hashtagList = searchRepository.findHashtag(keyword);
//        for(Hashtag h : hashtagList) {
//            System.out.println("h = " + h);
//        }
        return hashtagList;
    }
    public List<IndoorResponseDto> searchIndoorAll(Hashtag hash) {
        List<Indoor> searchIndoors = searchRepository.findIndoorAll(hash);
        List<IndoorResponseDto> indoorResponseDtoList = new ArrayList<>();
        for (Indoor searchIndoor : searchIndoors) {
            indoorResponseDtoList.add(new IndoorResponseDto(searchIndoor));
        }
//        System.out.println(searchIndoors.toString());
//        for (Indoor i : searchIndoors) {
//            System.out.println("i = " + i.getContent());
//        }
//        List<IndoorHashtag> indoorHashtags = indoorHashtagRepository.findIndoorHashtagByHashtagTagName(hash);
//        List<Indoor> indoors = new ArrayList<>();
//        indoorHashtags.stream().forEach(indoorHashtag -> indoors.add(indoorHashtag.getIndoor()));

        return indoorResponseDtoList;
    }

}
