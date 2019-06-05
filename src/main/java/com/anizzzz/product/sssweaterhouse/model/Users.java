package com.anizzzz.product.sssweaterhouse.model;

import com.anizzzz.product.sssweaterhouse.annotation.ValidEmail;
import com.anizzzz.product.sssweaterhouse.view.View;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users{
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid",
            strategy = "uuid")
    private String id;

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

    @Column(name = "user_id")
    private String userId;
    @Column(name = "account_id")
    private String accountId;

    @Column(name = "created_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    private Date createdDate;
    @Column(name = "activated_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    private Date activatedDate;
    private boolean active;
    @Column(name = "password_stamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    private Date passwordStamp;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToMany(targetEntity = Role.class, fetch = FetchType.LAZY)
    @JoinTable(name="users_role",
            joinColumns = @JoinColumn(name="user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="role_id", referencedColumnName = "id"))
    private List<Role> roles;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(targetEntity = VerificationToken.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "verification_token_id")
    private VerificationToken verificationToken;

    @OneToOne(targetEntity = PasswordResetToken.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "reset_password_token_id")
    private PasswordResetToken passwordResetToken;

    public Users(String firstname,
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

    public Users(String firstname,
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

    public Users(String firstname,
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

    public Users(String firstname,
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

    public Users(String firstname,
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
}
