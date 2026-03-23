package org.example.userservice.service;

import org.example.userservice.model.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAll();
    Optional<User> userNameById(String userId);
    List<User> getUsersByDept(String dept,Date dob);
    List<User> getUserByDob(Date dob);
}
