package com.ssafy.sns.service;

import com.ssafy.sns.domain.file.File;
import com.ssafy.sns.domain.newsfeed.Feed;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    public List<String> findFileNameList(Long indoorId);

    public void addFile(String file, Feed feed);

    public void modifyFiles(List<String> prevFileNames, List<String> curFileNames) throws IOException;
}
