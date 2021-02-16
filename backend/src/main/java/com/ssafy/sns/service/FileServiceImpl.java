package com.ssafy.sns.service;

import com.ssafy.sns.domain.file.File;
import com.ssafy.sns.domain.newsfeed.Feed;
import com.ssafy.sns.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class FileServiceImpl implements FileService{

    private final FileRepository fileRepository;
    private final S3Service s3Service;

    @Override
    public List<String> findFileNameList(Long id) {
        return fileRepository.findAllByFeedId(id)
                .stream()
                .map(file -> file.getFileName())
                .collect(Collectors.toList());
    }

    @Override
    public void addFile(String fileName, Feed feed)  {

        File file = new File(fileName);
        file = fileRepository.save(file);
        feed.addFile(file);
    }

    @Override
    public void modifyFiles(List<String> prevFileNames, List<String> curFileNames) throws IOException {
        // 삭제해야 할 리스트만 뽑는다.
        Map<String, Boolean> map = new HashMap<>();
        for (String fileName : prevFileNames) {
            map.put(fileName, false);
        }

        for (String fileName : curFileNames) {
            map.put(fileName, true);
        }

        for (Map.Entry<String, Boolean> entry : map.entrySet()) {
            String key = entry.getKey();
            boolean value = entry.getValue();
            // 삭제된 파일 이름
            if (!value) {
                s3Service.deleteFile(key);
                File file = fileRepository.findByFileName(key).get();
                Feed feed = file.getFeed();
                feed.deleteFile(file);
            }
        }
    }

}
