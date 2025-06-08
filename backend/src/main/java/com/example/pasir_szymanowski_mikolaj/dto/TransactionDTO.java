package com.example.pasir_szymanowski_mikolaj.dto;

import com.example.pasir_szymanowski_mikolaj.model.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
public class TransactionDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Setter
    @NotNull(message = "Kwota nie może być pusta")
    @Min(value = 1, message = "Kwota musi być większa niż 0")
    private Double amount;

    @Setter
    @NotNull(message = "Typ transakcji nie może być pusty")
    //Pattern(regexp = "INCOME|EXPENSE", message = " Typ musi być INCOME lub EXPENSE")
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Setter
    @Size(max = 50, message = "Tagi nie więcej niż 50 znaków")
    private String tags;

    @Setter
    @Size(max = 255, message = "Notatka nie więcej niż 255 znaków")
    private String notes;

    @Setter
    private LocalDateTime timestamp;

}
