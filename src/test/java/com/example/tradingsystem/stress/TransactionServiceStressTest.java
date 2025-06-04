package com.example.tradingsystem.stress;

import com.example.tradingsystem.entity.Transaction;
import com.example.tradingsystem.enums.TradeCardType;
import com.example.tradingsystem.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
public class TransactionServiceStressTest {

    @Autowired
    private TransactionService transactionService;

    @Test
    void stressTestConcurrentTransactions() throws InterruptedException {
        int threadCount = 100;
        int iterationsPerThread = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failureCount = new AtomicInteger(0);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                for (int j = 0; j < iterationsPerThread; j++) {
                    try {
                        String userId = UUID.randomUUID().toString();
                        Transaction transaction = transactionService.createTransaction(
                                userId, new BigDecimal("10.00"), "USD", TradeCardType.DEBIT.getType());
                        successCount.incrementAndGet();
                    } catch (Exception e) {
                        failureCount.incrementAndGet();
                        e.printStackTrace();
                    }
                }
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            Thread.sleep(100);
        }

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        int totalRequests = threadCount * iterationsPerThread;

        System.out.println("===== Stress Test Results =====");
        System.out.println("Total requests: " + totalRequests);
        System.out.println("Successful requests: " + successCount.get());
        System.out.println("Failed requests: " + failureCount.get());
        System.out.println("Total time: " + totalTime + " ms");
        System.out.println("Throughput: " + (totalRequests * 1000.0 / totalTime) + " requests/sec");
    }
}    