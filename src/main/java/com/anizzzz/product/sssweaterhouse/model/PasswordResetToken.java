package com.anizzzz.product.sssweaterhouse.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "password_reset_token")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetToken{
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid",
            strategy = "uuid")
    private String id;
    private String token;

    @OneToOne(targetEntity = Users.class, fetch = FetchType.LAZY)
    @JoinColumn(name="users_id")
    private Users users;

    @Column(name = "expiry_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy hh:mm:ss")
    private Date expiryDate;

    public PasswordResetToken(String token, Date expiryDate, Users users){
        this.token=token;
        this.expiryDate=expiryDate;
        this.users = users;
    }
}
