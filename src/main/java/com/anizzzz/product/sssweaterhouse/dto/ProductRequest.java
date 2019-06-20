package com.anizzzz.product.sssweaterhouse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
