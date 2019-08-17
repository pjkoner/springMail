package com.sendemail.sendemail.bean;

import lombok.Data;

import java.util.List;

@Data
public class EmailBean extends BaseBean{

    /**
     * 发件人邮箱
     */
    private String fromEmail;
    /**
     * 收件人邮件
     */
    private String toEmail;
    /**
     * 邮件主题
     */
    private String subject;
    /**
     * 邮件内容
     */
    private String text;
    /**
     * 附件
     */
    private List<EmailAttachment> emailAttachment;
    /**
     * 静态资源（多为图片）
     * 映入静态资源时 资源id要与文本中的名称一致
     */
    private InlineFile inlineFile;

    @Data
    public static class EmailAttachment{

        /**
         * 附件文件名
         */
        private String fileName;
        /**
         * 附件文件地址
         */
        private String filePath;

        /**
         * 全参构造函数
         * @param fileName 文件名
         * @param filePath 文件路径
         */
        public EmailAttachment(String fileName, String filePath) {
            this.fileName = fileName;
            this.filePath = filePath;
        }
    }

    @Data
    public static class InlineFile{
        /**
         * 资源id
         */
        private String contentId;
        /**
         * 资源地址
         */
        private String filePath;

        /**
         * 全参构造函数
         * @param contentId 资源id
         * @param filePath 资源地址
         */
        public InlineFile(String contentId, String filePath) {
            this.contentId = contentId;
            this.filePath = filePath;
        }
    }
}
