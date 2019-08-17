package com.sendemail.sendemail.controller;

import com.sendemail.sendemail.bean.ResultBean;
import com.sendemail.sendemail.service.MailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Resource
    private MailService mailService;

    @GetMapping("/simple")
    public ResultBean sendSimpleMail(){
        return mailService.sendSimpleMail();
    }

    @GetMapping("/attachments")
    public ResultBean sendAttachmentsMail() {
        return mailService.sendAttachmentsMail();
    }

    @GetMapping("/inline")
    public ResultBean sendInlineMail() {
        return mailService.sendInlineMail();
    }

    @GetMapping("/template")
    public ResultBean sendTemplateMail() {
        return mailService.sendTemplateMail();
    }
}
