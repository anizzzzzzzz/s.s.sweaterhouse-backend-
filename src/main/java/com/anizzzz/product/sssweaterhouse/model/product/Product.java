package com.anizzzz.product.sssweaterhouse.model.product;

import com.anizzzz.product.sssweaterhouse.model.comment.Comment;
import com.anizzzz.product.sssweaterhouse.view.View;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="product")
public class Product {
    @Id
    @GenericGenerator(name = "system_uuid", strategy = "uuid")
    @GeneratedValue(generator = "system_uuid")
    @JsonView({View.ProductPagination.class, View.Product.class})
    private String id;

    @JsonView({View.ProductPagination.class, View.Product.class})
    private String name;
    @Column(name = "product_code")
    @JsonView({View.ProductPagination.class, View.Product.class})
    private String productCode;
    @JsonView({View.ProductPagination.class, View.Product.class})
    private String type;
    @JsonView({View.ProductPagination.class, View.Product.class})
    private double price;
    @JsonView({View.ProductPagination.class, View.Product.class})
    private boolean sale;

    @Column(name = "created_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    @JsonView({View.ProductPagination.class, View.Product.class})
    private Date createdDate;

    @Column(name = "updated_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    private Date updatedDate;


    @ManyToMany(targetEntity = ProductSize.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="product_productsize",
            joinColumns = @JoinColumn(name="product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="size_id", referencedColumnName = "id"))
    @JsonView({View.Product.class})
    private List<ProductSize> size = new ArrayList<>();

    @OneToMany(targetEntity = ProductInfo.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    @JsonView({View.ProductPagination.class, View.Product.class})
    private List<ProductInfo> productInfos = new ArrayList<>();

    @OneToMany(targetEntity = Comment.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @OrderBy("createdDate DESC")
    @JsonView({View.Product.class})
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

    public Product(String name, String productCode, String type, double price, boolean sale, Date createdDate, Date updatedDate, List<ProductSize> size, List<ProductInfo> productInfos, List<Comment> comments) {
        this.name = name;
        this.productCode = productCode;
        this.type = type;
        this.price = price;
        this.sale = sale;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.size = size;
        this.productInfos = productInfos;
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isSale() {
        return sale;
    }

    public void setSale(boolean sale) {
        this.sale = sale;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public List<ProductSize> getSize() {
        return size;
    }

    public void setSize(List<ProductSize> size) {
        this.size = size;
    }

    public List<ProductInfo> getProductInfos() {
        return productInfos;
    }

    public void setProductInfos(List<ProductInfo> productInfos) {
        this.productInfos = productInfos;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
