package com.iwell.eye.common.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.lang.invoke.MethodHandles;

public class EyeBaseService {
	
    final protected static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    //메세지 모듈
    @Value("${api.url.eye}")
    protected String API_ROOT_MESSAGE;
}
