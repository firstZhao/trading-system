package com.example.tradingsystem.controller;

import com.example.tradingsystem.entity.Transaction;
import com.example.tradingsystem.enums.TradeCardType;
import com.example.tradingsystem.enums.TradeStatus;
import com.example.tradingsystem.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    void createTransaction_ValidInput_ReturnsCreatedTransaction() throws Exception {
        String uuid = UUID.randomUUID().toString();
        Transaction transaction = new Transaction(
                uuid, "user1", new BigDecimal("100.00"),
                "USD", TradeCardType.CREDIT.getType(),
                null, TradeStatus.PENDING.getStatus());

        when(transactionService.createTransaction("user1", new BigDecimal("100.00"), "USD", TradeCardType.CREDIT.getType()))
                .thenReturn(transaction);

        mockMvc.perform(post("/api/transactions")
                        .param("userId", "user1")
                        .param("amount", "100.00")
                        .param("currency", "USD")
                        .param("type", "DEBIT"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(uuid.toString())))
                .andExpect(jsonPath("$.userId", is("user1")));
    }

    @Test
    void getTransaction_ExistingId_ReturnsTransaction() throws Exception {
        String uuid = UUID.randomUUID().toString();
        Transaction transaction = new Transaction(
                uuid, "user1", new BigDecimal("100.00"), "USD", TradeCardType.DEBIT.getType(), null, TradeStatus.PENDING.getStatus());

        when(transactionService.getTransaction(uuid)).thenReturn(Optional.of(transaction));

        mockMvc.perform(get("/api/transactions/{id}", uuid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(uuid.toString())));
    }

    @Test
    void getAllTransactions_ReturnsListOfTransactions() throws Exception {
        String uuid1 = UUID.randomUUID().toString();
        String uuid2 = UUID.randomUUID().toString();
        List<Transaction> transactions = Arrays.asList(
                new Transaction(uuid1, "user1", new BigDecimal("100.00"), "USD", TradeCardType.DEBIT.getType(), null, TradeStatus.PENDING.getStatus()),
                new Transaction(uuid2, "user2", new BigDecimal("200.00"), "USD", TradeCardType.CREDIT.getType(), null, TradeStatus.COMPLETE.getStatus())
        );

        when(transactionService.getAllTransactions()).thenReturn(transactions);

        mockMvc.perform(get("/api/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(uuid1.toString())))
                .andExpect(jsonPath("$[1].id", is(uuid2.toString())));
    }
}    