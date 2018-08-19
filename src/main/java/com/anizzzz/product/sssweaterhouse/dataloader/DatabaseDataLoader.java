package com.anizzzz.product.sssweaterhouse.dataloader;

import com.anizzzz.product.sssweaterhouse.model.Role;
import com.anizzzz.product.sssweaterhouse.model.ProductSize;
import com.anizzzz.product.sssweaterhouse.service.IRoleService;
import com.anizzzz.product.sssweaterhouse.service.IProductSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.anizzzz.product.sssweaterhouse.constant.SizeConstant.*;
import static com.anizzzz.product.sssweaterhouse.constant.UserConstant.*;

@Component
public class DatabaseDataLoader implements ApplicationRunner {
    private final IRoleService iRoleService;
    private final IProductSizeService iProductSizeService;

    @Autowired
    public DatabaseDataLoader(IRoleService iRoleService, IProductSizeService iProductSizeService) {
        this.iRoleService = iRoleService;
        this.iProductSizeService = iProductSizeService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(iRoleService.findAll().size()==0 && iRoleService.findAll().isEmpty()){
            iRoleService.saveAll(
                    Arrays.asList(
                            new Role(SUPER_ADMIN),
                            new Role(ADMIN),
                            new Role(USER)
                    )
            );
        }

        if(iProductSizeService.findAll().size()==0 && iProductSizeService.findAll().isEmpty()){
            iProductSizeService.saveAll(
                    Arrays.asList(
                            new ProductSize(SM),
                            new ProductSize(LS),
                            new ProductSize(XL),
                            new ProductSize(XXL),
                            new ProductSize(XXXL)
                    )
            );
        }
    }
}
