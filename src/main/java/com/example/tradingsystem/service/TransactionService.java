package com.example.tradingsystem.service;

import com.example.tradingsystem.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface TransactionService {
    /**
     * 创建交易
     *
     * @param userId
     * @param amount
     * @param currency
     * @param type
     * @return
     */
    Transaction createTransaction(String userId, BigDecimal amount, String currency, String type);

    /**
     * 获取交易记录
     *
     * @param id
     * @return
     */
    Optional<Transaction> getTransaction(String id);

    /**
     * 获取所有交易记录
     *
     * @return
     */
    List<Transaction> getAllTransactions();

    /**
     * 删除交易记录 - 业务一般是逻辑删除
     *
     * @param id
     * @return
     */
    boolean deleteTransaction(String id);

    /**
     * 更新交易记录 - 一般根据业务操作修改状态 - 会判断一下前置状态
     *
     * @param id
     * @param status
     * @return
     */
    Transaction updateTransactionStatus(String id, Integer status);
}    