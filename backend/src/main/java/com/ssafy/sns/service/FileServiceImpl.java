package com.ssafy.sns.service;

import com.ssafy.sns.domain.file.File;
import com.ssafy.sns.domain.newsfeed.Feed;
import com.ssafy.sns.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class FileServiceImpl implements FileService{

    private final FileRepository fileRepository;

    @Override
    public List<String> findFileNameList(Long indoorId) {
        List<File> files = fileRepository.findAllByFeedId(indoorId);
        List<String> result = new ArrayList<>();
        files.stream().forEach(file -> result.add(file.getFileName()));
        return result;
    }

    @Override
    public void addFile(String fileName, Feed feed)  {

        File file = new File(fileName);
        file = fileRepository.save(file);
        feed.addFile(file);
    }

    @Override
    public void modifyFiles(Feed feed, MultipartFile file) {
        // 1. feed id 에 해당하는 모든 사진을 지운다.
//        List<File> currentFiles = fileRepository.findAllByFeedId(feed.getId());
//        fileRepository.delete(file);

        // 2. 새로운 파일들을 저장한다.


    }


}
