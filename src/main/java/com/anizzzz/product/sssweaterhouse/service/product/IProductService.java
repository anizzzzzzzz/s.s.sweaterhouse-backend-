package com.anizzzz.product.sssweaterhouse.service.product;

import com.anizzzz.product.sssweaterhouse.dto.ProductResponse;
import com.anizzzz.product.sssweaterhouse.dto.ResponseMessage;
import com.anizzzz.product.sssweaterhouse.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IProductService {
    ResponseMessage Save(MultipartFile[] images,String name, String type, double price, boolean sale, String[] size, String selectedImage)
            throws IOException;

    Optional<Product> findByIdAndProductCode(String id, String productCode);

    List<Product> findAllByType(String type);

    Page<Product> findAll(Pageable pageable) throws IOException;

    Page<Product> findAllBySale(Pageable pageable) throws IOException;

    Page<Product> findAllByType(Pageable pageable, String type) throws IOException;

    Page<Product> findAllBySaleAndType(Pageable pageable, String type) throws IOException;

    Page<Product> findAllByTypeAndPriceBetween(Pageable pageable, String type, double priceBegin, double priceEnd) throws IOException;

    Page<Product> findAllByTypeAndSaleAndPriceBetween(Pageable pageable, String type, boolean sale, double priceBegin, double priceEnd) throws IOException;
}
