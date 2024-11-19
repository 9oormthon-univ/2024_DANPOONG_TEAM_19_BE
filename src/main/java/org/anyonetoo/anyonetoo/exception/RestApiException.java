package org.anyonetoo.anyonetoo.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.anyonetoo.anyonetoo.exception.code.ErrorCode;

@Getter
@RequiredArgsConstructor
public class RestApiException extends RuntimeException{
    private final ErrorCode errorCode;
}
