package com.example.pasir_szymanowski_mikolaj.controller;

import com.example.pasir_szymanowski_mikolaj.dto.BalanceDTO;
import com.example.pasir_szymanowski_mikolaj.dto.TransactionDTO;
import com.example.pasir_szymanowski_mikolaj.model.Transaction;
import com.example.pasir_szymanowski_mikolaj.model.User;
import com.example.pasir_szymanowski_mikolaj.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class TransactionGraphQLController {

    private final TransactionService transactionService;

    public TransactionGraphQLController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @QueryMapping
    public List<Transaction> transactions() {
        return transactionService.getAllTransactions();
    }

    @MutationMapping
    public Transaction addTransaction(@Valid @Argument TransactionDTO transactionDTO) {
        return transactionService.createTransaction(transactionDTO);
    }

    @MutationMapping
    public Transaction updateTransaction(@Argument Long id, @Valid @Argument TransactionDTO transactionDTO){
        return transactionService.updateTransaction(id, transactionDTO);
    }

    @MutationMapping
    public boolean deleteTransaction(@Argument Long id){
        Transaction transaction = transactionService.deleteTransaction(id);
        if(transaction != null){
            return true;
        }else {
            return false;
        }
    }

    @QueryMapping
    public BalanceDTO userBalance(@Argument Float days) {
        User user = transactionService.getCurrentUser();
        return transactionService.getUserBalance(user);
    }
}
