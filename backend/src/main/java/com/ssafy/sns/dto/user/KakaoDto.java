package com.ssafy.sns.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Setter
@Getter
@AllArgsConstructor
public class KakaoDto {

    private String email;
    private String name;

}
