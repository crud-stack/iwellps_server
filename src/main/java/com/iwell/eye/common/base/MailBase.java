package com.iwell.eye.common.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.lang.invoke.MethodHandles;


public class MailBase {

    final protected static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Value("${mail.smtp}")
    protected String MAIL_ROOT_SMTP;

    @Value("${mail.host}")
    protected String MAIL_ROOT_HOST;

    @Value("${mail.id}")
    protected String MAIL_ROOT_ID;

    @Value("${mail.pwd}")
    protected String MAIL_ROOT_PWD;

}
