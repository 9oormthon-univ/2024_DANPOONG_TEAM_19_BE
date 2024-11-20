package org.anyonetoo.anyonetoo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AnyoneTooApplicationTests {

//    @Value("${cloud.aws.credentials.access-key}")
//    private String accessKey;
//
//    @Value("${cloud.aws.credentials.secret-key}")
//    private String secretKey;
//
//    @Value("${cloud.aws.s3.bucket}")
//    private String bucketName;

//    @Test
//    void env변수_로드_테스트() {
//        System.out.println("AWS Access Key: " + accessKey);
//        System.out.println("AWS Secret Key: " + secretKey);
//        System.out.println("Bucket Name: " + bucketName);
//
//        assertThat(accessKey).isNotNull();
//        assertThat(secretKey).isNotNull();
//        assertThat(bucketName).isNotNull();
//    }

    @Test
    void contextLoads() {
    }
}