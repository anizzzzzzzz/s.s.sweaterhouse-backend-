package com.anizzzz.product.sssweaterhouse.service.comment.impl;

import com.anizzzz.product.sssweaterhouse.dto.CommentDto;
import com.anizzzz.product.sssweaterhouse.dto.ResponseMessage;
import com.anizzzz.product.sssweaterhouse.model.comment.Comment;
import com.anizzzz.product.sssweaterhouse.model.product.Product;
import com.anizzzz.product.sssweaterhouse.model.user.User;
import com.anizzzz.product.sssweaterhouse.repository.comment.CommentRepository;
import com.anizzzz.product.sssweaterhouse.repository.product.ProductRepository;
import com.anizzzz.product.sssweaterhouse.service.comment.ICommentService;
import com.anizzzz.product.sssweaterhouse.service.product.IProductService;
import com.anizzzz.product.sssweaterhouse.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CommentService implements ICommentService {
    private final CommentRepository commentRepository;
    private final IUserService userService;
    private final ProductRepository productRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, IUserService userService, ProductRepository productRepository) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.productRepository = productRepository;
    }

    @Override
    public ResponseMessage save(CommentDto commentDto) {
        Optional<User> user = userService.findByUsername(commentDto.getCommenterUsername());
        Optional<Product> productOptional = productRepository.findById(commentDto.getProductId());

        if(user.isPresent() && productOptional.isPresent()){
            Comment comment = commentRepository.save(
                    new Comment(
                            commentDto.getComment(),
                            commentDto.getRate(),
                            new Date(),
                            user.get()
                    )
            );

            Product product = productOptional.get();
            product.getComments().add(comment);
            productRepository.save(product);

            return new ResponseMessage("Successfully saved comment for " + commentDto.getProductId() + " resume data",
                    HttpStatus.OK);
        }
        return new ResponseMessage("Successfully saved comment for " + commentDto.getProductId() + " resume data",
                HttpStatus.BAD_REQUEST);
    }
}
