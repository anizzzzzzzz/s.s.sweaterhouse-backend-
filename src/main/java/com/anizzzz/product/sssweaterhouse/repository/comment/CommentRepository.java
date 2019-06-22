package com.anizzzz.product.sssweaterhouse.repository.comment;

import com.anizzzz.product.sssweaterhouse.model.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, String> {
}
