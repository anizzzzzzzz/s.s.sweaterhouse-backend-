package com.anizzzz.product.sssweaterhouse.service.comment;

import com.anizzzz.product.sssweaterhouse.dto.CommentDto;
import com.anizzzz.product.sssweaterhouse.dto.ResponseMessage;

public interface ICommentService {
    ResponseMessage save(CommentDto commentDto);
}
