package ru.netology.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {
    private String id;
    private Timestamp created;
    private String credit_id;
    private String payment_id;
}