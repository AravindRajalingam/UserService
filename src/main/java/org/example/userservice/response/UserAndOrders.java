package org.example.userservice.response;

import lombok.*;
import org.example.userservice.dto.Orders;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAndOrders {
    private String user_id;
    private String username;
    private List<OrderResponse> orders;
}
