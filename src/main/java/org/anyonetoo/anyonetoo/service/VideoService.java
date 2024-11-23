package org.anyonetoo.anyonetoo.service;

import lombok.RequiredArgsConstructor;
import org.anyonetoo.anyonetoo.domain.dto.video.KakaoVideoSearchResponseDTO;
import org.anyonetoo.anyonetoo.domain.entity.User;
import org.anyonetoo.anyonetoo.domain.mapping.ConsumerPrefer;
import org.anyonetoo.anyonetoo.domain.mapping.SellerPrefer;
import org.anyonetoo.anyonetoo.exception.RestApiException;
import org.anyonetoo.anyonetoo.exception.code.CustomErrorCode;
import org.anyonetoo.anyonetoo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final UserRepository userRepository;

    @Value("${kakao.api.key}")
    private String apiKey;

    @Value("${kakao.api.base-url}")
    private String baseUrl;

    private final WebClient webClient = WebClient.builder().build();

    public KakaoVideoSearchResponseDTO search(String userId) {
        String query;
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.USER_NOT_FOUND));

        // 쿼리 생성
        if(user.getConsumer() != null) {
            List<ConsumerPrefer> consumerPrefers = user.getConsumer().getConsumerPrefers();
            int randomIndex = ThreadLocalRandom.current().nextInt(consumerPrefers.size());
            ConsumerPrefer randomPrefer = consumerPrefers.get(randomIndex);
            query = randomPrefer.getCategory().getName();
        } else {
            List<SellerPrefer> sellerPrefers = user.getSeller().getSellerPrefers();
            int randomIndex = ThreadLocalRandom.current().nextInt(sellerPrefers.size());
            SellerPrefer randomPrefer = sellerPrefers.get(randomIndex);
            query = randomPrefer.getCategory().getName();
        }

        String requestUrl = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("query", query) // 쿼리 파라미터로 query 값을 설정
                .build()  // UriComponents를 빌드
                .toUriString();

        // WebClient 사용하여 API 요청
        return webClient.get()
                .uri(requestUrl)
                .header("Authorization", "KakaoAK " + apiKey)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> {
                            // 응답 상태 코드 출력
                            System.out.println("Error response: " + clientResponse.statusCode());
                            return clientResponse.createException();
                        })
                .bodyToMono(KakaoVideoSearchResponseDTO.class)
                .block();
    }

    public KakaoVideoSearchResponseDTO searchKeyword(String query) {
        // 쿼리 파라미터를 URL 인코딩 처리
        String requestUrl = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("query", query) // 쿼리 파라미터로 query 값을 설정
                .build()  // UriComponents를 빌드
                .toUriString(); // 최종 URI 문자열로 변환 (encode()는 필요 없음)

        return webClient.get()
                .uri(requestUrl)
                .header("Authorization", "KakaoAK " + apiKey)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> {
                            // 응답 상태 코드 출력
                            System.out.println("Error response: " + clientResponse.statusCode());
                            return clientResponse.createException();
                        })
                .bodyToMono(KakaoVideoSearchResponseDTO.class)
                .block();
    }




}
