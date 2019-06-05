package com.anizzzz.product.sssweaterhouse.model.comment;

import com.anizzzz.product.sssweaterhouse.model.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comment")
@Data
@AllArgsConstructor
public class Comment {
    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    private String id;

    @Column(columnDefinition = "text")
    private String comment;

    private int rate;

    @Column(name = "created_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    private Date createdDate;
    @Column(name = "updated_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    private Date updatedDate;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(){}

    public Comment(String comment, int rate, Date createdDate, User user){
        this.comment = comment;
        this.rate = rate;
        this.createdDate = createdDate;
        this.updatedDate = createdDate;
        this.user = user;
    }
}
