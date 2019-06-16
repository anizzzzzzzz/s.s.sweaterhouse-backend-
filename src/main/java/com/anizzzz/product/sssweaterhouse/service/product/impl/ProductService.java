package com.anizzzz.product.sssweaterhouse.service.product.impl;

import com.anizzzz.product.sssweaterhouse.dto.ResponseMessage;
import com.anizzzz.product.sssweaterhouse.exceptionHandling.exceptions.ExtensionMismatchException;
import com.anizzzz.product.sssweaterhouse.exceptionHandling.exceptions.ProductException;
import com.anizzzz.product.sssweaterhouse.model.product.Product;
import com.anizzzz.product.sssweaterhouse.model.product.ProductInfo;
import com.anizzzz.product.sssweaterhouse.model.product.ProductSize;
import com.anizzzz.product.sssweaterhouse.repository.product.ProductRepository;
import com.anizzzz.product.sssweaterhouse.service.product.IProductInfoService;
import com.anizzzz.product.sssweaterhouse.service.product.IProductService;
import com.anizzzz.product.sssweaterhouse.service.product.IProductSizeService;
import com.anizzzz.product.sssweaterhouse.utils.ICompresserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final IProductInfoService iProductInfoService;
    private final IProductSizeService iProductSizeService;
    private final ICompresserUtils jpgCompresser;
    private final ICompresserUtils pngCompresser;

    @Autowired
    public ProductService(ProductRepository productRepository, IProductInfoService iProductInfoService, IProductSizeService iProductSizeService, ICompresserUtils jpgCompresser, ICompresserUtils pngCompresser) {
        this.productRepository = productRepository;
        this.iProductInfoService = iProductInfoService;
        this.iProductSizeService = iProductSizeService;
        this.jpgCompresser = jpgCompresser;
        this.pngCompresser = pngCompresser;
    }

    @Override
    public ResponseMessage save(MultipartFile[] images, String name, String type,
                                double price, boolean sale, String[] size, String selectedImage){
        List<ProductInfo> productInfos=new ArrayList<>();
        List<ProductSize> sizes=new ArrayList<>();

        Arrays.stream(size).forEach(sz->{
            sizes.add(iProductSizeService.findBySize(sz));
        });

        String folderLocation=System.getProperty("user.dir") + "/images/"+type;

        String productCode=getProductCode(type);
        int i=0;
        for(MultipartFile image:images) {
            try{
                String extension = image.getOriginalFilename()
                        .substring(Objects.requireNonNull(image.getOriginalFilename()).lastIndexOf('.')+1);
                if(extension.equalsIgnoreCase("jpeg") ||
                        extension.equalsIgnoreCase("png") ||
                        extension.equalsIgnoreCase("jpg")) {
                    boolean isSelected=false;
                    if(selectedImage!=null){
                        if(selectedImage.equalsIgnoreCase(image.getOriginalFilename()))
                            isSelected=true;
                    }
                    else {
                        if(i==0)
                            isSelected=true;
                    }
                    String fileNameWithExt = getRandomFileName(extension);
                    String fileName = fileNameWithExt.substring(0,fileNameWithExt.lastIndexOf('.'));
                    File imageFolder = new File(folderLocation);

                    if (!imageFolder.exists())
                        imageFolder.mkdirs();

                    String fileLocation = folderLocation+ "/" + fileNameWithExt;
                    /*File file = new File(fileLocation);

                    image.transferTo(file);*/
                    if(extension.equalsIgnoreCase("jpg")) {
                        jpgCompresser.compressImages(image.getInputStream(), fileLocation);
                    }
                    else if(extension.equalsIgnoreCase("png")) {
                        pngCompresser.compressImages(image.getInputStream(), fileLocation);
                    }

                    // since the images are hosted using the apache2,
                    // so only writing the path from imagaes
                    // ie; handwarmer/123123123.jpeg
                    productInfos.add(
                            new ProductInfo(
                                    fileName,extension,imageType(extension),type + "/" + fileNameWithExt,isSelected
                            )
                    );
                    i++;
                }
                else{
                    throw new ExtensionMismatchException();
                }
            }
            catch (IOException ex){
                throw new ProductException("Problem occured while saving product.",ex);
            }
            catch (ExtensionMismatchException ex){
                throw new ExtensionMismatchException();
            }
        }
        productRepository.save(new Product(name, productCode,type,price,sale,new Date(),sizes,productInfos));
        return new ResponseMessage("Product have been saved successfully.", HttpStatus.OK);
    }

    @Override
    public ResponseMessage update(MultipartFile[] imamges, Product product) {
        return null;
    }

    @Override
    public Optional<Product> findByIdAndProductCode(String id, String productCode) {
//        Optional<Product> product=productRepository.findByProductCode(productCode);

        /*return product.map(this::findSelectedProduct).
                orElseGet(() ->
                        new ResponseMessage("Cannot find product with specified product code", HttpStatus.NOT_FOUND));*/
        return productRepository.findByIdAndProductCode(id, productCode);
    }

    @Override
    public List<Product> findAllByType(String type) {
        return productRepository.findAllByType(type);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) throws IOException {
//        Page<Product> products=productRepository.findAllByOrderByCreatedDateDesc(pageable);
//        return getProductResponse(products);
        return productRepository.findAllByOrderByCreatedDateDesc(pageable);
    }

    @Override
    public Page<Product> findAllBySale(Pageable pageable) throws IOException {
        /*Page<Product> products=productRepository.findAllBySaleOrderByCreatedDateDesc(pageable,true);
        return getProductResponse(products);*/
        return productRepository.findAllBySaleOrderByCreatedDateDesc(pageable,true);
    }

    @Override
    public Page<Product> findAllByType(Pageable pageable, String type) throws IOException {
        return productRepository.findAllByTypeOrderByCreatedDateDesc(pageable,type);
    }

    @Override
    public Page<Product> findAllBySaleAndType(Pageable pageable, String type) throws IOException {
        return productRepository.findAllBySaleAndTypeOrderByCreatedDateDesc(pageable,true,type);
    }

    @Override
    public Page<Product> findAllByTypeAndPriceBetween(Pageable pageable, String type, double priceBegin, double priceEnd)
            throws IOException {
        return productRepository.findAllByTypeAndPriceBetweenOrderByCreatedDateDesc
                (pageable,
                        type,
                        priceBegin,
                        priceEnd
                );
    }

    @Override
    public Page<Product> findAllByTypeAndSaleAndPriceBetween(Pageable pageable, String type, boolean sale, double priceBegin, double priceEnd) throws IOException {
        return productRepository.findAllByTypeAndSaleAndPriceBetweenOrderByCreatedDateDesc(
                pageable,
                type,
                true,
                priceBegin,
                priceEnd
        );
    }

    private String getProductCode(String type){
        long index;
        List<Product> products=findAllByType(type);
        if(products == null){
            index=1;
        }
        else{
            index=products.size()+1;
        }

        if(type.equalsIgnoreCase("sweater")){
            return "SWT-" + Long.toString(index);
        }
        else if(type.equalsIgnoreCase("Handwarmer")) {
            return "HNW-" + Long.toString(index);
        }
        else if(type.equalsIgnoreCase("Jacket")) {
            return "JCK-" + Long.toString(index);
        }
        else if(type.equalsIgnoreCase("Shock")){
            return "SCK-" + Long.toString(index);
        }
        else if(type.equalsIgnoreCase("Trouser")){
            return "TRS-" + Long.toString(index);
        }
        else{
            return "OTHER-" +  Long.toString(index);
        }
    }

    private String getRandomFileName(String extension){
        String fileName = UUID.randomUUID().toString();

        while(iProductInfoService.findByName(fileName).isPresent()){
            fileName = UUID.randomUUID().toString();
        }
        return fileName +"."+ extension;
    }

    private String imageType(String extension){
        if(extension.equalsIgnoreCase("jpg"))
            return MediaType.IMAGE_JPEG_VALUE;
        else if(extension.equalsIgnoreCase("png"))
            return MediaType.IMAGE_PNG_VALUE;
        else
            return null;
    }
}
