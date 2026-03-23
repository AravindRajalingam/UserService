package org.example.userservice.service;

import org.example.userservice.model.User;
import org.example.userservice.repository.UserRepository;
import org.example.userservice.specifications.UserSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public List<User> getUsersByDept(String dept,Date dob) {
        return userRepository.findAll(Specification.where(UserSpecification.byDept(dept)).and(UserSpecification.byDob(dob)));
    }

    @Override
    public List<User> getUserByDob(Date dob) {
        return userRepository.findAll(Specification.where(UserSpecification.byDob(dob)));
    }
}
