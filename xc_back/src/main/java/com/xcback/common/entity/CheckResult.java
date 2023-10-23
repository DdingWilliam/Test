package com.xcback.common.entity;

import io.jsonwebtoken.Claims;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
/**
 * JWT 验证信息
 */
public class CheckResult {

    private int errCode;

    private boolean success;

    private Claims claims;

}
