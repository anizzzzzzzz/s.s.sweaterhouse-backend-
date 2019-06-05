package com.anizzzz.product.sssweaterhouse.service.user.impl;

import com.anizzzz.product.sssweaterhouse.constant.UserRole;
import com.anizzzz.product.sssweaterhouse.dto.ResponseMessage;
import com.anizzzz.product.sssweaterhouse.exceptionHandling.exceptions.DuplicateUserNameException;
import com.anizzzz.product.sssweaterhouse.exceptionHandling.exceptions.EmailException;
import com.anizzzz.product.sssweaterhouse.model.PasswordResetToken;
import com.anizzzz.product.sssweaterhouse.model.Role;
import com.anizzzz.product.sssweaterhouse.model.Users;
import com.anizzzz.product.sssweaterhouse.model.VerificationToken;
import com.anizzzz.product.sssweaterhouse.repository.user.UserRepository;
import com.anizzzz.product.sssweaterhouse.service.user.*;
import com.anizzzz.product.sssweaterhouse.utils.TokenExpirationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.anizzzz.product.sssweaterhouse.utils.TokenExpirationUtils.setTokenExpirationDate;

@Service
public class UserService implements IUserService {
    @Value("${frontend.port}")
    private String frontPort;

    private Logger logger= LoggerFactory.getLogger(UserService.class);

    private static final int EXPIRATION = 60*24;
    private final UserRepository userRepository;
    private final IRoleService iRoleService;
    private final IVerificationTokenService iVerificationTokenService;
    private final IPasswordResetService iPasswordResetService;
    private final IEmailService iEmailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       IRoleService iRoleService,
                       IVerificationTokenService iVerificationTokenService,
                       IPasswordResetService iPasswordResetService,
                       IEmailService iEmailService,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.iRoleService = iRoleService;
        this.iVerificationTokenService = iVerificationTokenService;
        this.iPasswordResetService = iPasswordResetService;
        this.iEmailService = iEmailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Optional<Users> findByUsername(String username) {
        return userRepository.findByUsername(username.toLowerCase());
    }

    @Override
    public ResponseMessage saveUser(Users users, HttpServletRequest request) {
        Optional<Users> user1=userRepository.findByUsername(users.getUsername().toLowerCase());
        if(user1.isPresent()){
            return new ResponseMessage(
                    users.getUsername().toLowerCase()+" already exists. Try with another email id.",
                    HttpStatus.BAD_REQUEST
            );
        }
        else{
            users.setUsername(users.getUsername().toLowerCase());
            users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
            users.setRoles(Collections.singletonList(iRoleService.findByName(UserRole.USER.toString())));
            users.setCreatedDate(new Date());
            users.setActive(false);
            users.setPasswordStamp(new Date());
            users.setVerificationToken(new VerificationToken(
                    randUUIDToken(),
                    setTokenExpirationDate(EXPIRATION),
                    users));
            userRepository.save(users);

            String subject = "Account Confirmation";
            StringBuilder body=new StringBuilder();
            body.append("Welcome,<br/>Your users account with the e-mail address <strong>")
                .append(users.getUsername())
                .append("</strong> has been created.<br/>")
                .append("Please follow the link below to activate your account. The link will remain valid for 24 hrs. <br/>")
                .append("<a href=\"")
                .append(getServerAddress(request)).append("/users/activate-users?token=")
                .append(users.getVerificationToken().getToken())
                .append("\">Activate account</a><br/>");

            try {
                iEmailService.sendMail(users.getUsername(),subject, body.toString());
            } catch (Exception e) {
                logger.error("Error sending email: "+e.getMessage());
                throw new EmailException("Cannot Send Email to "+ users.getUsername(), e);
            }
            return new ResponseMessage(
                    users.getUsername()+" has been successfully registered.",
                    HttpStatus.OK
            );
        }
    }

    @Override
    public ResponseMessage saveAdmin(Users users) {
        Optional<Users> optional = userRepository.findByUsername(users.getUsername());
        if(optional.isPresent()){
            return new ResponseMessage(
                    users.getUsername().toLowerCase()+" already exists. Try with another email id.",
                    HttpStatus.BAD_REQUEST
            );
        }
        else{
            users.setUsername(users.getUsername().toLowerCase());
            users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
            if(users.getRoles().size()==0){
                List<Role> roles = new ArrayList<>();
                roles.add(iRoleService.findByName(UserRole.ADMIN.toString()));
                roles.add(iRoleService.findByName(UserRole.USER.toString()));
                users.setRoles(roles);
            }
            users.setCreatedDate(new Date());
            users.setActive(true);
            users.setActivatedDate(new Date());
            users.setPasswordStamp(new Date());
            userRepository.save(users);
            return new ResponseMessage(
                    users.getUsername()+" has been successfully registered as admin.",
                    HttpStatus.OK
            );
        }
    }

    @Override
    public ResponseMessage resendVerificationToken(String username, HttpServletRequest request) {
        Optional<Users> optional=findByUsername(username.toLowerCase());
        if(optional.isPresent()){
            if(!optional.get().isActive()) {
                VerificationToken verificationToken = optional.get().getVerificationToken();
                verificationToken.setToken(randUUIDToken());
                verificationToken.setExpiryDate(setTokenExpirationDate(EXPIRATION));
                iVerificationTokenService.save(verificationToken);

                String subject = "Resend Verification Token";
                StringBuilder body=new StringBuilder();
                body.append("Hi "+optional.get().getFirstname()+",<br/>")
                        .append("Please follow the link below to activate your account. The link will remain valid for 24 hrs. <br/>")
                        .append("<a href=\"")
                        .append(getServerAddress(request)).append("/users/activate-users?token=")
                        .append(optional.get().getVerificationToken().getToken())
                        .append("\">Activate account</a><br/>");

                try {
                    iEmailService.sendMail(optional.get().getUsername(),subject, body.toString());
                } catch (Exception e) {
                    logger.error("Error sending email: "+e.getMessage());
                    throw new EmailException("Cannot Send Email to "+optional.get().getUsername(), e);
                }

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

    /*@Override
    public ResponseMessage activateUser(String token) {
        Optional<VerificationToken> verificationToken=iVerificationTokenService.findByToken(token);
        if(verificationToken.isPresent()){
            if(!verificationToken.get().getUsers().isActive()) {
                if (TokenExpirationUtils.isVerificationTokenExpired(verificationToken.get().getExpiryDate())) {
                    return new ResponseMessage(
                            "Token is expired.",
                            HttpStatus.BAD_REQUEST
                    );
                } else {
                    Users users = verificationToken.get().getUsers();
                    users.setActive(true);
                    users.setActivatedDate(new Date());
                    userRepository.save(users);
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
    }*/

    @Override
    public ResponseMessage activateUser(String token) {
        Optional<Users> usersOptional = userRepository.findByVerificationToken_Token(token);
        if(usersOptional.isPresent()){
            Users users = usersOptional.get();
            if (!users.isActive()){
                if (TokenExpirationUtils.isVerificationTokenExpired(users.getVerificationToken().getExpiryDate())) {
                    return new ResponseMessage(
                            "Token is expired.",
                            HttpStatus.BAD_REQUEST
                    );
                }
                else {
                    users.setActive(true);
                    users.setActivatedDate(new Date());
                    userRepository.save(users);
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
    public ResponseMessage sendResetPasswordToken(String username, HttpServletRequest request) {
        Optional<Users> optional=findByUsername(username.toLowerCase());

        if(optional.isPresent()){
            Users users =optional.get();
            if(users.isActive()) {
                PasswordResetToken passwordResetToken = users.getPasswordResetToken();
                if(passwordResetToken!=null) {
                    passwordResetToken.setToken(randUUIDToken());
                    passwordResetToken.setExpiryDate(setTokenExpirationDate(EXPIRATION));
                    iPasswordResetService.save(passwordResetToken);
                }
                else{
                    users.setPasswordResetToken(
                            new PasswordResetToken(
                                    randUUIDToken(),
                                    setTokenExpirationDate(EXPIRATION),
                                    users)
                    );
                    userRepository.save(users);
                }
                //#TODO
                //send email for password reset too and clicking that link would(not entering token).
                return new ResponseMessage(
                        "Password reset Token has been send to email : " + users.getUsername().toLowerCase() + ".",
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
        Optional<Users> optional=findByUsername(username.toLowerCase());

        if(optional.isPresent()){
            if(optional.get().isActive()) {
                Optional<PasswordResetToken> tokenOptional = iPasswordResetService.findByToken(token);
                if (tokenOptional.isPresent()) {
                    if (!TokenExpirationUtils.isVerificationTokenExpired(tokenOptional.get().getExpiryDate())) {
                        if (Objects.equals(optional.get().getId(), tokenOptional.get().getUsers().getId())) {
                            //resetting password
                            Users users = optional.get();
                            users.setPassword(bCryptPasswordEncoder.encode(password));
                            users.setPasswordStamp(new Date());
                            userRepository.save(users);

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

    //----------------------------- Social Login -----------------------------------------
    @Override
    public Users findByUserId(String userId) {
        Optional<Users> user = userRepository.findByUserId(userId);
        return user.orElse(null);
    }

    @Override
    public Users findByUserIdAndAccountId(String userId, String accountId) {
        Optional<Users> user = userRepository.findByUserIdAndAccountId(userId, accountId);
        return user.orElse(null);
    }

    @Override
    public String findAvailableUserName(String userName_prefix, String accountProviderId) {
        String username=userName_prefix+"@"+accountProviderId+".com";
        Optional<Users> user = userRepository.findByUsername(username);

        if(!user.isPresent()){
            return username;
        }

        int i=0;
        while(true){
            username=userName_prefix+"_"+(i++)+"@"+accountProviderId+".com";
            user=findByUsername(username);
            if(!user.isPresent()){
                return username;
            }
        }
    }

    @Override
    public Users createAppUser(Connection<?> connection) throws DuplicateUserNameException {
        try{
            ConnectionKey key = connection.getKey();
            logger.info("Key = ( "+key.getProviderId()+" , "+key.getProviderUserId()+" )");

            UserProfile userProfile=connection.fetchUserProfile();

            Users appUsers =findByUserIdAndAccountId(key.getProviderUserId(),key.getProviderId());

            if(appUsers !=null){
                return appUsers;
            }

            String username=userProfile.getEmail();

            if(username==null || username.length() == 0){
                String userName_prefix = userProfile.getFirstName().trim().toLowerCase()
                        +"_"+userProfile.getLastName().trim().toLowerCase();
                username=findAvailableUserName(userName_prefix, connection.getKey().getProviderId());
            }
            else{
                if (findByUsername(username).isPresent()){
                    throw new DuplicateUserNameException();
                }
            }

            appUsers = new Users(
                    userProfile.getFirstName(),
                    userProfile.getLastName(),
                    username,
                    bCryptPasswordEncoder.encode(UUID.randomUUID().toString().substring(0,10)),
                    connection.getKey().getProviderUserId(),
                    connection.getKey().getProviderId(),
                    new Date(),
                    new Date(),
                    true,
                    new Date(),
                    Collections.singletonList(iRoleService.findByName(UserRole.USER.toString()))
            );
            userRepository.save(appUsers);
            return appUsers;
        }
        catch (DuplicateUserNameException ex){
            logger.error(ex.getMessage());
        }
        return null;
    }

    //----------------------------- Social Login -----------------------------------------

    private String randUUIDToken(){
        String randUid= UUID.randomUUID().toString();
        while(iVerificationTokenService.findByToken(randUid)==null){
            randUid=UUID.randomUUID().toString();
        }
        return randUid;
    }

    private String getServerAddress(HttpServletRequest request){
        //For Frontend
//        return request.getScheme() + "://" + request.getServerName() + ":" + frontPort;
        //For Server
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    }
}
