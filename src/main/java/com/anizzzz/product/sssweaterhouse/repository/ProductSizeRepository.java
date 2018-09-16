package com.anizzzz.product.sssweaterhouse.repository;

import com.anizzzz.product.sssweaterhouse.model.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSizeRepository extends JpaRepository<ProductSize,Long> {
    ProductSize findBySize(String size);
}
