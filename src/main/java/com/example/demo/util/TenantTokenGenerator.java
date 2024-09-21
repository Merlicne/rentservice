package com.example.demo.util;

import java.nio.charset.StandardCharsets;


import com.google.common.hash.Hashing;

public class TenantTokenGenerator {
    public static String generateToken(String tenantPhone) {
        String sha256hex = Hashing.sha256()
                .hashString(tenantPhone, StandardCharsets.UTF_8)
                .toString();
        return sha256hex;

    }
}
