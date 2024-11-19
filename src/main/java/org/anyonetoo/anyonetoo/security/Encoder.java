package org.anyonetoo.anyonetoo.security;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class Encoder {
    public String encrypt(String text) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(text.getBytes());

        return bytesToHex(messageDigest.digest());
//        try {
//            // SHA-256 알고리즘이 존재하는지 체크하려면 예외를 처리하는 방법을 사용해야 합니다.
//            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
//            messageDigest.update(text.getBytes());
//            return bytesToHex(messageDigest.digest());
//        } catch (NoSuchAlgorithmException e) {
//            // 예외 발생 시 다른 처리 로직을 추가할 수 있습니다.
//            System.err.println("Encryption algorithm SHA-256 not found.");
//            return null; // 또는 다른 방식으로 처리
//        }

    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}