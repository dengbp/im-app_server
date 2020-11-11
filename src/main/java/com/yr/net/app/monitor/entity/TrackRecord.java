package com.yr.net.app.monitor.entity;

import java.io.Serializable;

public class TrackRecord implements Serializable {
    private Long id;

    private String signature;

    private String serverName;

    private Long serverStartTime;

    private Byte state;

    private String remarks;

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

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName == null ? null : serverName.trim();
    }

    public Long getServerStartTime() {
        return serverStartTime;
    }

    public void setServerStartTime(Long serverStartTime) {
        this.serverStartTime = serverStartTime;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
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
        sb.append(", serverName=").append(serverName);
        sb.append(", serverStartTime=").append(serverStartTime);
        sb.append(", state=").append(state);
        sb.append(", remarks=").append(remarks);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}
