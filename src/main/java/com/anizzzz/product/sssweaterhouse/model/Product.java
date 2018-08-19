package com.anizzzz.product.sssweaterhouse.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
@Table(name="product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productCode;
    private String type;
    private double price;
    private boolean sale;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    private Date createdDate;

    @ManyToMany
    @JoinTable(name="product_productsize",
            joinColumns = @JoinColumn(name="product_id"),
            inverseJoinColumns = @JoinColumn(name="size_id"))
    private List<ProductSize> size;

    @OneToMany(targetEntity = ProductInfo.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ProductInfo> productInfos;

    public Product(String productCode,
                   String type,
                   double price,
                   boolean sale,
                   Date createdDate,
                   List<ProductSize> size,
                   List<ProductInfo> productInfos){
        this.productCode=productCode;
        this.type=type;
        this.price=price;
        this.sale=sale;
        this.createdDate=createdDate;
        this.size=size;
        this.productInfos=productInfos;
    }

    public Product(String productCode,
                   String type,
                   double price,
                   boolean sale,
                   Date createdDate,
                   List<ProductInfo> productInfos){
        this.productCode=productCode;
        this.type=type;
        this.price=price;
        this.sale=sale;
        this.createdDate=createdDate;
        this.productInfos=productInfos;
    }
}
