package com.iwell.eye.common.util;

import com.iwell.eye.common.model.AddFileVO;
import com.google.common.io.ByteStreams;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddFileUtil {

    public static AddFileVO upload(AddFileVO fileVo) throws Exception {

        int pos = fileVo.getFile().getOriginalFilename().lastIndexOf( "." );
        String extension = fileVo.getFile().getOriginalFilename().substring( pos + 1 );

        String fileQryKey = createFileSearchKey();
        String fileName = fileQryKey +"."+extension;

        AddFileVO addFileVO = new AddFileVO();

        String path = fileVo.getFilePath()+fileVo.getMberCoSid()+"/"+fileName;

        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }

        fileVo.getFile().transferTo(new File(path));
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/file/download/")
                .path(fileName)
                .toUriString();

        addFileVO.setData(
                    fileQryKey
                ,   fileVo.getFile().getOriginalFilename()
                ,   fileName
                ,   path
                ,   fileDownloadUri
                ,   fileVo.getFile().getContentType()
                ,   fileVo.getFile().getSize()
        );

        return addFileVO;
    }

    public static boolean deleteFile(String path){
        File file = new File(path);
        if(file.exists() == true) {
            file.delete();
            return true;
        }else {
            return false;
        }
    }

    public static String createFileSearchKey() throws Exception{
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String date = time.format(calendar.getTime());
        return date;
    }

    public static ByteArrayResource fileToByteArrayResource(AddFileVO fileVo) throws IOException {
        // =============================================
        // 첨부파일 전송을 위한 작업
        // =============================================

        File file = new File(fileVo.getFilePath());

        byte[] bytes = null;
        FileInputStream fileInputStream = null;
        ByteArrayResource resource = null;


        fileInputStream = new FileInputStream(file);
        bytes = ByteStreams.toByteArray(fileInputStream);
        String orgFileNm = fileVo.getOrgFileNm();

        resource = new ByteArrayResource(bytes){
            @Override
            public String getFilename() throws IllegalStateException {
                return orgFileNm;
            }
        };

        if(fileInputStream != null) { fileInputStream.close(); }


        return resource;
    }
}
