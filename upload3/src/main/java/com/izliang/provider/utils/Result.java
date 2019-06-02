package com.izliang.provider.utils;

import com.alibaba.fastjson.JSONObject;

public class Result {

    public static JSONObject Result(int code,String msg,Object data){

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("resultStatus", code == 200 ? true : false);
        jsonObject.put("errorCode", code == 200 ? code: code);
        jsonObject.put("errorMessage", code == 200 ? msg:msg);
        jsonObject.put("resultData", data);

        return jsonObject;

    }


    public static JSONObject ResultPage(int code,String msg,Object datas,int currentPage,int totalElements,int totalNumber){

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("resultStatus", code == 200 ? true : false);
        jsonObject.put("errorCode", code == 200 ? "": code);
        jsonObject.put("errorMessage", code == 200 ? "":msg);

        JSONObject jsonObjectPage = new JSONObject();
        //当前页
        jsonObjectPage.put("currentPage",currentPage);
        //总页数
        jsonObjectPage.put("totalPage",totalElements);
        //数据总条数
        jsonObjectPage.put("totalNumber",totalNumber);
        //数据
        jsonObjectPage.put("list",datas);
        jsonObject.put("resultData", jsonObjectPage);

        return jsonObject;

    }

}
