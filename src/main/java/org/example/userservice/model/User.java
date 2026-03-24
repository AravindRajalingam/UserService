package org.example.userservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "students")
public class User implements Serializable {
    @Id
    private String student_id;
    private String student_name;
    private String department;
    private int curr_year;
    private Date dob;

    @Transient
    private String phone;

    @Embedded
    private Address address;
}

@Embeddable
class Address{
    private String street;
    private String city;
    private String district;
}

