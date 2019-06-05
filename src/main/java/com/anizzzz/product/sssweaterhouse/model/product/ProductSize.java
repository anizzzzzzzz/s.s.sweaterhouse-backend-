package com.anizzzz.product.sssweaterhouse.model.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "product_size")
@Data
@AllArgsConstructor
public class ProductSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String size;

    public ProductSize(){}

    public ProductSize(String size){
        this.size=size;
    }
}
