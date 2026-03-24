package org.example.userservice.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Orders {

    private Long order_id;
    private String item;
    private int quantity;
    private int order_day;
    private int order_month;
    private int order_year;
}
