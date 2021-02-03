package com.ssafy.sns.dto.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtDto {

    private String auth_token;
    private Long id;
    private String name;

}
