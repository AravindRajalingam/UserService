package org.example.userservice.dto;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Orders {
    @Id
    private Long order_id;
    private String item;
    private int quantity;
    private int order_day;
    private int order_month;
    private int order_year;
//    @ManyToOne
//    @JoinColumn(name = "user_id" ,unique = true)
//    @JsonBackReference
//    private User users;
}
