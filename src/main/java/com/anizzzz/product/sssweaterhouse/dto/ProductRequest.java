package com.anizzzz.product.sssweaterhouse.dto;

import java.util.ArrayList;
import java.util.List;

public class ProductRequest {
    private String id="";
    private String productCode="";
    private String name="";
    private double price=0;
    private boolean sale;
    private List<String> size = new ArrayList<>();
    // not needed for now.
    // private List<ProductInfo> productInfos = new ArrayList<>();
    private List<String> deletedImagesId = new ArrayList<>();
    private String selectedImage="";

    public ProductRequest() {
    }

    public ProductRequest(String id, String productCode, String name, double price, boolean sale, List<String> size, List<String> deletedImagesId, String selectedImage) {
        this.id = id;
        this.productCode = productCode;
        this.name = name;
        this.price = price;
        this.sale = sale;
        this.size = size;
        this.deletedImagesId = deletedImagesId;
        this.selectedImage = selectedImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<String> getSize() {
        return size;
    }

    public void setSize(List<String> size) {
        this.size = size;
    }

    public List<String> getDeletedImagesId() {
        return deletedImagesId;
    }

    public void setDeletedImagesId(List<String> deletedImagesId) {
        this.deletedImagesId = deletedImagesId;
    }

    public String getSelectedImage() {
        return selectedImage;
    }

    public void setSelectedImage(String selectedImage) {
        this.selectedImage = selectedImage;
    }
}
