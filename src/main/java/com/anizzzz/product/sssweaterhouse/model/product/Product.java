package com.anizzzz.product.sssweaterhouse.model.product;

import com.anizzzz.product.sssweaterhouse.model.comment.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="product")
@Data
@AllArgsConstructor
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

    @Column(name = "updated_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    private Date updatedDate;


    @ManyToMany(targetEntity = ProductSize.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="product_productsize",
            joinColumns = @JoinColumn(name="product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="size_id", referencedColumnName = "id"))
    private List<ProductSize> size = new ArrayList<>();

    @OneToMany(targetEntity = ProductInfo.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private List<ProductInfo> productInfos = new ArrayList<>();

    @OneToMany(targetEntity = Comment.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @OrderBy("createdDate DESC")
    private List<Comment> comments = new ArrayList<>();

    public Product(){}

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
        this.updatedDate = createdDate;
        this.size=size;
        this.productInfos=productInfos;
    }
}
