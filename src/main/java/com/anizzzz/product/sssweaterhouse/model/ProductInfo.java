package com.anizzzz.product.sssweaterhouse.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity
@Table(name = "product_info")
public class ProductInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String extension;
    private String type;
    private String location;
    private boolean highlight;

    public ProductInfo(String name, String extension,String type, String location, boolean highlight){
        this.name=name;
        this.extension=extension;
        this.type=type;
        this.location=location;
        this.highlight=highlight;
    }
}
