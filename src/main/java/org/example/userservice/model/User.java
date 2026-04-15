package org.example.userservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.example.userservice.dto.Orders;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
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

