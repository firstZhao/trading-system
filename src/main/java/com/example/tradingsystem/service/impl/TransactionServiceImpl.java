package com.example.tradingsystem.service.impl;

import com.example.tradingsystem.entity.Transaction;
import com.example.tradingsystem.enums.TradeCardType;
import com.example.tradingsystem.enums.TradeStatus;
import com.example.tradingsystem.repository.TransactionRepository;
import com.example.tradingsystem.service.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {
    @Resource
    private TransactionRepository transactionRepository;

    @Override
    public Transaction createTransaction(String userId, BigDecimal amount, String currency, String type) {
        validateTransactionAmount(amount);
        validateTransactionType(type);

        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                userId,
                amount,
                currency,
                type,
                LocalDateTime.now(),
                TradeStatus.PENDING.getStatus()
        );

        return transactionRepository.save(transaction);
    }

    @Override
    public Optional<Transaction> getTransaction(String id) {
        return transactionRepository.findById(id);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public boolean deleteTransaction(String id) {
        //todo: 业务中一般是逻辑删除, 这里是物理删除
        return transactionRepository.deleteById(id);
    }

    @Override
    public Transaction updateTransactionStatus(String id, Integer status) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found with id: " + id));
        //todo: 正常业务操作引起交易状态变更,会判断一下前置状态
        validateTransactionStatus(status);

        Transaction updatedTransaction = transaction.withStatus(status);
        return transactionRepository.update(updatedTransaction);
    }

    private void validateTransactionAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transaction amount must be positive");
        }
    }

    private void validateTransactionType(String type) {
        if (!TradeCardType.getAll().contains(type)) {
            throw new IllegalArgumentException("Transaction type must be DEBIT or CREDIT");
        }
    }

    private void validateTransactionStatus(Integer status) {
        if (!TradeStatus.getAll().contains(status)) {
            throw new IllegalArgumentException("Invalid transaction status");
        }
    }
}    