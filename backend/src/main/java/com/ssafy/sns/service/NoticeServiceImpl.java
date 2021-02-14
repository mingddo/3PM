package com.ssafy.sns.service;

import com.ssafy.sns.domain.notice.Notice;
import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.repository.NoticeRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{


    private final NoticeRepositoryImpl noticeRepository;

    @Override
    public List<Notice> followList(User user) {
        return noticeRepository.followList(user);
    }

    @Override
    public List<Notice> feedClabList(User user) {
        return noticeRepository.feedClabList(user);
    }

    @Override
    public List<Notice> commentList(User user) {
        return noticeRepository.commentList(user);
    }
}
