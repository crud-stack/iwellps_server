package com.iwell.eye.common.util;

public class HTMLCharacterset {

    public static String setEncodeData(String encodeData) {

        if (encodeData != null) {

            encodeData = encodeData.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
            encodeData = encodeData.replaceAll("\\*", "");
            encodeData = encodeData.replaceAll("\\?", "");
            encodeData = encodeData.replaceAll("\\[", "");
            encodeData = encodeData.replaceAll("\\{", "");
            encodeData = encodeData.replaceAll("\\(", "");
            encodeData = encodeData.replaceAll("\\)", "");
            encodeData = encodeData.replaceAll("\\^", "");
            encodeData = encodeData.replaceAll("\\$", "");
            encodeData = encodeData.replaceAll("'", "");
            //encodeData = encodeData.replaceAll("@", "");
            encodeData = encodeData.replaceAll("%", "");
            encodeData = encodeData.replaceAll(";", "");
            encodeData = encodeData.replaceAll(":", "");
            encodeData = encodeData.replaceAll("-", "");
            encodeData = encodeData.replaceAll("#", "");
            encodeData = encodeData.replaceAll("--", "");
            encodeData = encodeData.replaceAll("-", "");
            encodeData = encodeData.replaceAll(",", "");
            encodeData = encodeData.replaceAll("\\+", "");
            encodeData = encodeData.replaceAll("/", "");
            encodeData = encodeData.replaceAll("=", "");

        }
            return encodeData;
    }

    public static String setSQlInjection(String encodeData) {

        if (encodeData != null) {


            encodeData = encodeData.replaceAll("`", "");
            encodeData = encodeData.replaceAll(";", "");
            encodeData = encodeData.replaceAll("--", "");
            encodeData = encodeData.replaceAll("#", "");
            encodeData = encodeData.replaceAll("\\)", "");
            encodeData = encodeData.replaceAll("\\(", "");
            encodeData = encodeData.replaceAll("'", "");
            encodeData = encodeData.replaceAll("@", "");
            encodeData = encodeData.replaceAll("%", "");
            encodeData = encodeData.replaceAll(";", "");
            encodeData = encodeData.replaceAll(":", "");
            encodeData = encodeData.replaceAll("-", "");
            encodeData = encodeData.replaceAll("#", "");
            encodeData = encodeData.replaceAll("--", "");
            encodeData = encodeData.replaceAll("-", "");
            encodeData = encodeData.replaceAll(",", "");
            encodeData = encodeData.replaceAll("\\+", "");
            encodeData = encodeData.replaceAll("/", "");
            encodeData = encodeData.replaceAll("=", "");
            encodeData = encodeData.replaceAll("UNION", "");
            encodeData = encodeData.replaceAll("SELECT", "");
            encodeData = encodeData.replaceAll("FROM", "");
            encodeData = encodeData.replaceAll("OR", "");
            encodeData = encodeData.replaceAll("AND", "");
            encodeData = encodeData.replaceAll("THEN", "");
            encodeData = encodeData.replaceAll("ELSE", "");
            encodeData = encodeData.replaceAll("CASE", "");
            encodeData = encodeData.replaceAll("END", "");
            encodeData = encodeData.replaceAll("WHEN", "");
            encodeData = encodeData.replaceAll("\\/*", "");
            encodeData = encodeData.replaceAll("\\*/", "");
            encodeData = encodeData.replaceAll("\\r", "");
            encodeData = encodeData.replaceAll("\\n", "");
            encodeData = encodeData.replaceAll(" ", "");

        }
        return encodeData;
    }
}
