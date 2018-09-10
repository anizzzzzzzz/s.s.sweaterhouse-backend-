package com.anizzzz.product.sssweaterhouse.service;

import com.anizzzz.product.sssweaterhouse.dto.ProductResponse;
import com.anizzzz.product.sssweaterhouse.dto.ResponseMessage;
import com.anizzzz.product.sssweaterhouse.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IProductService {
    ResponseMessage Save(MultipartFile[] images,String name, String type, double price, boolean sale, String[] size, String selectedImage)
            throws IOException;

    Optional<Product> findOne(Long id);

    ResponseMessage findByProductCode(String productCode);

    List<Product> findAllByType(String type);

    Page<ProductResponse> findAll(Pageable pageable) throws IOException;

    Page<ProductResponse> findAllBySale(Pageable pageable) throws IOException;

    Page<ProductResponse> findAllByType(Pageable pageable, String type) throws IOException;

    Page<ProductResponse> findAllBySaleAndType(Pageable pageable, boolean sale, String type) throws IOException;

    Page<ProductResponse> findAllByTypeAndPriceBetween(Pageable pageable, String type, double priceBegin, double priceEnd) throws IOException;

    Page<ProductResponse> findAllByTypeAndSaleAndPriceBetween(Pageable pageable, String type, boolean sale, double priceBegin, double priceEnd) throws IOException;
}
