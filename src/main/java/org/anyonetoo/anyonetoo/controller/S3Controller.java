package org.anyonetoo.anyonetoo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.anyonetoo.anyonetoo.domain.dto.res.ResponseDto;
import org.anyonetoo.anyonetoo.service.S3Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/core/s3")
public class S3Controller {
    private final S3Service s3Service;

    @Operation(summary = "S3 PresignedUrl 발급")
    @ApiResponse(responseCode = "200", description = "Url 발급 성공")
    @GetMapping("/presignedUrl")
    public ResponseEntity<ResponseDto<String>> getS3PresignedUrl(@RequestParam String fileName){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(s3Service.getPresignedUrl(fileName), "Url 발급 성공"));
    }
}
