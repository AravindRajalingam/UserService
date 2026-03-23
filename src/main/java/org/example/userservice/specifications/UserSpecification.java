package org.example.userservice.specifications;

import org.example.userservice.model.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class UserSpecification {

    public static Specification<User> byDept(String dept){
        return ((root,query,builder)->{
            return builder.equal(root.get("department"),dept);
        });
    }

    public static Specification<User> byDob(Date dob){
        return ((root, query, builder) -> {
            return builder.greaterThan(root.get("dob"),dob);
        });
    }
}
