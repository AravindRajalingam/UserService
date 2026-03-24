package org.example.userservice.service;

import org.example.userservice.model.User;
import org.example.userservice.response.UserAndOrders;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAll();
    Optional<User> userNameById(String userId);
    List<User> getUsersByMulAttributes(String dept, Date dob, int year);
    List<User> getUserByDob(Date dob);
    List<User> getUserByYear(int year);
    void deleteFromCacheUsersByDept(String dept,Date dob,int year);
    String removeAllCache();
    List<User> getUsersByDept(String dept);
    List<UserAndOrders> getUser(String dept);
}
