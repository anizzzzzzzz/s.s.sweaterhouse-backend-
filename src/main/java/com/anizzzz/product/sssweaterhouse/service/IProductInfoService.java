package com.anizzzz.product.sssweaterhouse.service;

import com.anizzzz.product.sssweaterhouse.model.ProductInfo;

import java.util.Optional;

public interface IProductInfoService {
    Optional<ProductInfo> findByName(String name);
}
