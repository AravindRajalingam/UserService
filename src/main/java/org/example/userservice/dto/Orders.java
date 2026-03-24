package org.example.userservice.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @Column(name = "order_id")
    private Long order_id;
    @Column(name = "user_id")
    private String user_id;
    @Column(name = "item")
    private String item;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "order_day")
    private int order_day;
    @Column(name = "order_month")
    private int order_month;
    @Column(name = "order_year")
    private int order_year;
}
