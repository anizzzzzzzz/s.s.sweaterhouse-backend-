package com.anizzzz.product.sssweaterhouse.repository.product;

import com.anizzzz.product.sssweaterhouse.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {
    Optional<Product> findByIdAndProductCode(String id, String productCode);

    Page<Product> findAllBySale(Pageable pageable, boolean sale);

    List<Product> findAllByType(String type);

    Page<Product> findAllByType(Pageable pageable,String type);

    Page<Product> findAllBySaleAndType(Pageable pageable, boolean sale, String type);

    Page<Product> findAllByTypeAndPriceBetween(Pageable pageable, String type, double priceBegin, double priceEnd);

    Page<Product> findAllByTypeAndSaleAndPriceBetween(Pageable pageable, String type, boolean sale, double priceBegin, double priceEnd);

    Long countAllByType(String type);
}
