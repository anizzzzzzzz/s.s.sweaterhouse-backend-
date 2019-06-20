package com.anizzzz.product.sssweaterhouse.model.product;

import com.anizzzz.product.sssweaterhouse.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product_info")
@Data
@AllArgsConstructor
public class ProductInfo {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid",
            strategy = "uuid")
    @JsonView({View.Product.class})
    private String id;

    @JsonView({View.Product.class})
    private String name;
    private String extension;
    private String type;
    @JsonView({View.ProductPagination.class, View.Product.class})
    private String location;
    @JsonView({View.ProductPagination.class, View.Product.class})
    private boolean highlight;

    public ProductInfo(){}

    public ProductInfo(String name, String extension,String type, String location, boolean highlight){
        this.name=name;
        this.extension=extension;
        this.type=type;
        this.location=location;
        this.highlight=highlight;
    }
}
