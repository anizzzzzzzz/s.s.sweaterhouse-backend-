package com.anizzzz.product.sssweaterhouse.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="role")
@Data
@AllArgsConstructor
public class Role implements Serializable {
    private static final long serialVersionUID =243509823490109483L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Role(){}

    public Role(String name){
        this.name=name;
    }
}
