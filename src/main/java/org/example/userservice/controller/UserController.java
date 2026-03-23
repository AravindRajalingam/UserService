package org.example.userservice.controller;

import org.example.userservice.util.JwtUtil;
import org.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final JwtUtil jwtUtil;

    public UserController(UserService userService,JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil=jwtUtil;
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> get(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
    }

    @GetMapping("/loginUser/{id}")
    public ResponseEntity<?> loginUser(@RequestHeader("Authorization") String token, @PathVariable String id){
        token=token.replace("Bearer ","");
//        String userId= jwtUtil.getSubject(token);
         String userId=id;
        return ResponseEntity.status(HttpStatus.OK).body(userService.userNameById(userId));
    }

    @GetMapping("/byDept")
    public ResponseEntity<?> filterByDept(@RequestParam String dept,@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dob,@RequestParam int year){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsersByDept(dept,dob,year));
    }

    @GetMapping("/byDob/{dob}")
    public ResponseEntity<?> filterByDob(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dob){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByDob(dob));
    }
}
