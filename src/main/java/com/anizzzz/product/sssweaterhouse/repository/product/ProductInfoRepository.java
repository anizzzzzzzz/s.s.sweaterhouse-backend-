package com.anizzzz.product.sssweaterhouse.repository.product;

import com.anizzzz.product.sssweaterhouse.model.product.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {
    Optional<ProductInfo> findByName(String name);

    @Query(value = "DELETE FROM product_info where product_id IS NULL", nativeQuery = true)
    @Modifying
    @Transactional
    void deleteAllIfProductIdIsNull();

}
