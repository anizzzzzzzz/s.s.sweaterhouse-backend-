package com.anizzzz.product.sssweaterhouse.service.product.impl;

import com.anizzzz.product.sssweaterhouse.model.product.ProductInfo;
import com.anizzzz.product.sssweaterhouse.repository.product.ProductInfoRepository;
import com.anizzzz.product.sssweaterhouse.service.product.IProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductInfoService implements IProductInfoService {
    private final ProductInfoRepository productInfoRepository;

    @Autowired
    public ProductInfoService(ProductInfoRepository productInfoRepository) {
        this.productInfoRepository = productInfoRepository;
    }

    @Override
    public Optional<ProductInfo> findByName(String name) {
        return productInfoRepository.findByName(name);
    }

    @Override
    public Optional<ProductInfo> findById(String id) {
        return productInfoRepository.findById(id);
    }

    @Override
    public void deleteAllIfProductIdIsNull() {
        productInfoRepository.deleteAllIfProductIdIsNull();
    }
}
