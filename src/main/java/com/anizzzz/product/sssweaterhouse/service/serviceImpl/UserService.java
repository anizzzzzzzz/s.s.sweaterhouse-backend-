package com.anizzzz.product.sssweaterhouse.service.serviceImpl;

import com.anizzzz.product.sssweaterhouse.dto.ResponseMessage;
import com.anizzzz.product.sssweaterhouse.model.PasswordResetToken;
import com.anizzzz.product.sssweaterhouse.model.User;
import com.anizzzz.product.sssweaterhouse.model.VerificationToken;
import com.anizzzz.product.sssweaterhouse.repository.UserRepository;
import com.anizzzz.product.sssweaterhouse.service.IPasswordResetService;
import com.anizzzz.product.sssweaterhouse.service.IRoleService;
import com.anizzzz.product.sssweaterhouse.service.IUserService;
import com.anizzzz.product.sssweaterhouse.service.IVerificationTokenService;
import com.anizzzz.product.sssweaterhouse.utils.TokenExpirationUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.anizzzz.product.sssweaterhouse.utils.TokenExpirationUtils.setTokenExpirationDate;

@Service
public class UserService implements IUserService {
    private static final int EXPIRATION = 60*24;
    private final UserRepository userRepository;
    private final IRoleService iRoleService;
    private final IVerificationTokenService iVerificationTokenService;
    private final IPasswordResetService iPasswordResetService;

    @Autowired
    public UserService(UserRepository userRepository, IRoleService iRoleService, IVerificationTokenService iVerificationTokenService, IPasswordResetService iPasswordResetService) {
        this.userRepository = userRepository;
        this.iRoleService = iRoleService;
        this.iVerificationTokenService = iVerificationTokenService;
        this.iPasswordResetService = iPasswordResetService;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username.toLowerCase());
    }

    @Override
    public ResponseMessage save(User user) {
        Optional<User> user1=userRepository.findByUsername(user.getUsername().toLowerCase());
        if(user1.isPresent()){
            return new ResponseMessage(
                    user.getUsername().toLowerCase()+" already exists. Try with another email id.",
                    HttpStatus.BAD_REQUEST
            );
        }
        else{
            user.setUsername(user.getUsername().toLowerCase());
            user.setRoles(Arrays.asList(iRoleService.findOne(3)));
            user.setCreatedDate(new Date());
            user.setActive(false);
            user.setVerificationToken(new VerificationToken(
                    randUUIDToken(),
                    setTokenExpirationDate(EXPIRATION),
                    user));
            userRepository.save(user);
            return new ResponseMessage(
                    user.getUsername()+" has been successfully registered.",
                    HttpStatus.OK
            );
        }
    }

    @Override
    public ResponseMessage resendVerificationToken(String username) {
        Optional<User> optional=findByUsername(username.toLowerCase());
        if(optional.isPresent()){
            if(!optional.get().isActive()) {
                VerificationToken verificationToken = optional.get().getVerificationToken();
                verificationToken.setToken(randUUIDToken());
                verificationToken.setExpiryDate(setTokenExpirationDate(EXPIRATION));
                iVerificationTokenService.save(verificationToken);
                return new ResponseMessage(
                        "Verification Code has been send to your email : " + username.toLowerCase(),
                        HttpStatus.OK
                );
            }
            else{
                return new ResponseMessage(
                        "Account with username "+username.toLowerCase()+" has already been activated.",
                        HttpStatus.BAD_REQUEST
                );
            }
        }
        else {
            return new ResponseMessage(
                    "Account with username "+username.toLowerCase()+" doesn't exists.",
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @Override
    public ResponseMessage activateUser(String token) {
        Optional<VerificationToken> verificationToken=iVerificationTokenService.findByToken(token);
        if(verificationToken.isPresent()){
            if(!verificationToken.get().getUser().isActive()) {
                if (TokenExpirationUtils.isVerificationTokenExpired(verificationToken.get().getExpiryDate())) {
                    return new ResponseMessage(
                            "Token is expired.",
                            HttpStatus.BAD_REQUEST
                    );
                } else {
                    User user = verificationToken.get().getUser();
                    user.setActive(true);
                    userRepository.save(user);
                    return new ResponseMessage(
                            "Your account has been activated",
                            HttpStatus.OK
                    );
                }
            }
            else{
                return new ResponseMessage(
                        "Your account has already been activated.",
                        HttpStatus.BAD_REQUEST
                );
            }
        }
        else{
            return new ResponseMessage(
                    "Invalid token.",
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @Override
    public ResponseMessage sendResetPasswordToken(String username) {
        Optional<User> optional=findByUsername(username.toLowerCase());

        if(optional.isPresent()){
            User user=optional.get();
            if(user.isActive()) {
                user.setPasswordResetToken(
                        new PasswordResetToken(
                                randUUIDToken(),
                                setTokenExpirationDate(EXPIRATION),
                                user)
                );
                userRepository.save(user);
                //#TODO
                //send email for password reset too and clicking that link would(not entering token).
                return new ResponseMessage(
                        "Password reset Token has been send to email : " + user.getUsername().toLowerCase() + ".",
                        HttpStatus.OK
                );
            }
            else{
                return new ResponseMessage(
                        "Account with username "+username.toLowerCase()+" is not active.",
                        HttpStatus.BAD_REQUEST
                );
            }
        }
        else{
            return new ResponseMessage(
                    "Account with username "+username.toLowerCase()+" doesn't exists.",
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @Override
    public ResponseMessage resetUserPassword(String token, String username,String password) {
        Optional<User> optional=findByUsername(username.toLowerCase());

        if(optional.isPresent()){
            if(optional.get().isActive()) {
                Optional<PasswordResetToken> tokenOptional = iPasswordResetService.findByToken(token);
                if (tokenOptional.isPresent()) {
                    if (!TokenExpirationUtils.isVerificationTokenExpired(tokenOptional.get().getExpiryDate())) {
                        if (Objects.equals(optional.get().getId(), tokenOptional.get().getUser().getId())) {
                            //resetting password
                            User user = optional.get();
                            user.setPassword(password);
                            userRepository.save(user);

                            return new ResponseMessage(
                                    "Password is reset.",
                                    HttpStatus.OK
                            );
                        } else {
                            return new ResponseMessage(
                                    "Invalid token.",
                                    HttpStatus.BAD_REQUEST
                            );
                        }
                    } else {
                        return new ResponseMessage(
                                "Expired token.",
                                HttpStatus.BAD_REQUEST
                        );
                    }
                } else {
                    return new ResponseMessage(
                            "Invalid token.",
                            HttpStatus.BAD_REQUEST
                    );
                }
            }
            else{
                return new ResponseMessage(
                        "Account with username "+username.toLowerCase()+" is not active.",
                        HttpStatus.BAD_REQUEST
                );
            }
        }
        else{
            return new ResponseMessage(
                    "Account with username "+username.toLowerCase()+" doesn't exists.",
                    HttpStatus.NOT_FOUND
            );
        }
    }

    private String randUUIDToken(){
        String randUid= UUID.randomUUID().toString();
        while(iVerificationTokenService.findByToken(randUid)==null){
            randUid=UUID.randomUUID().toString();
        }
        return randUid;
    }
}
