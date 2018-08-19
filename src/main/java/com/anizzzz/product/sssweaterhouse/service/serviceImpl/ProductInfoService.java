package com.anizzzz.product.sssweaterhouse.service.serviceImpl;

import com.anizzzz.product.sssweaterhouse.model.ProductInfo;
import com.anizzzz.product.sssweaterhouse.repository.ProductInfoRepository;
import com.anizzzz.product.sssweaterhouse.service.IProductInfoService;
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
}
