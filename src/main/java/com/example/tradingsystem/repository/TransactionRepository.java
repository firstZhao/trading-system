package com.example.tradingsystem.repository;

import com.example.tradingsystem.entity.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository {
    /**
     * 创建交易
     *
     * @param transaction
     * @return
     */
    Transaction save(Transaction transaction);

    /**
     * 根据交易ID查询交易记录
     *
     * @param id
     * @return
     */
    Optional<Transaction> findById(String id);

    /**
     * 查询所有交易记录
     *
     * @return
     */
    List<Transaction> findAll();

    /**
     * 删除交易记录
     *
     * @param id
     * @return
     */
    boolean deleteById(String id);

    /**
     * 更新交易记录
     *
     * @param transaction
     * @return
     */
    Transaction update(Transaction transaction);
}    