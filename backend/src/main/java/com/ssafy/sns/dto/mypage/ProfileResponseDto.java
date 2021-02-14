package com.ssafy.sns.dto.mypage;

import com.ssafy.sns.domain.user.User;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfileResponseDto {

    private Long user_id;
    private String nickname;
    private String user_img;
    private String introduce;

    public ProfileResponseDto(User user) {
        this.nickname = user.getNickname();
        this.user_id = user.getId();
        this.user_img = user.getImg();
        this.introduce = user.getIntroduce();
    }
}
