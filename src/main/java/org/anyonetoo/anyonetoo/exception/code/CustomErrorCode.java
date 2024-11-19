package org.anyonetoo.anyonetoo.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomErrorCode implements ErrorCode{
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "Internal Server Error"),

    // Product
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "Product Not Found"),

    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "User Not Found"),

    // Comment
    MAIN_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "Main Comment Not Found"),

    // Seller
    SELLER_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "Seller Not Found"),
    ;

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;
}
