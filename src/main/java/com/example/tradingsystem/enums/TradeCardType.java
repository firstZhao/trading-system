package com.example.tradingsystem.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum TradeCardType {
    DEBIT("DEBIT"),
    CREDIT("CREDIT");

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    TradeCardType(String type) {
        this.type = type;
    }

    public static List<String> getAll() {
        return Arrays.stream(TradeCardType.values())
                .map(TradeCardType::getType)
                .collect(Collectors.toList());
    }
}
