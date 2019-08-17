package com.sendemail.sendemail.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sendemail.sendemail.bean.EmailBean;
import com.sendemail.sendemail.bean.ResultBean;
import com.sendemail.sendemail.service.MailService;
import com.sendemail.sendemail.util.EmailHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Objects;
import java.util.Properties;


@Service("mailService")
@Slf4j
public class MailServiceImpl implements MailService {

    @Value("${email.from:#{null}}")
    private String fromEmail;
    @Value("${email.to:#{null}}")
    private String toEmail;

    @Resource
    private JavaMailSender javaMailSender;

    @Override
    public ResultBean sendSimpleMail() {
        EmailBean bean = new EmailBean();
        bean.setFromEmail(fromEmail);
        bean.setToEmail(toEmail);
        bean.setSubject("测试邮件");
        bean.setText("这是一封测试邮件,发送简单邮件信息");
        return EmailHelper.getInstance().sendSimpleMail(bean);
    }

    @Override
    public ResultBean sendAttachmentsMail() {
        EmailBean bean = new EmailBean();
        bean.setFromEmail(fromEmail);
        bean.setToEmail(toEmail);
        bean.setSubject("添加附件测试");
        bean.setText("这是一封测试邮件，测试邮件发送添加附件文件");
        bean.setEmailAttachment(
            Collections.singletonList(
                new EmailBean.EmailAttachment("这是一个附件文件-哈哈.docx", "C:\\Users\\fangj\\Desktop\\电动车备案查询小程序.docx")));
        return EmailHelper.getInstance().sendAttachmentsMail(bean);
    }

    @Override
    public ResultBean sendInlineMail() {
        EmailBean bean = new EmailBean();
        bean.setFromEmail(fromEmail);
        bean.setToEmail(toEmail);
        bean.setSubject("引入静态资源测试");
        bean.setText("<html><body><img src=\"cid:hello\" ></body></html>");
        bean.setInlineFile(
            new EmailBean.InlineFile("hello", "C:\\Users\\fangj\\Desktop\\cat.jpg"));
        return EmailHelper.getInstance().sendInlineMail(bean);
    }

    @Override
    public ResultBean sendTemplateMail() {
        EmailBean bean = new EmailBean();
        bean.setFromEmail(fromEmail);
        bean.setToEmail(toEmail);
        bean.setSubject("发送模板邮件测试");

        String templatePath = "src/main/resources/templates/example.vm";

        VelocityContext context = new VelocityContext();
        context.put("username", "张三");

        StringWriter writer = replaceTemplateVariable(templatePath, context);
        if (Objects.isNull(writer)) {
            log.error("文本替换错误 templatePath:{}, context：{}", templatePath, JSONObject.toJSONString(context));
            return ResultBean.FAIL("文本替换失败");
        }
        bean.setText(writer.toString());
        return EmailHelper.getInstance().sendTemplateMail(bean);
    }

    /**
     * 替换html模板变量
     * @param templatePath 模板路劲
     * @param context 所需替换变量的map
     * @return
     */
    public StringWriter replaceTemplateVariable(String templatePath, VelocityContext context) {
        //实例化一个StringWriter
        StringWriter writer = new StringWriter();
        try {
            //初始化参数
            Properties properties = new Properties();
            //设置velocity资源加载方式为file
            properties.setProperty("resource.loader", "file");
            //设置velocity资源加载方式为file时的处理类
            properties.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
            //实例化一个VelocityEngine对象
            VelocityEngine velocityEngine = new VelocityEngine(properties);

            //从vm目录下加载hello.vm模板,在eclipse工程中该vm目录与src目录平级
            velocityEngine.mergeTemplate(templatePath, "UTF-8", context, writer);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return writer;
    }
}
