package org.example.userservice.feign;

import org.example.userservice.dto.Orders;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("order-service")
public interface OrderClient {

    @GetMapping("/orders/ordersById")
    List<Orders> ordersById(@RequestParam String id);
}
