package org.anyonetoo.anyonetoo.domain.dto.image;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PreSignedUrlResponseDto {
    private Long imageId;
    private String preSignedUrl;

    public static PreSignedUrlResponseDto of(Long imageId, String preSignedUrl) {
        return PreSignedUrlResponseDto.builder()
                .imageId(imageId)
                .preSignedUrl(preSignedUrl)
                .build();
    }
}