package com.anizzzz.product.sssweaterhouse.model.product;

import com.anizzzz.product.sssweaterhouse.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product_info")
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

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isHighlight() {
        return highlight;
    }

    public void setHighlight(boolean highlight) {
        this.highlight = highlight;
    }
}
