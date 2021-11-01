package com.iwell.eye.common.util;

import com.iwell.eye.common.model.UserJoinInfoVO;
import org.springframework.stereotype.Component;
import ua_parser.Client;
import ua_parser.Parser;

import javax.servlet.http.HttpServletRequest;


@Component
public class UserAgentInfo {

    public static UserJoinInfoVO userAgent (HttpServletRequest req){

        String agent = req.getHeader("User-Agent");
        Parser uaParser;
        UserJoinInfoVO userAgent = new UserJoinInfoVO();

        uaParser = new Parser();
        Client c = uaParser.parse(agent);
        String ip = GetIp(req);
        String browser = c.userAgent.family;
        String browserMinor = "";
        if(c.userAgent.minor == null) {
            browserMinor = "0";
        }else {
            browserMinor = c.userAgent.minor;
        }
        String browserVer = c.userAgent.major+"."+browserMinor;
        String os = c.os.family;

        String osMinor = "";
        if(c.os.minor == null) {
            osMinor = "0";
        }else {
            osMinor = c.os.minor;
        }
        String osVer = c.os.major+"."+osMinor;

        String device = c.device.family;

        userAgent.setIp(ip);
        userAgent.setBrowser(browser);
        userAgent.setBrowserVer(browserVer);
        userAgent.setOs(os);
        userAgent.setOsVer(osVer);
        userAgent.setDevice(device);


        return userAgent;
    }

    public static String GetIp(HttpServletRequest req){
        String ip = req.getHeader("X-FORWARDED-FOR");
        if(ip == null || ip.length() == 0) {
            ip = req.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0) {
            ip = req.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0) {
            ip = req.getRemoteAddr();
        }
        return ip;
    }

}
