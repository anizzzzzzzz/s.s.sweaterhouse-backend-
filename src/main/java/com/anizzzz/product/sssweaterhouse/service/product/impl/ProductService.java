package com.anizzzz.product.sssweaterhouse.service.product.impl;

import com.anizzzz.product.sssweaterhouse.dto.ProductResponse;
import com.anizzzz.product.sssweaterhouse.dto.ResponseMessage;
import com.anizzzz.product.sssweaterhouse.exceptionHandling.exceptions.ExtensionMismatchException;
import com.anizzzz.product.sssweaterhouse.exceptionHandling.exceptions.ProductException;
import com.anizzzz.product.sssweaterhouse.model.Product;
import com.anizzzz.product.sssweaterhouse.model.ProductInfo;
import com.anizzzz.product.sssweaterhouse.model.ProductSize;
import com.anizzzz.product.sssweaterhouse.repository.product.ProductRepository;
import com.anizzzz.product.sssweaterhouse.service.product.IProductInfoService;
import com.anizzzz.product.sssweaterhouse.service.product.IProductService;
import com.anizzzz.product.sssweaterhouse.service.product.IProductSizeService;
import com.anizzzz.product.sssweaterhouse.utils.ICompresserUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
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
    public ResponseMessage Save(MultipartFile[] images,String name, String type, double price, boolean sale, String[] size, String selectedImage){
        List<ProductInfo> productInfos=new ArrayList<>();
        List<ProductSize> sizes=new ArrayList<>();

        Arrays.stream(size).forEach(sz->{
            sizes.add(iProductSizeService.findBySize(sz));
        });

        String folderLocation=System.getProperty("users.dir") + "\\images\\"+type;

        String productCode=getProductCode(type);
        int i=0;
        for(MultipartFile image:images) {
            try{
                String extension = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf('.')+1);
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

                    String fileLocation = folderLocation+ "\\" + fileNameWithExt;
                    /*File file = new File(fileLocation);

                    image.transferTo(file);*/
                    if(extension.equalsIgnoreCase("jpg")) {
                        jpgCompresser.compressImages(image.getInputStream(), fileLocation);
                    }
                    else if(extension.equalsIgnoreCase("png")) {
                        pngCompresser.compressImages(image.getInputStream(), fileLocation);
                    }
                    productInfos.add(new ProductInfo(fileName,extension,imageType(extension),fileLocation,isSelected));
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
    public Optional<Product> findOne(Long id) {
        return null;
    }

    @Override
    public ResponseMessage findByProductCode(String productCode) {
        Optional<Product> product=productRepository.findByProductCode(productCode);

        return product.map(this::findSelectedProduct).
                orElseGet(() ->
                        new ResponseMessage("Cannot find product with specified product code", HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Product> findAllByType(String type) {
        return productRepository.findAllByType(type);
    }

    @Override
    public Page<ProductResponse> findAll(Pageable pageable) throws IOException {
        Page<Product> products=productRepository.findAllByOrderByCreatedDateDesc(pageable);
        return getProductResponse(products);
    }

    @Override
    public Page<ProductResponse> findAllBySale(Pageable pageable) throws IOException {
        Page<Product> products=productRepository.findAllBySaleOrderByCreatedDateDesc(pageable,true);
        return getProductResponse(products);
    }

    @Override
    public Page<ProductResponse> findAllByType(Pageable pageable, String type) throws IOException {
        Page<Product> products=productRepository.findAllByTypeOrderByCreatedDateDesc(pageable,type);
        return getProductResponse(products);
    }

    @Override
    public Page<ProductResponse> findAllBySaleAndType(Pageable pageable, String type) throws IOException {
        Page<Product> products=productRepository.findAllBySaleAndTypeOrderByCreatedDateDesc(pageable,true,type);
        return getProductResponse(products);
    }

    @Override
    public Page<ProductResponse> findAllByTypeAndPriceBetween(Pageable pageable, String type, double priceBegin, double priceEnd)
            throws IOException {
        Page<Product> products=productRepository.findAllByTypeAndPriceBetweenOrderByCreatedDateDesc
                (pageable,
                type,
                priceBegin,
                priceEnd
        );
        return getProductResponse(products);
    }

    @Override
    public Page<ProductResponse> findAllByTypeAndSaleAndPriceBetween(Pageable pageable, String type, boolean sale, double priceBegin, double priceEnd) throws IOException {
        Page<Product> products=productRepository.findAllByTypeAndSaleAndPriceBetweenOrderByCreatedDateDesc(
                pageable,
                type,
                true,
                priceBegin,
                priceEnd
        );
        return getProductResponse(products);
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

    private Page<ProductResponse> getProductResponse(Page<Product> products) throws IOException {
        List<ProductResponse> productResponses=new ArrayList<>();

        for(Product product:products.getContent()){
            ProductInfo proInfo = product.getProductInfos().stream().
                    filter(productInfo -> productInfo.isHighlight()).
                    collect(toSingleton());

            if(proInfo == null){
                proInfo=product.getProductInfos().stream().findFirst().get();
            }

            productResponses.add(
                    new ProductResponse(
                            product.getName(),
                            product.getProductCode(),
                            product.getType(),
                            product.getPrice(),
                            product.getSize(),
                            IOUtils.toByteArray(FileUtils.openInputStream(new File(proInfo.getLocation()))),
                            proInfo.getType(),
                            product.isSale()
                    )
            );
        }
        return new Page<ProductResponse>() {
            @Override
            public int getTotalPages() {
                return products.getTotalPages();
            }

            @Override
            public long getTotalElements() {
                return products.getTotalElements();
            }

            @Override
            public <U> Page<U> map(Function<? super ProductResponse, ? extends U> function) {
                return null;
            }

            @Override
            public int getNumber() {
                return products.getNumber();
            }

            @Override
            public int getSize() {
                return products.getSize();
            }

            @Override
            public int getNumberOfElements() {
                return products.getNumberOfElements();
            }

            @Override
            public List<ProductResponse> getContent() {
                return productResponses;
            }

            @Override
            public boolean hasContent() {
                return products.hasContent();
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return products.isFirst();
            }

            @Override
            public boolean isLast() {
                return products.isLast();
            }

            @Override
            public boolean hasNext() {
                return products.hasNext();
            }

            @Override
            public boolean hasPrevious() {
                return products.hasPrevious();
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public Iterator<ProductResponse> iterator() {
                return null;
            }
        };
    }

    private ResponseMessage findSelectedProduct(Product product){
        List<HashMap<String,Object>> images=new ArrayList<>();

        product.getProductInfos().forEach(image->{
            HashMap<String,Object> imagesInfo=new HashMap<>();
            try {
                imagesInfo.put("name",image.getName());
                imagesInfo.put("type",image.getType());
                imagesInfo.put("extension",image.getExtension());
                imagesInfo.put("image",IOUtils.toByteArray(FileUtils.openInputStream(new File(image.getLocation()))));
                imagesInfo.put("highlighted",image.isHighlight());
                images.add(imagesInfo);
            } catch (IOException e) {
                throw new ProductException("Error occured while fetching product",e);
            }
        });

        return new ResponseMessage("Successfully fetched product",
                    new ProductResponse(
                            product.getName(),
                            product.getProductCode(),
                            product.getType(),
                            product.getPrice(),
                            product.getSize(),
                            images
                    ),
                    HttpStatus.OK
                );
    }

    private static <T> Collector<T, ?, T> toSingleton() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> list.size() == 1 ? list.get(0) : null
        );
    }
}
