package com.desafiojavapitang.utils;

import org.springframework.security.core.Authentication;

public interface JwtTokenProvider {

    String generateToken(Authentication authentication);
}
