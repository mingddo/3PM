package com.ssafy.sns.dto.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponseDto {

    private String accToken;
    private String refToken;
    private Long id;
}
