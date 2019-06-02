package com.izliang.provider.model.res;

import com.izliang.provider.model.Admin;

public class ResIndex {

    /*
    * 用户数量
    * */

    private int userNum;

    //obs信息数量
    private int obsNum;

    //当月下载流量(剩余)
    private long flow;

    //当前存储容量（剩余）
    private long size;

    private long totalSize;

    private long totalFlow;

    private long usedSize;

    private long usedFlow;

    private double num1;

    private double num2;

    private double num3;

    private Admin admin;

    //当前公告条数
    private int noticeNum;

    //用户当前申请的空间
    private long userApplySize;

    //用户当前使用的空间
    private long UserUseSize;

    //当前存储文件总数
    private int applyNum;

    //实际存储文件数量
    private int realNum;

    //当前日志数量
    private int logNum;

    //当前obs分类数量
    private int obsClassNum;

    //当前帮助文档分类数
    private int helperClassNum;

    //当前帮助文档总数
    private int helperNum;

    public int getUserNum() {
        return userNum;
    }

    public void setUserNum(int userNum) {
        this.userNum = userNum;
    }

    public int getObsNum() {
        return obsNum;
    }

    public void setObsNum(int obsNum) {
        this.obsNum = obsNum;
    }

    public long getFlow() {
        return flow;
    }

    public void setFlow(long flow) {
        this.flow = flow;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public double getNum1() {
        return num1;
    }

    public void setNum1(double num1) {
        this.num1 = num1;
    }

    public double getNum2() {
        return num2;
    }

    public void setNum2(double num2) {
        this.num2 = num2;
    }

    public double getNum3() {
        return num3;
    }

    public void setNum3(double num3) {
        this.num3 = num3;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public int getNoticeNum() {
        return noticeNum;
    }

    public void setNoticeNum(int noticeNum) {
        this.noticeNum = noticeNum;
    }

    public long getUserApplySize() {
        return userApplySize;
    }

    public void setUserApplySize(long userApplySize) {
        this.userApplySize = userApplySize;
    }

    public long getUserUseSize() {
        return UserUseSize;
    }

    public void setUserUseSize(long userUseSize) {
        UserUseSize = userUseSize;
    }

    public int getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(int applyNum) {
        this.applyNum = applyNum;
    }

    public int getRealNum() {
        return realNum;
    }

    public void setRealNum(int realNum) {
        this.realNum = realNum;
    }

    public int getLogNum() {
        return logNum;
    }

    public void setLogNum(int logNum) {
        this.logNum = logNum;
    }

    public int getObsClassNum() {
        return obsClassNum;
    }

    public void setObsClassNum(int obsClassNum) {
        this.obsClassNum = obsClassNum;
    }

    public int getHelperClassNum() {
        return helperClassNum;
    }

    public void setHelperClassNum(int helperClassNum) {
        this.helperClassNum = helperClassNum;
    }

    public int getHelperNum() {
        return helperNum;
    }

    public void setHelperNum(int helperNum) {
        this.helperNum = helperNum;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public long getTotalFlow() {
        return totalFlow;
    }

    public void setTotalFlow(long totalFlow) {
        this.totalFlow = totalFlow;
    }

    public long getUsedSize() {
        return usedSize;
    }

    public void setUsedSize(long usedSize) {
        this.usedSize = usedSize;
    }

    public long getUsedFlow() {
        return usedFlow;
    }

    public void setUsedFlow(long usedFlow) {
        this.usedFlow = usedFlow;
    }
}
