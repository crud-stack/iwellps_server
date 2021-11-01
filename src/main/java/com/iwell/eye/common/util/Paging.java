package com.iwell.eye.common.util;

import lombok.Data;

@Data
public class Paging {

    private int pageRowCount; //페이지에 나타낼 로우의 갯수
    private int pageDisplayCount; //화면 하단 디스플레이 페이지 갯수

    private int startRowNum; //보여줄 페이지의 데이터의 시작 ResultSet Row 번호
    private int endRowNum; //보여줄 페이지 데이터의 끝 ResultSet row 번호

    private int pageNum; //보여줄 페이지의 번호
    private int totalRow; //전체 row 갯수
    private int totalPageCount; //전체 페이지의 갯수
    private int startPageNum; //시작 페이지 번호
    private int endPageNum; //끝 페이지 번호

    public void setMssqlPaging(int pageNum, int pageRowCount, int pageDisplayCount, int totalRow){
        this.startRowNum = (pageNum-1)*pageRowCount;
        this.pageRowCount = pageRowCount;
        this.endPageNum = startPageNum+pageDisplayCount-1;
        this.totalPageCount = (int)Math.ceil(totalRow/(double)pageRowCount); //3
        if(totalPageCount < endPageNum){
            this.endPageNum = totalPageCount;
        }
    }

    public void setPaging(int pageNum,int pageDisplayCount, int pageRowCount, int totalRow){
        this.pageNum = pageNum;
        this.startRowNum = (pageNum-1)*pageRowCount;
        this.endRowNum = pageNum*pageRowCount;
        this.pageRowCount = pageRowCount;
        this.totalPageCount = (int)Math.ceil(totalRow/(double)pageRowCount); //3
        this.startPageNum = 1+((pageNum-1)/pageDisplayCount)*pageDisplayCount;

        this.endPageNum = startPageNum+pageDisplayCount-1;

        if(totalPageCount < endPageNum){
            this.endPageNum = totalPageCount;
        }

    }






}

