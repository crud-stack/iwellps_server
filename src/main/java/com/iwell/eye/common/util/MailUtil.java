package com.iwell.eye.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class MailUtil {

    final protected static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String TO = "TO";
    private static final String CC = "CC";
    private static final String BCC = "BCC";


    @Value("${mail.smtp}")
    protected String MAIL_ROOT_SMTP;

    @Value("${mail.host}")
    protected String MAIL_ROOT_HOST;

    @Value("${mail.id}")
    protected String MAIL_ROOT_ID;

    @Value("${mail.pwd}")
    protected String MAIL_ROOT_PWD;


    public static String makeMailAdress(List<String> targetList) {
        String strAddress = "";
        for (int i = 0; i < targetList.size(); i++) {
            strAddress += targetList.get(i);
            if (i < targetList.size() - 1) {
                strAddress += ",";
            }
        }
        return strAddress;
    }


    /**
     * 대량 메일을 발송한다.
     *
     * @param
     * 		List<Map<String, Object>> 메일 수신인 정보
     * @param
     * 		Map<String,Object> 메일 발송인 및 메일내용
     * @param
     * 		List<Map<String, Object>> 첨부파일정보
     * @return
     * 		boolean
     */
    public boolean mailSend(
            List<Map<String,Object>> mailInfo
            , Map<String,Object> contents
            ,List<Map<String,String>> fileInfo) {
        boolean success = false;
        String resource = "crud.properties";
        String smtp = MAIL_ROOT_SMTP;
        String host = MAIL_ROOT_HOST;
        String id = MAIL_ROOT_ID;
        String pwd = MAIL_ROOT_PWD;


        InternetAddress[] toAddr = null;
        InternetAddress[] cCAddr = null;
        InternetAddress[] bCAddr = null;
        List<String> toList = new ArrayList<String>();
        List<String> cCList = new ArrayList<String>();
        List<String> bCList = new ArrayList<String>();

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", smtp);
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.debug", "true");

        Authenticator authenticator = new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("","");
            }

        };

        String from = contents.get("fromMail").toString();
        String subject = contents.get("title").toString();
        String content = contents.get("content").toString();

        try {

            Session session = Session.getInstance(properties, authenticator);
            Message msg = new MimeMessage(session);


            for(Map<String, Object>map : mailInfo) {
                String tomail = map.get("toMail").toString();
                String reciType = map.get("reciType").toString();
                if(TO.equals(reciType)){
                    toList.add(tomail);
                }

                if(CC.equals(reciType)){
                    cCList.add(tomail);
                }

                if(BCC.equals(reciType)){
                    bCList.add(tomail);
                }
            }

            if (toList.size() > 0) {
                toAddr = InternetAddress.parse(makeMailAdress(toList));
            }
            if (cCList.size() > 0) {
                cCAddr = InternetAddress.parse(makeMailAdress(cCList));
            }
            if (bCList.size() > 0) {
                bCAddr = InternetAddress.parse(makeMailAdress(bCList));
            }



            if(toAddr.length > 0) {

                if(fileInfo.size() > 0) {
                    Multipart mp = new MimeMultipart();
                    MimeBodyPart contentMimeBody = new MimeBodyPart();
                    for(int i= 0; i<fileInfo.size(); i++) {
                        MimeBodyPart attachFileMimeBody = new MimeBodyPart();
                        String path = fileInfo.get(i).get("path").toString();
                        String fileName = fileInfo.get(i).get("orgFileName").toString();
                        File attachFile = new File(path);
                        attachFileMimeBody.setFileName(MimeUtility.encodeText(fileName, "UTF-8", "B"));
                        FileDataSource fileDataSource = new FileDataSource(attachFile);
                        DataHandler dataHandler = new DataHandler(fileDataSource);

                        attachFileMimeBody.setDataHandler(dataHandler);
                        attachFileMimeBody.setDescription("UTF-8");

                        mp.addBodyPart(attachFileMimeBody);
                    }

                    contentMimeBody.setContent(content,"text/html; charset=UTF-8");
                    contentMimeBody.setHeader("Content-Transfer-Encoding", "base64");
                    mp.addBodyPart(contentMimeBody);
                    msg.setContent(mp);

                }else {
                    msg.setContent(content,"text/html; charset=euc-kr");
                }



                msg.setSubject(subject);
                msg.setFrom(new InternetAddress(from));

                msg.setRecipients(Message.RecipientType.TO, toAddr);
                if (null != cCAddr && 0 < cCAddr.length) {
                    msg.setRecipients(Message.RecipientType.CC, cCAddr);
                }
                if (null != bCAddr && 0 < bCAddr.length) {
                    msg.setRecipients(Message.RecipientType.BCC, bCAddr);
                }



                //Transport.send(msg);

                Transport transport = session.getTransport("smtp");
                transport.connect(host, id, pwd);
                transport.sendMessage(msg, msg.getAllRecipients());
                transport.close();


                success = true;
            }else {
                success = false;
            }

        }catch (AddressException ae) {
            logger.error("주소 설정에 문제가 생겼습니다. :" + ae.getMessage());
            success = false;
        }catch (SendFailedException sf) {
            logger.error("메일 정송 실패 :" + sf.getMessage());
            success = false;
        }catch (MessagingException me) {
            logger.error("예기치 못한 이유로 Mail 전송 실패 : " + me.getMessage());
            success = false;
        }catch (Exception e) {
            logger.error("알수 없는 이유로 Mail 전송 실패 : "+e.getMessage());
            success = false;
        }

        return success;
    }

    /**
     * 개인 다이렉트 메일을 발송한다.
     *
     * @param
     * 		Map<String, Object> 메일 수신인 과 발송인 정보,content
     * @param
     * 		List<Map<String, Object>> 첨부파일정보
     *
     * @return
     * 		boolean
     */

    public boolean mailDirectSend(Map<String,Object> mailInfo,List<Map<String,String>> fileInfo) {
        boolean success = false;

        String smtp = "25";
        String host = "119.192.230.40";
//        String id = MAIL_ROOT_ID;
//        String pwd = MAIL_ROOT_PWD;
        String id = "test";
        String pwd = "crudsystem1008!";


        InternetAddress[] toAddr = null;
        InternetAddress[] cCAddr = null;
        InternetAddress[] bCAddr = null;

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", smtp);
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.debug", "true");



        try {

            Session session = Session.getInstance(properties, null);
            Message msg = new MimeMessage(session);


            String toMail = mailInfo.get("toMail").toString();
            String title = mailInfo.get("title").toString();
            String content = mailInfo.get("content").toString();
            String formMail = mailInfo.get("fromMail").toString();
            String cc = String.valueOf(mailInfo.get("cc"));
            String bCc = String.valueOf(mailInfo.get("bCc"));

            if (!"null".equals(toMail)) {
                toAddr = InternetAddress.parse(toMail);
            }
            if (!"null".equals(cc)) {
                cCAddr = InternetAddress.parse(cc);
            }
            if (!"null".equals(bCc)) {
                bCAddr = InternetAddress.parse(bCc);
            }

            if(!"null".equals(toMail)) {

                if(fileInfo.size() > 0) {

                    Multipart mp = new MimeMultipart();
                    MimeBodyPart contentMimeBody = new MimeBodyPart();

                    for(int i= 0; i<fileInfo.size(); i++) {

                        MimeBodyPart attachFileMimeBody = new MimeBodyPart();
                        String path = fileInfo.get(i).get("path").toString();
                        String fileName = fileInfo.get(i).get("orgFileName").toString();
                        File attachFile = new File(path);
                        attachFileMimeBody.setFileName(MimeUtility.encodeText(fileName, "UTF-8", "B"));
                        FileDataSource fileDataSource = new FileDataSource(attachFile);
                        DataHandler dataHandler = new DataHandler(fileDataSource);

                        attachFileMimeBody.setDataHandler(dataHandler);
                        attachFileMimeBody.setDescription("UTF-8");

                        mp.addBodyPart(attachFileMimeBody);
                    }

                    contentMimeBody.setContent(content,"text/html; charset=UTF-8");
                    contentMimeBody.setHeader("Content-Transfer-Encoding", "base64");
                    mp.addBodyPart(contentMimeBody);

                    msg.setContent(mp);

                }else {
                    msg.setContent(content,"text/html; charset=euc-kr");
                }

                msg.setSubject(title);
                msg.setFrom(new InternetAddress(formMail));


                msg.setRecipients(Message.RecipientType.TO, toAddr);

                if (null != cCAddr && 0 < toAddr.length) {
                    msg.setRecipients(Message.RecipientType.CC, cCAddr);
                }
                if (null != bCAddr && 0 < bCAddr.length) {
                    msg.setRecipients(Message.RecipientType.BCC, bCAddr);
                }

                //Transport.send(msg);

                Transport transport = session.getTransport("smtp");
                transport.connect(host, id, pwd);
                transport.sendMessage(msg, msg.getAllRecipients());
                transport.close();
                success = true;
            }else {
                success = false;
            }


        }catch (AddressException ae) {
            logger.error("주소 설정에 문제가 생겼습니다. :" + ae.getMessage());
            success = false;
        }catch (SendFailedException sf) {
            logger.error("메일 정송 실패 :" + sf.getMessage());
            success = false;
        }catch (MessagingException me) {
            logger.error("예기치 못한 이유로 Mail 전송 실패 : " + me.getMessage());
            success = false;
        }catch (Exception e) {
            logger.error("알수 없는 이유로 Mail 전송 실패 : "+e.getMessage());
            success = false;
        }

        return success;
    }
}
