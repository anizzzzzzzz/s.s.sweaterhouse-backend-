package com.anizzzz.product.sssweaterhouse.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GenericGenerator(name = "system_uuid", strategy = "uuid")
    @GeneratedValue(generator = "system_uuid")
    private String id;

    private String name;
    @Column(name = "product_code")
    private String productCode;
    private String type;
    private double price;
    private boolean sale;

    @Column(name = "created_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    private Date createdDate;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToMany(targetEntity = ProductSize.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="product_productsize",
            joinColumns = @JoinColumn(name="product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="size_id", referencedColumnName = "id"))
    private List<ProductSize> size;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(targetEntity = ProductInfo.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private List<ProductInfo> productInfos = new ArrayList<>();

    public Product(String name,
                   String productCode,
                   String type,
                   double price,
                   boolean sale,
                   Date createdDate,
                   List<ProductSize> size,
                   List<ProductInfo> productInfos){
        this.name=name;
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
