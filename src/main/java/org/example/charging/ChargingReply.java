package org.example.charging;

import org.example.enums.Result;

public class ChargingReply {
    private String requestId;
    private String result;
    private Integer gsu;

    public ChargingReply(String requestId, String result, Integer gsu) {
        this.requestId = requestId;
        this.result = result;
        this.gsu = gsu;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getResult() {
        return result;
    }

    public Integer getGsu() {
        return gsu;
    }
}
