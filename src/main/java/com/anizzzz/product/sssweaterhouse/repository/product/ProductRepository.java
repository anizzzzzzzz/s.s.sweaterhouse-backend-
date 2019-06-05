package com.anizzzz.product.sssweaterhouse.repository.product;

import com.anizzzz.product.sssweaterhouse.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {
    Optional<Product> findByProductCode(String productCode);

    Page<Product> findAllByOrderByCreatedDateDesc(Pageable pageable);

    Page<Product> findAllBySaleOrderByCreatedDateDesc(Pageable pageable, boolean sale);

    List<Product> findAllByType(String type);

    Page<Product> findAllByTypeOrderByCreatedDateDesc(Pageable pageable,String type);

    Page<Product> findAllBySaleAndTypeOrderByCreatedDateDesc(Pageable pageable, boolean sale, String type);

    Page<Product> findAllByTypeAndPriceBetweenOrderByCreatedDateDesc(Pageable pageable, String type, double priceBegin, double priceEnd);

    Page<Product> findAllByTypeAndSaleAndPriceBetweenOrderByCreatedDateDesc(Pageable pageable, String type, boolean sale, double priceBegin, double priceEnd);
}
