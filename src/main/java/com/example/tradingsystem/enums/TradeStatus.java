package com.example.tradingsystem.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum TradeStatus {
    PENDING(0),
    COMPLETE(1),
    FAILED(2),
    CANCELLED(3);

    private Integer status;

    TradeStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public static List<Integer> getAll() {
        return Arrays.stream(TradeStatus.values())
                .map(TradeStatus::getStatus)
                .collect(Collectors.toList());
    }
}
