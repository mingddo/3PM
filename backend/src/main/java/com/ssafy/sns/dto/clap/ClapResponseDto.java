package com.ssafy.sns.dto.clap;

import com.ssafy.sns.dto.user.SimpleUserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClapResponseDto {

    private List<SimpleUserDto> user;
}
