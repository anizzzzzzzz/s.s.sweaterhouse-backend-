package com.anizzzz.product.sssweaterhouse.controller;

import com.anizzzz.product.sssweaterhouse.dto.ResponseMessage;
import com.anizzzz.product.sssweaterhouse.model.User;
import com.anizzzz.product.sssweaterhouse.service.IUserService;
import com.anizzzz.product.sssweaterhouse.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;


@RestController
public class UserController {
    private final IUserService iUserService;

    @Autowired
    public UserController(IUserService userService) {
        this.iUserService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<?> save(@RequestBody User user){
        ResponseMessage message=iUserService.save(user);
        return new ResponseEntity<>(message, message.getHttpStatus());
    }

    @GetMapping("/users")
    public ResponseEntity<?> findAll(Pageable pageable){
        return ResponseEntity.ok(iUserService.findAll(pageable));
    }

    @GetMapping("/user")
    @JsonView({View.ShowUser.class})
    public ResponseEntity<?> findOne(@RequestParam String username){
        Optional<User> user=iUserService.findByUsername(username);
        if(user.isPresent()){
            return ResponseEntity.ok(user);
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/user/resend-token")
    public ResponseEntity<?> resendVerificationToken(@RequestParam String username){
        ResponseMessage message=iUserService.resendVerificationToken(username);

        return new ResponseEntity<>(message,message.getHttpStatus());
    }

    @PostMapping("/user/activate-user")
    public ResponseEntity<?> activateUser(@RequestParam String token){
        ResponseMessage message=iUserService.activateUser(token);

        return new ResponseEntity<>(message,message.getHttpStatus());
    }

    @PostMapping("/user/send-reset-password-token")
    public ResponseEntity<?> resettingPassword(@RequestParam String username){
        ResponseMessage message=iUserService.sendResetPasswordToken(username);

        return new ResponseEntity<>(message, message.getHttpStatus());
    }

    @PostMapping("/user/reset-password")
    public ResponseEntity<?> checkResetPasswordTokenValidity(@RequestParam String username,
                                                             @RequestParam String token,
                                                             @RequestParam String password){
        ResponseMessage message=iUserService.resetUserPassword(token,username,password);

        return new ResponseEntity<>(message, message.getHttpStatus());
    }
}