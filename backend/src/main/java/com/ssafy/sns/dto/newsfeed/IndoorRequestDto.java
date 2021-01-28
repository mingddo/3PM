package com.ssafy.sns.dto.newsfeed;

import com.ssafy.sns.domain.newsfeed.Indoor;
import com.ssafy.sns.domain.user.User;
import lombok.*;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class IndoorRequestDto {

    private Long indoorId;
    private String content;
    private String file;
    private List<String> tags;
    private Long userId;

    @Builder
    public IndoorRequestDto(Long indoorId, String content, String file, List<String> tags, Long userId) {
        this.content = content;
        this.file = file;
        this.tags = tags;
        this.userId = userId;
    }

    public Indoor toEntity(User user) {
        return Indoor.builder()
                .content(content)
                .file(file)
                .user(user)
                .build();
    }
}
