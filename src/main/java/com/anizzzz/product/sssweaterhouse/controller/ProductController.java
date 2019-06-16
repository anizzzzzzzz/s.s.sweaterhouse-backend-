package com.anizzzz.product.sssweaterhouse.controller;

import com.anizzzz.product.sssweaterhouse.dto.ResponseMessage;
import com.anizzzz.product.sssweaterhouse.model.product.Product;
import com.anizzzz.product.sssweaterhouse.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
public class ProductController {
    private final IProductService iProductService;

    @Autowired
    public ProductController(IProductService iProductService) {
        this.iProductService = iProductService;
    }

    @PostMapping("/upload-products")
    public ResponseEntity<?> uploadImages(@RequestParam("images") MultipartFile[] images,
                                        @RequestParam("name") String name,
                                        @RequestParam("type") String type,
                                        @RequestParam("price") Double price,
                                        @RequestParam("sale") Boolean sale,
                                        @RequestParam("size") String[] size,
                                        @RequestParam("selectedImage") String selectedImage) throws IOException {
        ResponseMessage response=iProductService.save(images, name, type, price ,sale,size,selectedImage);
        return new ResponseEntity<Object>(response,response.getHttpStatus());
    }

    @PostMapping(value = "/update-product", consumes = {"multipart/form-data"})
    public ResponseEntity<?> updateProduct(@RequestParam("product") Product product,
                                           @RequestParam("images") MultipartFile[] images){
        System.out.println(product);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/find-all")
    public ResponseEntity<?> findAll(Pageable pageable) throws IOException {
        return ResponseEntity.ok(iProductService.findAll(pageable));
    }

    @PostMapping("/find-all-by-type")
    public ResponseEntity<?> findAllByType(Pageable pageable, @RequestParam String type) throws IOException{
        return ResponseEntity.ok(iProductService.findAllByType(pageable,type));
    }

    @PostMapping("/find-one")
    public ResponseEntity<?> findByIdAndProductCode(@RequestParam String id, @RequestParam String productCode){
        Optional<Product> product=iProductService.findByIdAndProductCode(id, productCode);
        if(product.isPresent())
            return ResponseEntity.ok(product.get());
        return ResponseEntity.badRequest()
                .body(new ResponseMessage("Cannot find product with specified detail.", HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/find-all-sales")
    public ResponseEntity<?> findAllSales(Pageable pageable) throws IOException{
        return ResponseEntity.ok(iProductService.findAllBySale(pageable));
    }

    @GetMapping("/find-all-sales-and-type")
    public ResponseEntity<?> findAllBySalesAndType(Pageable pageable, @RequestParam String type) throws IOException{
        return ResponseEntity.ok(iProductService.findAllBySaleAndType(pageable,type));
    }
}
