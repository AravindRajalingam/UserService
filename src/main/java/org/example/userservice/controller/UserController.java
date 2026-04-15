package org.example.userservice.controller;

import org.example.userservice.model.User;
import org.example.userservice.serialization.Serialization;
import org.example.userservice.service.UserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.routing-key}") private String routingKey;

    public UserController(UserService userService, RabbitTemplate rabbitTemplate) {
        this.userService = userService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> get(){
        try{
            List<User> users=userService.getAll();
            rabbitTemplate.convertAndSend("sample_exchange",routingKey,Serialization.toJson(users.get(0)));
            return ResponseEntity.status(HttpStatus.OK).body(users);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @GetMapping("/loginUser/{id}")
    public ResponseEntity<?> loginUser(@RequestHeader("Authorization") String token, @PathVariable String id){
        token=token.replace("Bearer ","");
//        String userId= jwtUtil.getSubject(token);
        return ResponseEntity.status(HttpStatus.OK).body(userService.userNameById(id).orElse(null));
    }

    @GetMapping("/byDept")
    public ResponseEntity<?> filterByDept(@RequestParam String dept,@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dob,@RequestParam int year){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsersByMulAttributes(dept,dob,year));
    }

    @GetMapping("/byDob/{dob}")
    public ResponseEntity<?> filterByDob(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dob){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByDob(dob));
    }

    @GetMapping("/byYear")
    public ResponseEntity<?> filterByYear(@RequestParam int year){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByYear(year));
    }

    @GetMapping("/sample")
    public ResponseEntity<?> sample(){
        return ResponseEntity.status(HttpStatus.OK).body("Sample endpoint");
    }

    @DeleteMapping("/deleteCache")
    public ResponseEntity<?> delete(@RequestParam String dept,@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dob,int year){
        userService.deleteFromCacheUsersByDept(dept,dob,year);
        return ResponseEntity.status(HttpStatus.OK).body("Removed from cache.");
    }

    @DeleteMapping("/deleteAllCache")
    public ResponseEntity<?> deleteAll(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.removeAllCache());
    }

    @GetMapping("/userById")
    public ResponseEntity<?> userById(@RequestParam String id){
        return ResponseEntity.status(HttpStatus.OK).body(Serialization.fromJson(Serialization.toJson(userService.userById(id)),User.class));
    }

    @GetMapping("/userByDept")
    public ResponseEntity<?> userByDept(@RequestParam String dept){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsersByDept(dept));
    }

    @GetMapping("/usersWithOrders")
    public ResponseEntity<?> usersWithOrders(@RequestParam String dept){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(dept));
    }

    @GetMapping("/usersWithoutOrders")
    public ResponseEntity<?> usersWithoutOrders(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.usersWithoutOrders());
    }
}
