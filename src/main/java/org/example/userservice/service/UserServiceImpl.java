package org.example.userservice.service;

import org.example.userservice.dto.Orders;
import org.example.userservice.feign.OrderClient;
import org.example.userservice.model.User;
import org.example.userservice.repository.UserRepository;
import org.example.userservice.response.OrderResponse;
import org.example.userservice.response.UserAndOrders;
import org.example.userservice.specifications.UserSpecification;
import org.jspecify.annotations.NonNull;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OrderClient orderClient;

    public UserServiceImpl(UserRepository userRepository, OrderClient orderClient) {
        this.userRepository = userRepository;
        this.orderClient = orderClient;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> userNameById(String userId) {
//        User user=User.builder().student_id("41").build();
        return userRepository.findById(userId);
    }

    @Override
    @Cacheable(value = "user",key = "{#dept,#dob,#year}")
    public List<User> getUsersByMulAttributes(String dept,Date dob,int year) {
        LoggerFactory.getLogger(UserServiceImpl.class).info("Fetching from DB....");
        final Specification<User> spec=Specification.where(UserSpecification.byDept(dept)
                .and(UserSpecification.byYear(year))
                .and(UserSpecification.byDob(dob)));
        return userRepository.findAll(spec);
    }

    @Override
    public List<User> getUserByDob(Date dob) {
        return userRepository.findAll(Specification.where(UserSpecification.byDob(dob)));
    }

    @Override
    public List<User> getUserByYear(int year) {
        return userRepository.findAll(Specification.where(UserSpecification.byYear(year)));
    }
    @Override
    @CacheEvict(value = "user", key = "{#dept,#dob,#year}" ,beforeInvocation = true)
    public void deleteFromCacheUsersByDept(String dept,Date dob,int year) {

    }

    @Override
    @CacheEvict(value = "user",allEntries = true,beforeInvocation = true)
    public String removeAllCache() {
        return "All cache removed.";
    }

    @Override
    @Cacheable(value = "userByDept",key = "#dept")
    public List<User> getUsersByDept(String dept) {
        return userRepository.findAll(Specification.where(UserSpecification.byDept(dept)));
    }

    @Override
    public List<UserAndOrders> getUser(String dept) {
        List<User> users=userRepository.findAll(Specification.where(UserSpecification.byDept(dept)));
        List<UserAndOrders> res =new ArrayList<>();
        for(User user:users){
            List<Orders> orders=orderClient.ordersById(user.getStudent_id());
            List<OrderResponse> orderResponses = getOrderResponses(orders);
            UserAndOrders userAndOrders = UserAndOrders.builder().user_id(user.getStudent_id()).username(user.getStudent_name()).orders(orderResponses).build();
            res.add(userAndOrders);
        }
        return res;
    }

    private static @NonNull List<OrderResponse> getOrderResponses(List<Orders> orders) {
        List<OrderResponse> orderResponses=new ArrayList<>();
        for(Orders order: orders){
            OrderResponse orderResponse=OrderResponse.builder()
                    .order_id(order.getOrder_id())
                    .item(order.getItem())
                    .quantity(order.getQuantity())
                    .order_date(order.getOrder_day() +"-"+order.getOrder_month()+"-"+order.getOrder_year())
                    .build();
            orderResponses.add(orderResponse);
        }
        return orderResponses;
    }
}
