package com.anizzzz.product.sssweaterhouse.model;

import com.anizzzz.product.sssweaterhouse.annotation.ValidEmail;
import com.anizzzz.product.sssweaterhouse.annotation.ValidPassword;
import com.anizzzz.product.sssweaterhouse.view.View;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @JsonView({View.ShowUser.class})
    private String firstname;
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
    @ValidPassword
    private String password;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    private Date createdDate;
    private boolean active;

    @ManyToMany
    @JoinTable(name="user_role",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    private List<Role> roles;

    @OneToOne(targetEntity = VerificationToken.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "verification_token_id")
    private VerificationToken verificationToken;

    @OneToOne(targetEntity = PasswordResetToken.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "password_reset_id")
    private PasswordResetToken passwordResetToken;

}
