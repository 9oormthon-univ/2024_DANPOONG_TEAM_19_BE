package org.anyonetoo.anyonetoo.service;

import lombok.RequiredArgsConstructor;
import org.anyonetoo.anyonetoo.domain.dto.video.KakaoVideoSearchResponseDTO;
import org.anyonetoo.anyonetoo.domain.entity.User;
import org.anyonetoo.anyonetoo.domain.mapping.ConsumerPrefer;
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

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<ConsumerPrefer> consumerPrefers = user.getConsumer().getConsumerPrefers();

        int randomIndex = ThreadLocalRandom.current().nextInt(consumerPrefers.size());
        ConsumerPrefer randomPrefer = consumerPrefers.get(randomIndex);

        String query = randomPrefer.getCategory().getName();

        String requestUrl = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("query", query)
                .toUriString();

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
