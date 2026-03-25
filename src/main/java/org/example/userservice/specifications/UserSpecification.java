package org.example.userservice.specifications;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.example.userservice.dto.Orders;
import org.example.userservice.model.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public static Specification<User> byYear(int year){
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("curr_year"),year);
        });
    }

    public static Specification<User> notPresent(List<String> ids){
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.not(root.get("student_id").in(ids));
        });
    }

//    public static Specification<User> not(){
//        return ((root, query, criteriaBuilder) -> {
//            Join<Object, Object> join = root.join("orders", JoinType.LEFT);
//            return criteriaBuilder.isNull(join.get("order_id"));
//        });
//    }
}
