package com.ssafy.sns.service;

import com.ssafy.sns.domain.hashtag.Hashtag;
import com.ssafy.sns.domain.hashtag.IndoorHashtag;
import com.ssafy.sns.domain.newsfeed.Indoor;
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


    public Hashtag searchHashtag(String keyword) {
        System.out.println(keyword);
        Hashtag hash = searchRepository.findHashtag(keyword);
        System.out.println(hash.getTagName());

        return hash;
    }
    public List<Indoor> searchIndoorAll(String hash) {
//        List<Indoor> searchIndoors = searchRepository.findIndoorAll(hash);
//        System.out.println(searchIndoors.toString());

        List<IndoorHashtag> indoorHashtags = indoorHashtagRepository.findIndoorHashtagsByHashtagTagName(hash);
        List<Indoor> indoors = new ArrayList<>();
        indoorHashtags.stream().forEach(indoorHashtag -> indoors.add(indoorHashtag.getIndoor()));

        return indoors;
    }

}
