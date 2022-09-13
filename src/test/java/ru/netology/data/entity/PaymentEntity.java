package ru.netology.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEntity {
    private String id;
    private int amount;
    private Timestamp created;
    private String status;
    private String transaction_id;
}