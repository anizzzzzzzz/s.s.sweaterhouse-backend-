package com.anizzzz.product.sssweaterhouse.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "password_reset_token")
@Data
@AllArgsConstructor
public class PasswordResetToken{
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid",
            strategy = "uuid")
    private String id;
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name="users_id")
    private User user;

    @Column(name = "expiry_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy hh:mm:ss")
    private Date expiryDate;

    public PasswordResetToken(){}

    public PasswordResetToken(String token, Date expiryDate, User user){
        this.token=token;
        this.expiryDate=expiryDate;
        this.user = user;
    }
}
