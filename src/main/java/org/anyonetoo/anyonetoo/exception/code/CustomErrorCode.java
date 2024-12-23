package org.anyonetoo.anyonetoo.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomErrorCode implements ErrorCode{

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "Internal Server Error"),
    INVALID_PARAMS(HttpStatus.BAD_REQUEST, 400, "Request Validation Failed"),

    // Product
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "Product Not Found"),
    NO_PERMISSION_FOR_DELETE_PRODUCT(HttpStatus.FORBIDDEN, 403, "No permission provided for delete this product"),
    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "User Not Found"),
    INVALID_USER_TYPE(HttpStatus.BAD_REQUEST, 400, "User type not Defined"),

    // Comment
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "Comment Not Found"),
    NO_PERMISSION_FOR_COMMENT(HttpStatus.FORBIDDEN, 403, "No Permission For Comment"),

    // Seller
    SELLER_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "Seller Not Found"),
    CONSUMER_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "Consumer Not Found"),

    PURCHASE_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "Purchase Not Found"),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "Purchase Not Found"),
    ;


    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;
}
