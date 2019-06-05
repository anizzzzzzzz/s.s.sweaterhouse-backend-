package com.anizzzz.product.sssweaterhouse.model;

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
@NoArgsConstructor
public class ProductInfo {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid",
            strategy = "uuid")
    private String id;

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
