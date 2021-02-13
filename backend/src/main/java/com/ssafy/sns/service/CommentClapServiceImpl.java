package com.ssafy.sns.service;

import com.ssafy.sns.domain.clap.CommentClap;
import com.ssafy.sns.domain.comment.Comment;
import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.clap.ClapResponseDto;
import com.ssafy.sns.dto.user.SimpleUserDto;
import com.ssafy.sns.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentClapServiceImpl implements ClapService {

    private final CommentClapRepositoryImpl commentClapRepository;
    private final UserRepository userRepository;
    private final CommentRepositoryImpl commentRepository;

    @Override
    public void changeClap(Long userId, Long targetId) {

        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        Comment comment = commentRepository.findById(targetId);
        if (comment == null) throw new NoSuchElementException();

        Optional<CommentClap> resultClap = commentClapRepository.findClap(user, comment);
        if (resultClap.isPresent()) {
            user.deleteCommentClap(resultClap.get());
            comment.deleteCommentClap(resultClap.get());
            commentClapRepository.delete(resultClap.get());
        } else {
            CommentClap commentClap = commentClapRepository.save(new CommentClap());
            user.addCommentClap(commentClap);
            comment.addCommentClap(commentClap);
        }
    }

    @Override
    public ClapResponseDto clapUserList(Long targetId) {

        Comment comment = commentRepository.findById(targetId);
        if (comment == null) throw new NoSuchElementException();

        List<Long> clapIds = commentClapRepository.findClapAll(comment)
                .stream()
                .map(commentClap -> commentClap.getUser().getId())
                .collect(Collectors.toList());

        return new ClapResponseDto(userRepository.findByIdIn(clapIds)
                .stream()
                .map(SimpleUserDto::new)
                .collect(Collectors.toList()));
    }
}
