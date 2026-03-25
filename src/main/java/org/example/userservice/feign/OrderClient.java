package org.example.userservice.feign;

import org.example.userservice.dto.Orders;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient("order-service")
public interface OrderClient {

    @GetMapping("/orders/ordersById")
    Map<String, List<Orders>> ordersByIds(@RequestParam List<String> ids);

    @GetMapping("/orders/userswithorder")
    List<String> usersWithOrder();
}
