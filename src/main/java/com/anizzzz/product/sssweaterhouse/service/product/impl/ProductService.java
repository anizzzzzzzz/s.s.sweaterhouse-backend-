package com.anizzzz.product.sssweaterhouse.service.product.impl;

import com.anizzzz.product.sssweaterhouse.dto.ProductRequest;
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
import java.util.stream.Collectors;

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

        String folderLocation=System.getProperty("user.dir") + "/images/"+type;
        File imageFolder = new File(folderLocation);

        if (!imageFolder.exists())
            imageFolder.mkdirs();

        String productCode=getProductCode(type);
        saveImages(productInfos, images, selectedImage, folderLocation, type);

        productRepository.save(
                new Product(
                        name,
                        productCode,
                        type,
                        price,
                        sale,
                        new Date(),
                        Arrays.stream(size).map(iProductSizeService::findBySize).collect(Collectors.toList()),
                        productInfos
                )
        );
        return new ResponseMessage("Product have been saved successfully.", HttpStatus.OK);
    }

    @Override
    public ResponseMessage update(MultipartFile[] images, ProductRequest productRequest) {
        Optional<Product> productOptional = findByIdAndProductCode(productRequest.getId(),
                productRequest.getProductCode());

        if(productOptional.isPresent()){
            Product product = productOptional.get();

            String folderLocation=System.getProperty("user.dir") + "/images/"+ product.getType();
            File imageFolder = new File(folderLocation);
            if (!imageFolder.exists())
                imageFolder.mkdirs();

            // Deleting the ProductInfos selected by user.
            productRequest.getDeletedImagesId().forEach(id -> {
                iProductInfoService.findById(id)
                        .ifPresent(productInfo -> product.getProductInfos().remove(productInfo));
            });
            saveImages(product.getProductInfos(),
                    images, productRequest.getSelectedImage(), folderLocation, product.getType());

            // If selected Image isn't newly added images, then selected image will be id.
            // So, if it is id, then setting that image as highlight.
            if(!productRequest.getSelectedImage().contains(".jpeg") ||
                    !productRequest.getSelectedImage().contains(".png") ||
                    !productRequest.getSelectedImage().contains(".jpg")){
                product.getProductInfos().forEach(productInfo -> {
                    if (productInfo.getName().equalsIgnoreCase(productRequest.getSelectedImage()))
                        productInfo.setHighlight(true);
                    else
                        productInfo.setHighlight(false);
                });
            }

            // Updating product
            product.setName(productRequest.getName());
            product.setUpdatedDate(new Date());
            product.setPrice(productRequest.getPrice());
            product.setSale(productRequest.isSale());

            // updating size
            product.setSize(
                    productRequest.getSize().stream()
                            .map(iProductSizeService::findBySize)
                            .collect(Collectors.toList())
            );
            productRepository.save(product);
            return new ResponseMessage("Successfully updated the product.", HttpStatus.OK);
        }

        return new ResponseMessage("Cannot find product. Sorry, cannot execute operation", HttpStatus.BAD_REQUEST);
    }

    @Override
    public boolean deleteProduct(String id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isPresent()){
            Product product = productOptional.get();
            product.setSize(null);
            product.setComments(null);

            // deleting the image file first
            product.getProductInfos().forEach(productInfo ->
                    new File(System.getProperty("user.dir") + "/images/" + productInfo.getLocation()).delete());
            product.setProductInfos(null);

            productRepository.save(product);

            iProductInfoService.deleteAllIfProductIdIsNull();
            productRepository.delete(product);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Product> findByIdAndProductCode(String id, String productCode) {
        return productRepository.findByIdAndProductCode(id, productCode);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) throws IOException {
//        Page<Product> products=productRepository.findAllByOrderByCreatedDateDesc(pageable);
//        return getProductResponse(products);
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findAllBySale(Pageable pageable) throws IOException {
        /*Page<Product> products=productRepository.findAllBySaleOrderByCreatedDateDesc(pageable,true);
        return getProductResponse(products);*/
        return productRepository.findAllBySale(pageable,true);
    }

    @Override
    public Page<Product> findAllByType(Pageable pageable, String type) throws IOException {
        return productRepository.findAllByType(pageable,type);
    }

    @Override
    public Page<Product> findAllBySaleAndType(Pageable pageable, String type) throws IOException {
        return productRepository.findAllBySaleAndType(pageable,true,type);
    }

    @Override
    public Page<Product> findAllByTypeAndPriceBetween(Pageable pageable, String type, double priceBegin, double priceEnd)
            throws IOException {
        return productRepository.findAllByTypeAndPriceBetween
                (pageable,
                        type,
                        priceBegin,
                        priceEnd
                );
    }

    @Override
    public Page<Product> findAllByTypeAndSaleAndPriceBetween(Pageable pageable, String type, boolean sale, double priceBegin, double priceEnd) throws IOException {
        return productRepository.findAllByTypeAndSaleAndPriceBetween(
                pageable,
                type,
                true,
                priceBegin,
                priceEnd
        );
    }

    private String getProductCode(String type){
        Long index = productRepository.countAllByType(type);

        if(type.equalsIgnoreCase("sweater")){
            return "SWT-" + Long.toString(index + 1);
        }
        else if(type.equalsIgnoreCase("Handwarmer")) {
            return "HNW-" + Long.toString(index + 1);
        }
        else if(type.equalsIgnoreCase("Jacket")) {
            return "JCK-" + Long.toString(index + 1);
        }
        else if(type.equalsIgnoreCase("Shock")){
            return "SCK-" + Long.toString(index + 1);
        }
        else if(type.equalsIgnoreCase("Trouser")){
            return "TRS-" + Long.toString(index + 1);
        }
        else{
            return "OTHER-" +  Long.toString(index + 1);
        }
    }

    private String getRandomFileName(String extension){
        return UUID.randomUUID().toString() +"."+ extension;
    }

    private String imageType(String extension){
        if(extension.equalsIgnoreCase("jpg"))
            return MediaType.IMAGE_JPEG_VALUE;
        else if(extension.equalsIgnoreCase("png"))
            return MediaType.IMAGE_PNG_VALUE;
        else
            return null;
    }

    private void saveImages(List<ProductInfo> productInfos, MultipartFile[] mFiles,
                            String selectedImage, String folderLocation, String type){
        int i=0;
        for(MultipartFile image:mFiles) {
            try{
                String extension = image.getOriginalFilename()
                        .substring(Objects.requireNonNull(image.getOriginalFilename()).lastIndexOf('.')+1);
                if(extension.equalsIgnoreCase("jpeg") ||
                        extension.equalsIgnoreCase("png") ||
                        extension.equalsIgnoreCase("jpg")) {
                    boolean isSelected=false;
                    if(selectedImage!=null){
                        if(selectedImage.endsWith(".jpeg") || selectedImage.endsWith(".png")
                                || selectedImage.endsWith(".jpg")) {
                            if (selectedImage.equalsIgnoreCase(image.getOriginalFilename())){
                                System.out.println("true");
                                isSelected = true;
                                productInfos.forEach(prod -> prod.setHighlight(false));
                            }
                        }
                    }
                    else {
                        if(i==0)
                            isSelected=true;
                    }
                    String fileNameWithExt = getRandomFileName(extension);
                    String fileName = fileNameWithExt.substring(0,fileNameWithExt.lastIndexOf('.'));

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
                                    fileName,
                                    extension,
                                    imageType(extension),
                                    type + "/" + fileNameWithExt,
                                    isSelected
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
    }
}
