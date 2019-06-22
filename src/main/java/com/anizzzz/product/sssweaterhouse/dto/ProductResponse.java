package com.anizzzz.product.sssweaterhouse.dto;

import com.anizzzz.product.sssweaterhouse.model.product.ProductSize;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.List;

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

    public ProductResponse(){}

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

    public List<ProductSize> getSize() {
        return size;
    }

    public void setSize(List<ProductSize> size) {
        this.size = size;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public List<HashMap<String, Object>> getImages() {
        return images;
    }

    public void setImages(List<HashMap<String, Object>> images) {
        this.images = images;
    }

    public boolean isSale() {
        return sale;
    }

    public void setSale(boolean sale) {
        this.sale = sale;
    }
}
