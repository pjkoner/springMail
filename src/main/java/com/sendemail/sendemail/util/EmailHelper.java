package com.sendemail.sendemail.util;

import com.sendemail.sendemail.bean.EmailBean;
import com.sendemail.sendemail.bean.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Objects;

@Slf4j
@Component
public class EmailHelper {

    @Resource
    private JavaMailSender javaMailSender;

    private static EmailHelper INSTANCE;

    public EmailHelper() {
        INSTANCE = this;
    }

    public static EmailHelper getInstance(){
        return INSTANCE;
    }

    /**
     * 发送一封简单的文本文件
     * @param emailBean 文件内容
     * @return
     */
    public ResultBean sendSimpleMail(EmailBean emailBean) {
        log.info("==开始发送邮件, Subject: {}, setTo:{}", emailBean.getSubject(), emailBean.getToEmail());
        SimpleMailMessage message = new SimpleMailMessage();
        //发件人
        message.setFrom(emailBean.getFromEmail());
        //收件人
        message.setTo(emailBean.getToEmail());
        //主题
        message.setSubject(emailBean.getSubject());
        //内容
        message.setText(emailBean.getText());
        try {
            javaMailSender.send(message);
            log.info("==邮件发送成功, Subject: {}, setTo:{}", emailBean.getSubject(), emailBean.getToEmail());
            return ResultBean.SUCCESS();
        } catch (Exception e) {
            log.error("==邮件发送失败", e);
            return ResultBean.FAIL();
        }
    }

    /**
     * 发送一封带附件的邮件
     * @param emailBean 文件内容
     * @return
     */
    public ResultBean sendAttachmentsMail(EmailBean emailBean) {
        log.info("==开始发送邮件, Subject: {}, setTo:{}", emailBean.getSubject(), emailBean.getToEmail());
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(emailBean.getFromEmail());
            helper.setTo(emailBean.getToEmail());
            helper.setSubject(emailBean.getSubject());
            helper.setText(emailBean.getText());
            if (!CollectionUtils.isEmpty(emailBean.getEmailAttachment())) {
                emailBean.getEmailAttachment().stream().filter(Objects::nonNull).forEach(bean -> {
                    FileSystemResource file = new FileSystemResource(new File(bean.getFilePath()));
                    try {
                        helper.addAttachment(bean.getFileName(), file);
                    } catch (MessagingException e) {
                        log.error("==添加附件失败, filename:{}, filePath:{}", bean.getFileName(), bean.getFilePath());
                        e.printStackTrace();
                    }
                });
            }
        } catch (MessagingException e) {
            log.error("==邮件发送失败", e);
            return ResultBean.FAIL();
        }
        try {
            javaMailSender.send(mimeMessage);
            log.info("==邮件发送成功, Subject: {}, setTo:{}", emailBean.getSubject(), emailBean.getToEmail());
            return ResultBean.SUCCESS();
        } catch (Exception e) {
            log.error("==邮件发送失败", e);
        }
        return ResultBean.FAIL();
    }

    /**
     * 发送一封嵌入静态资源的邮件
     * @param emailBean 文件内容
     * @return
     */
    public ResultBean sendInlineMail(EmailBean emailBean) {
        log.info("==开始发送邮件, Subject: {}, setTo:{}", emailBean.getSubject(), emailBean.getToEmail());
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(emailBean.getFromEmail());
            helper.setTo(emailBean.getToEmail());
            helper.setSubject(emailBean.getSubject());
            helper.setText(emailBean.getText(), true);
            // 注意addInline()中资源名称 hello 必须与 text正文中cid:hello对应起来
            FileSystemResource file = new FileSystemResource(new File(emailBean.getInlineFile().getFilePath()));
            helper.addInline(emailBean.getInlineFile().getContentId(), file);
        } catch (MessagingException e) {
            log.error("==邮件发送失败", e);
            return ResultBean.FAIL();
        }
        try {
            javaMailSender.send(mimeMessage);
            log.info("==邮件发送成功, Subject: {}, setTo:{}", emailBean.getSubject(), emailBean.getToEmail());
            return ResultBean.SUCCESS();
        } catch (Exception e) {
            log.error("==邮件发送失败", e);
        }
        return ResultBean.FAIL();
    }

    /**
     * 发送一封模板邮件
     * @param emailBean 文件内容
     * @return
     */
    public ResultBean sendTemplateMail(EmailBean emailBean) {
        log.info("==开始发送邮件, Subject: {}, setTo:{}", emailBean.getSubject(), emailBean.getToEmail());
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(emailBean.getFromEmail());
            helper.setTo(emailBean.getToEmail());
            helper.setSubject(emailBean.getSubject());
            helper.setText(emailBean.getText(), true);
        } catch (MessagingException e) {
            log.error("==邮件发送失败", e);
            return ResultBean.FAIL();
        }
        try {
            javaMailSender.send(mimeMessage);
            log.info("==邮件发送成功, Subject: {}, setTo:{}", emailBean.getSubject(), emailBean.getToEmail());
            return ResultBean.SUCCESS();
        } catch (Exception e) {
            log.error("==邮件发送失败", e);
        }
        return ResultBean.FAIL();
    }
}
