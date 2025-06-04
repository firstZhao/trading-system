package com.example.tradingsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Transaction {
    //交易ID - UUID生成, 正常业务中使用分布式ID(分库分表业务) / 表自增ID(简单业务)
    private String id;
    private String userId;
    private BigDecimal amount;
    private String currency;
    //com.example.tradingsystem.enums.TradeCardType
    private String type;
    private LocalDateTime timestamp;
    //com.example.tradingsystem.enums.TradeStatus
    private Integer status;

    public Transaction withStatus(Integer newStatus) {
        return new Transaction(id, userId, amount, currency, type, timestamp, newStatus);
    }
}
