package org.anyonetoo.anyonetoo.domain.dto.video;

import lombok.Data;

import java.util.List;

@Data
public class KakaoVideoSearchResponseDTO {
    private List<Document> documents;
    private Meta meta;

    @Data
    public static class Document {
        private String title;
        private String url;
        private String datetime;
        private String playTime;
        private String thumbnail;
        private String author;
    }

    @Data
    public static class Meta {
        private int totalCount;
        private int pageableCount;
        private boolean isEnd;
    }
}
