package com.iwell.eye.common.model;

import com.iwell.eye.common.base.CommonBaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

@Data
@EqualsAndHashCode(callSuper = false)
public class AddFileVO extends CommonBaseVO {

    private int addFileAid;
    private String mberCoSid; //회원사 sid
    private String fileQryKey; //파일검색키
    private String orgFileNm; //오리지날 파일명
    private String saveFileNm; //저장 파일명
    private String filePath; //파일 경로
    private String downLoadPath; //다운로드 경로
    private String contentType; //확장자
    private long fileSize; //사이즈
    private String regSid; //등록자

    private MultipartFile file; //파일 객체
    private int orderNo;

    private int code;

    public void setFile(String mberCoSid, MultipartFile file,String filePath){
        this.mberCoSid = mberCoSid;
        this.filePath = filePath;
        this.file = file;
    }

    public void setDownLoad(String fileQryKey){
        this.fileQryKey = fileQryKey;
    }

    public void setDelete(String mberCoSid, String fileQryKey){
        this.mberCoSid = mberCoSid;
        this.fileQryKey = fileQryKey;
    }

    public void setList(String mberCoSid, String orgFileNm, String fileQryKey){
        this.mberCoSid = mberCoSid;
        this.orgFileNm = orgFileNm;
        this.fileQryKey = fileQryKey;
    }

    public void setData(String mberCoSid,  String fileQryKey) {
        this.fileQryKey = fileQryKey;
        this.mberCoSid = mberCoSid;
    }

    public void setData(String fileQryKey, String orgFileNm, String saveFileNm, String filePath, String downLoadPath, String contentType, long fileSize) {
        this.fileQryKey = fileQryKey;
        this.orgFileNm = orgFileNm;
        this.saveFileNm = saveFileNm;
        this.filePath = filePath;
        this.downLoadPath = downLoadPath;
        this.contentType = contentType;
        this.fileSize = fileSize;
    }
}
