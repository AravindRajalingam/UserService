package org.example.userservice.response;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAndOrders {
    private String user_id;
    private String username;
    private List<OrderResponse> orders;
}
