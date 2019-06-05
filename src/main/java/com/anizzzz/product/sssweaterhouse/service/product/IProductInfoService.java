package com.anizzzz.product.sssweaterhouse.service.product;

import com.anizzzz.product.sssweaterhouse.model.product.ProductInfo;

import java.util.Optional;

public interface IProductInfoService {
    Optional<ProductInfo> findByName(String name);
}
