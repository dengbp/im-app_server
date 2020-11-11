package com.yr.net.app.monitor.entity;

import java.io.Serializable;

public class AlertRecord implements Serializable {
    private Long id;

    private String signature;

    private Byte exceptionType;

    private String exceptionDetail;

    private Long errorTime;

    private String receiveEmail;

    private Byte state;

    private Long sendTime;

    private Long createTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
    }

    public Byte getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(Byte exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String getExceptionDetail() {
        return exceptionDetail;
    }

    public void setExceptionDetail(String exceptionDetail) {
        this.exceptionDetail = exceptionDetail == null ? null : exceptionDetail.trim();
    }

    public Long getErrorTime() {
        return errorTime;
    }

    public void setErrorTime(Long errorTime) {
        this.errorTime = errorTime;
    }

    public String getReceiveEmail() {
        return receiveEmail;
    }

    public void setReceiveEmail(String receiveEmail) {
        this.receiveEmail = receiveEmail == null ? null : receiveEmail.trim();
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Long getSendTime() {
        return sendTime;
    }

    public void setSendTime(Long sendTime) {
        this.sendTime = sendTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", signature=").append(signature);
        sb.append(", exceptionType=").append(exceptionType);
        sb.append(", exceptionDetail=").append(exceptionDetail);
        sb.append(", errorTime=").append(errorTime);
        sb.append(", receiveEmail=").append(receiveEmail);
        sb.append(", state=").append(state);
        sb.append(", sendTime=").append(sendTime);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}
