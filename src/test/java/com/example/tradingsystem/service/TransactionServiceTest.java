package com.example.tradingsystem.service;

import com.example.tradingsystem.entity.Transaction;
import com.example.tradingsystem.enums.TradeCardType;
import com.example.tradingsystem.enums.TradeStatus;
import com.example.tradingsystem.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void createTransaction_ValidInput_ReturnsTransaction() {
        String uuid = UUID.randomUUID().toString();
        Transaction mockTransaction = new Transaction(
                uuid, "user1", new BigDecimal("100.00"), "USD", "DEBIT", null, TradeStatus.PENDING.getStatus());

        when(transactionRepository.save(any())).thenReturn(mockTransaction);

        Transaction result = transactionService.createTransaction(
                "user1", new BigDecimal("100.00"), "USD", "DEBIT");

        assertNotNull(result);
        assertEquals(uuid, result.getId());
        assertEquals(TradeStatus.PENDING.getStatus(), result.getStatus());
    }

    @Test
    void createTransaction_NegativeAmount_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            transactionService.createTransaction(
                    "user1", new BigDecimal("-100.00"), "USD", TradeCardType.CREDIT.getType());
        });
    }

    @Test
    void updateTransactionStatus_ExistingTransaction_ReturnsUpdatedTransaction() {
        String uuid = UUID.randomUUID().toString();
        Transaction originalTransaction = new Transaction(
                uuid, "user1", new BigDecimal("100.00"), "USD", TradeCardType.CREDIT.getType(), null, TradeStatus.PENDING.getStatus());

        when(transactionRepository.findById(uuid)).thenReturn(Optional.of(originalTransaction));
        when(transactionRepository.update(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Transaction updatedTransaction = transactionService.updateTransactionStatus(uuid, TradeStatus.COMPLETE.getStatus());

        assertEquals(TradeStatus.COMPLETE.getStatus(), updatedTransaction.getStatus());
    }

    @Test
    void updateTransactionStatus_NonExistingTransaction_ThrowsException() {
        String uuid = UUID.randomUUID().toString();
        when(transactionRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            transactionService.updateTransactionStatus(uuid, TradeStatus.COMPLETE.getStatus());
        });
    }
}    