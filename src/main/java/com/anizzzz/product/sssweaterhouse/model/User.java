package com.anizzzz.product.sssweaterhouse.model;

import com.anizzzz.product.sssweaterhouse.annotation.ValidEmail;
import com.anizzzz.product.sssweaterhouse.view.View;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = 87937612836871263L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @JsonView({View.ShowUser.class})
    private String firstname;
    @JsonView({View.ShowUser.class})
    private String middlename;
    @NotNull
    @NotEmpty
    @JsonView({View.ShowUser.class})
    private String lastname;

    @NotNull
    @NotEmpty
    @ValidEmail
    @JsonView({View.ShowUser.class})
    private String username;

    @NotNull
    @NotEmpty
    private String password;

    private String userId;
    private String accountId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    private Date createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    private Date activatedDate;
    private boolean active;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    private Date passwordStamp;

    @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
    @JoinTable(name="user_role",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    private List<Role> roles;

    @OneToOne(targetEntity = VerificationToken.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "verification_token_id")
    private VerificationToken verificationToken;

    @OneToOne(targetEntity = PasswordResetToken.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "reset_password_token_id")
    private PasswordResetToken passwordResetToken;

    public User(){}

    public User(String firstname,
                String lastname,
                String username,
                String password,
                List<Role> roles){
        this.firstname=firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public User(String firstname,
                String lastname,
                String username,
                String password,
                List<Role> roles,
                Date createdDate,
                Date activatedDate,
                boolean active,
                Date passwordStamp){
        this.firstname=firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.createdDate = createdDate;
        this.activatedDate = activatedDate;
        this.active = active;
        this.passwordStamp = passwordStamp;
    }

    public User(String firstname,
                String lastname,
                String username,
                String password,
                String userId,
                String accountId,
                Date createdDate,
                Date activatedDate,
                boolean active,
                Date passwordStamp){
        this.firstname=firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.userId = userId;
        this.accountId = accountId;
        this.createdDate = createdDate;
        this.activatedDate = activatedDate;
        this.active = active;
        this.passwordStamp = passwordStamp;
    }

    public User(String firstname,
                String lastname,
                String username,
                String password,
                String userId,
                String accountId,
                Date createdDate,
                Date activatedDate,
                boolean active,
                Date passwordStamp,
                List<Role> roles){
        this.firstname=firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.userId = userId;
        this.accountId = accountId;
        this.createdDate = createdDate;
        this.activatedDate = activatedDate;
        this.active = active;
        this.passwordStamp = passwordStamp;
        this.roles=roles;
    }

    public User(String firstname,
                String middlename,
                String lastname,
                String username,
                String password,
                String userId,
                String accountId,
                Date createdDate,
                Date activatedDate,
                boolean active,
                Date passwordStamp,
                List<Role> roleList){
        this.firstname=firstname;
        this.middlename=middlename;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.userId = userId;
        this.accountId = accountId;
        this.createdDate = createdDate;
        this.activatedDate = activatedDate;
        this.active = active;
        this.passwordStamp = passwordStamp;
        this.roles = roleList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename(){return middlename;}

    public void setMiddlename(String middlename){ this.middlename = middlename;}

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getActivatedDate() {
        return activatedDate;
    }

    public void setActivatedDate(Date activatedDate) {
        this.activatedDate = activatedDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getPasswordStamp() {
        return passwordStamp;
    }

    public void setPasswordStamp(Date passwordStamp) {
        this.passwordStamp = passwordStamp;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public VerificationToken getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(VerificationToken verificationToken) {
        this.verificationToken = verificationToken;
    }

    public PasswordResetToken getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(PasswordResetToken passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }
}
