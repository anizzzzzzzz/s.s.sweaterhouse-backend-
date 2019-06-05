package com.anizzzz.product.sssweaterhouse.service.product.impl;

import com.anizzzz.product.sssweaterhouse.model.product.ProductSize;
import com.anizzzz.product.sssweaterhouse.repository.product.ProductSizeRepository;
import com.anizzzz.product.sssweaterhouse.service.product.IProductSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductSizeService implements IProductSizeService {
    private final ProductSizeRepository productSizeRepository;

    @Autowired
    public ProductSizeService(ProductSizeRepository productSizeRepository) {
        this.productSizeRepository = productSizeRepository;
    }

    @Override
    public ProductSize findOne(Long id) {
        Optional<ProductSize> size= productSizeRepository.findById(id);

        return size.orElse(null);
    }

    @Override
    public void saveAll(List<ProductSize> productSizes) {
        productSizeRepository.saveAll(productSizes);
    }

    @Override
    public List<ProductSize> findAll() {
        return productSizeRepository.findAll();
    }

    @Override
    public ProductSize findBySize(String size) {
        return productSizeRepository.findBySize(size);
    }
}
