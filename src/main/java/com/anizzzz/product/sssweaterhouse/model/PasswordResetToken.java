package com.anizzzz.product.sssweaterhouse.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@Entity
@Table(name = "password_reset_token")
public class PasswordResetToken implements Serializable {
    private static final long serialVersionUID =452346234662321L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name="user_id")
    private User user;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy hh:mm:ss")
    private Date expiryDate;

    public PasswordResetToken(String token, Date expiryDate, User user){
        this.token=token;
        this.expiryDate=expiryDate;
        this.user=user;
    }
}
