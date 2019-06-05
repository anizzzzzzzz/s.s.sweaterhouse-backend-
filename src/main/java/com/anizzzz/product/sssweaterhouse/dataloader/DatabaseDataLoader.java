package com.anizzzz.product.sssweaterhouse.dataloader;

import com.anizzzz.product.sssweaterhouse.constant.Size;
import com.anizzzz.product.sssweaterhouse.constant.UserRole;
import com.anizzzz.product.sssweaterhouse.model.ProductSize;
import com.anizzzz.product.sssweaterhouse.model.Role;
import com.anizzzz.product.sssweaterhouse.model.Users;
import com.anizzzz.product.sssweaterhouse.service.product.IProductSizeService;
import com.anizzzz.product.sssweaterhouse.service.user.IRoleService;
import com.anizzzz.product.sssweaterhouse.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DatabaseDataLoader implements ApplicationRunner {
    private final IRoleService iRoleService;
    private final IProductSizeService iProductSizeService;
    private final IUserService iUserService;

    @Autowired
    public DatabaseDataLoader(IRoleService iRoleService, IProductSizeService iProductSizeService, IUserService iUserService) {
        this.iRoleService = iRoleService;
        this.iProductSizeService = iProductSizeService;
        this.iUserService = iUserService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(iRoleService.findAll().size()==0 && iRoleService.findAll().isEmpty()){
            iRoleService.saveAll(
                    Arrays.asList(
                            new Role(UserRole.SUPER_ADMIN.toString()),
                            new Role(UserRole.ADMIN.toString()),
                            new Role(UserRole.USER.toString())
                    )
            );
        }

        if(iProductSizeService.findAll().size()==0 && iProductSizeService.findAll().isEmpty()){
            iProductSizeService.saveAll(
                    Arrays.asList(
                            new ProductSize(Size.S.toString()),
                            new ProductSize(Size.L.toString()),
                            new ProductSize(Size.XL.toString()),
                            new ProductSize(Size.XXL.toString()),
                            new ProductSize(Size.XXXL.toString())
                    )
            );
        }

        String adminUsername = "admin@sweaterhouse.com";
        if(!iUserService.findByUsername(adminUsername).isPresent()){
            iUserService.saveAdmin(
                    new Users(
                            "Admin",
                            "Sweaterhouse",
                            adminUsername,
                            "Sweaterhouse101",
                            iRoleService.findAll()
                    )
            );
        }
    }
}
