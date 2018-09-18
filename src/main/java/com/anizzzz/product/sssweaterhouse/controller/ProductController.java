package com.anizzzz.product.sssweaterhouse.controller;

import com.anizzzz.product.sssweaterhouse.dto.ResponseMessage;
import com.anizzzz.product.sssweaterhouse.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ProductController {
    private final IProductService iProductService;

    @Autowired
    public ProductController(IProductService iProductService) {
        this.iProductService = iProductService;
    }

    @PostMapping("/upload-images")
    public ResponseEntity<?> uploadImages(@RequestParam("images") MultipartFile[] images,
                                        @RequestParam("name") String name,
                                        @RequestParam("type") String type,
                                        @RequestParam("price") Double price,
                                        @RequestParam("sale") Boolean sale,
                                        @RequestParam("size") String[] size,
                                        @RequestParam("selectedImage") String selectedImage) throws IOException {
        ResponseMessage response=iProductService.Save(images, name, type, price ,sale,size,selectedImage);
        return new ResponseEntity<Object>(response,response.getHttpStatus());
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
    public ResponseEntity<?> findOneProduct(@RequestParam String productCode){
        ResponseMessage responseMessage=iProductService.findByProductCode(productCode);
        return new ResponseEntity<Object>(responseMessage, responseMessage.getHttpStatus());
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
