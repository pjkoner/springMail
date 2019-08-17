package com.sendemail.sendemail.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sendemail.sendemail.SendEmailApplicationTests;
import com.sendemail.sendemail.bean.ResultBean;
import com.sendemail.sendemail.controller.MailController;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;

@Slf4j
public class MailServiceImplTest extends SendEmailApplicationTests {

    @Resource
    private MailController mailController;

    /**
     * 测试发送模板邮件
     */
    @Test
    public void testSendTemplateMail(){
        ResultBean resultBean = mailController.sendTemplateMail();
        log.info(JSONObject.toJSONString(resultBean));
    }
}