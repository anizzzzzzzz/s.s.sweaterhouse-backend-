package com.anizzzz.product.sssweaterhouse.controller;

import com.anizzzz.product.sssweaterhouse.dto.ProductResponse;
import com.anizzzz.product.sssweaterhouse.dto.ResponseMessage;
import com.anizzzz.product.sssweaterhouse.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {
    private final IProductService iProductService;

    @Autowired
    public ProductController(IProductService iProductService) {
        this.iProductService = iProductService;
    }

    @PostMapping("/upload-images")
    public ResponseMessage uploadImages(@RequestParam("images") MultipartFile[] images,
                                        @RequestParam("type") String type,
                                        @RequestParam("price") String price,
                                        @RequestParam("sale") String sale,
                                        @RequestParam("size") String size) throws IOException {
        return iProductService.Save(images, type, Double.parseDouble(price) ,Boolean.parseBoolean(sale),size);
    }

    @GetMapping("/find-all")
    public ResponseEntity<?> findAll(Pageable pageable) throws IOException {
        return ResponseEntity.ok(iProductService.findAll(pageable));
    }

    @PostMapping("/find-all-by-type")
    public ResponseEntity<?> findAllByType(Pageable pageable, @RequestParam String type) throws IOException{
        return ResponseEntity.ok(iProductService.findAllByType(pageable,type));
    }
}