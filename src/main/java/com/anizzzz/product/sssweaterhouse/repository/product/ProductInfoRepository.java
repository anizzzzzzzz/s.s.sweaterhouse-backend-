package com.anizzzz.product.sssweaterhouse.repository.product;

import com.anizzzz.product.sssweaterhouse.model.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {
    Optional<ProductInfo> findByName(String name);

}