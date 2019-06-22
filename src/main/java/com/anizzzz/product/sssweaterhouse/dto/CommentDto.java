package com.anizzzz.product.sssweaterhouse.dto;

public class CommentDto {
    private String productId;
    private String comment;
    private int rate=3;
    private String commenterUsername;

    public CommentDto() {
    }

    public CommentDto(String productId, String comment, int rate, String commenterUsername) {
        this.productId = productId;
        this.rate = rate;
        this.comment = comment;
        this.commenterUsername = commenterUsername;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommenterUsername() {
        return commenterUsername;
    }

    public void setCommenterUsername(String commenterUsername) {
        this.commenterUsername = commenterUsername;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
