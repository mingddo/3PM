package com.ssafy.sns.controller;

import com.ssafy.sns.service.FirebaseCloudMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoticeController {

    @Autowired
    FirebaseCloudMessageService firebaseCloudMessageService;


}
