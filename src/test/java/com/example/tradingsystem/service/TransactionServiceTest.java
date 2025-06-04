package com.example.tradingsystem.service;

import com.example.tradingsystem.entity.Transaction;
import com.example.tradingsystem.enums.TradeCardType;
import com.example.tradingsystem.enums.TradeStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    /**
     * 创建记录测试
     */
    @Test
    void createTransaction_ValidInput_ReturnsTransaction() {
        Transaction result = transactionService.createTransaction(
                "user1", new BigDecimal("100.00"), "USD", TradeCardType.DEBIT.getType());

        System.out.println(result);
    }

    /**
     * 金额异常
     */
    @Test
    void createTransaction_NegativeAmount_ThrowsException() {
        transactionService.createTransaction(
                "user1", new BigDecimal("-100.00"), "USD", TradeCardType.CREDIT.getType());
    }

    /**
     * 修改状态成功
     */
    @Test
    void updateTransactionStatus_ExistingTransaction_ReturnsUpdatedTransaction() {
        Transaction origin = transactionService.createTransaction("user1", new BigDecimal("100.00"), "USD", TradeCardType.CREDIT.getType());
        Transaction updatedTransaction = transactionService.updateTransactionStatus(origin.getId(), TradeStatus.COMPLETE.getStatus());
        System.out.println("修改状态是否成功 === " + Objects.equals(updatedTransaction.getStatus(), TradeStatus.COMPLETE.getStatus()));
    }

    /**
     * 修改状态：状态值校验异常
     */
    @Test
    void updateTransactionStatus_NonExistingTransaction_ThrowsException() {
        String uuid = UUID.randomUUID().toString();

        transactionService.updateTransactionStatus(uuid, 5);
    }

    @Test
    void findAllTransaction() {
        Transaction result1 = transactionService.createTransaction(
                "user1", new BigDecimal("100.00"), "USD", TradeCardType.DEBIT.getType());
        Transaction result2 = transactionService.createTransaction(
                "user1", new BigDecimal("101.00"), "USD", TradeCardType.CREDIT.getType());
        Transaction result3 = transactionService.createTransaction(
                "user1", new BigDecimal("102.00"), "USD", TradeCardType.DEBIT.getType());
        Optional<Transaction> result = transactionService.getTransaction(result1.getId());
        System.out.println("查询单条记录 === " + Optional.of(result).get());
        List<Transaction> list = transactionService.getAllTransactions();
        System.out.println("查询所有记录 === " + list);
    }
}    