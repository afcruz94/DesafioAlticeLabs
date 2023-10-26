package org.example.charging;

import org.example.enums.Result;

public class ChargingReply {
    private final String requestId;
    private final Result result;
    private final Integer gsu;

    public ChargingReply(String requestId, Result result, Integer gsu) {
        this.requestId = requestId;
        this.result = result;
        this.gsu = gsu;
    }

    public String getRequestId() {
        return requestId;
    }

    public Result getResult() {
        return result;
    }

    public Integer getGsu() {
        return gsu;
    }
}
