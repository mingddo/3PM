package com.ssafy.sns.dto.mypage;

import com.ssafy.sns.domain.user.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfileRequestDto {

    private String nickname;
    private String introduce;

}
