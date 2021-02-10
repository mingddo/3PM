package com.ssafy.sns.controller;

import com.google.api.Authentication;
import com.ssafy.sns.service.FirebaseCloudMessageService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;

@RestController("/api/notice")
public class NoticeController {

    @Autowired
    FirebaseCloudMessageService firebaseCloudMessageService;

//    @GetMapping(value = "/send", produces = "application/json; charset=utf8")
//    public ResponseEntity<String> send() throws IOException {
//        HttpStatus status = HttpStatus.ACCEPTED;
//        firebaseCloudMessageServicendMessageTo("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBY2Nlc3NUb2tlbiIsImV4cCI6MTYxMjg0MTE3OCwiUmVmcmVzaFRva2VuIjoiZXlKMGVYQWlPaUpLVjFRaUxDSmhiR2NpT2lKSVV6STFOaUo5LmV5SnpkV0lpT2lKU1pXWnlaWE5vVkc5clpXNGlMQ0psZUhBaU9qRTJNVE0zTURFMU56Z3NJblZ6WlhKSlpDSTZNbjAuS255TWVGYXFUaWdGU0JqLUhJWkNLLWNSOFQ1RUxLT0hid19TQUVsbEstTSJ9.TeDotWsD3PRJvYC6HVKWNEuKAVHxjWDDxGZ-RSP1ebk", "123", "456");
//
//        return new ResponseEntity<>("123", status);
//    }
//
//    @ApiOperation(value = "알람 저장하기")
//    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
//    @PostMapping
//    public ResponseEntity<Void> saveNotification(@RequestBody final NotificationReqDto requestDto) {
//        notificationService.saveNoti(requestDto, userId);
//        return ResponseEntity.ok().build();
//    }


}
