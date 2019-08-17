package com.sendemail.sendemail.service;

import com.sendemail.sendemail.bean.ResultBean;

public interface MailService {

    ResultBean sendSimpleMail();

    ResultBean sendAttachmentsMail();

    ResultBean sendInlineMail();

    ResultBean sendTemplateMail();
}
