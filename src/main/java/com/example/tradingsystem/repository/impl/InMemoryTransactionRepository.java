package com.example.tradingsystem.repository.impl;

import com.example.tradingsystem.entity.Transaction;
import com.example.tradingsystem.repository.TransactionRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryTransactionRepository implements TransactionRepository {
    private final Map<String, Transaction> transactionMap = new ConcurrentHashMap<>();

    @Override
    public Transaction save(Transaction transaction) {
        transactionMap.put(transaction.getId(), transaction);
        return transaction;
    }

    @Override
    public Optional<Transaction> findById(String id) {
        return Optional.ofNullable(transactionMap.get(id));
    }

    @Override
    public List<Transaction> findAll() {
        return new ArrayList<>(transactionMap.values());
    }

    @Override
    public boolean deleteById(String id) {
        return transactionMap.remove(id) != null;
    }

    @Override
    public Transaction update(Transaction transaction) {
        if (!transactionMap.containsKey(transaction.getId())) {
            throw new IllegalArgumentException("Transaction not found with id: " + transaction.getId());
        }
        return save(transaction);
    }
}    