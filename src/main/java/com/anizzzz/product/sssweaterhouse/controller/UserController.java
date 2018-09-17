package com.anizzzz.product.sssweaterhouse.controller;

import com.anizzzz.product.sssweaterhouse.dto.ResponseMessage;
import com.anizzzz.product.sssweaterhouse.model.User;
import com.anizzzz.product.sssweaterhouse.service.IUserService;
import com.anizzzz.product.sssweaterhouse.validation.UserValidator;
import com.anizzzz.product.sssweaterhouse.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;


@RestController
public class UserController {
    private final IUserService iUserService;
    private final UserValidator userValidator;

    @Autowired
    public UserController(IUserService userService, UserValidator userValidator) {
        this.iUserService = userService;
        this.userValidator = userValidator;
    }

    @InitBinder
    public void dataBinding(WebDataBinder binder) {
        binder.addValidators(userValidator);
    }

    @PostMapping("/user")
    public ResponseEntity<?> save(@Valid @RequestBody User user, HttpServletRequest request){
        ResponseMessage message=iUserService.save(user, request);
        return new ResponseEntity<>(message, message.getHttpStatus());
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
    public ResponseEntity<?> resendVerificationToken(@RequestParam String username, HttpServletRequest request){
        ResponseMessage message=iUserService.resendVerificationToken(username, request);

        return new ResponseEntity<>(message,message.getHttpStatus());
    }

    @GetMapping("/user/activate-user")
    public ResponseEntity<?> activateUser(@RequestParam String token){
        ResponseMessage message=iUserService.activateUser(token);

        return new ResponseEntity<>(message,message.getHttpStatus());
    }

    @PostMapping("/user/send-reset-password-token")
    public ResponseEntity<?> resettingPassword(@RequestParam String username, HttpServletRequest request){
        ResponseMessage message=iUserService.sendResetPasswordToken(username, request);

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
