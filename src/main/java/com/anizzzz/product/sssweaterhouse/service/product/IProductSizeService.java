package com.anizzzz.product.sssweaterhouse.service.product;

import com.anizzzz.product.sssweaterhouse.model.product.ProductSize;

import java.util.List;

public interface IProductSizeService {
    ProductSize findOne(Long id);

    void saveAll(List<ProductSize> productSizes);

    List<ProductSize> findAll();

    ProductSize findBySize(String size);
}
