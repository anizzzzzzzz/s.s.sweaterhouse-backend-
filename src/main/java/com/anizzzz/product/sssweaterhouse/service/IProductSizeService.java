package com.anizzzz.product.sssweaterhouse.service;

import com.anizzzz.product.sssweaterhouse.model.ProductSize;

import java.util.List;

public interface IProductSizeService {
    ProductSize findOne(int id);

    void saveAll(List<ProductSize> productSizes);

    List<ProductSize> findAll();

    ProductSize findBySize(String size);
}
