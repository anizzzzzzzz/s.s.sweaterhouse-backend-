package com.anizzzz.product.sssweaterhouse.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="role")
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
