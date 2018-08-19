package com.anizzzz.product.sssweaterhouse.dto;

import com.anizzzz.product.sssweaterhouse.model.ProductSize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private String productCode;
    private String type;
    private double price;
    private List<ProductSize> size;
    private byte[] image;
    private String imageType;
}
