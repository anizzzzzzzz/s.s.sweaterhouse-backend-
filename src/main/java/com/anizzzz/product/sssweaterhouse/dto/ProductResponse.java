package com.anizzzz.product.sssweaterhouse.dto;

import com.anizzzz.product.sssweaterhouse.model.product.ProductSize;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ProductResponse {
    private String name;
    private String productCode;
    private String type;
    private double price;
    private List<ProductSize> size;
    private byte[] image;
    private String imageType;
    private List<HashMap<String,Object>> images;
    private boolean sale;

    public ProductResponse(String name,
                           String productCode,
                           String type,
                           double price,
                           List<ProductSize> size,
                           byte[] image,
                           String imageType,
                           boolean sale){
        this.name=name;
        this.productCode=productCode;
        this.type=type;
        this.price=price;
        this.size=size;
        this.image=image;
        this.imageType=imageType;
        this.sale=sale;
    }

    public ProductResponse(String name,
                           String productCode,
                           String type,
                           double price,
                           List<ProductSize> size,
                           List<HashMap<String,Object>> images
                           ){
        this.name=name;
        this.productCode=productCode;
        this.type=type;
        this.price=price;
        this.size=size;
        this.images=images;
    }
}
