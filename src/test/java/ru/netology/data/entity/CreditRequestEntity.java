package ru.netology.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditRequestEntity {
    private String id;
    private String bank_id;
    private Timestamp created;
    private String status;
}
