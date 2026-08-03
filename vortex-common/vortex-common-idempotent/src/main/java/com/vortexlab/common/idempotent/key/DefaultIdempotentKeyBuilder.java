package com.vortexlab.common.idempotent.key;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vortexlab.common.idempotent.annotation.Idempotent;
import com.vortexlab.common.idempotent.exception.IdempotentException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DefaultIdempotentKeyBuilder implements IdempotentKeyBuilder {

    private final ObjectMapper objectMapper;

    @Override
    public String builder(Idempotent annotation, HttpServletRequest request, Object[] args) {
        String prefix = annotation.prefix();

        switch (annotation.type()) {
            case PARAM:
                return prefix + request.getRequestURI() + ":" + buildParams(args);
            case HEADER:
                String token = request.getHeader(annotation.headerName());
                return prefix + token;
            case TOKEN:
                String idempotentToken = request.getHeader(annotation.headerName());
                if (idempotentToken == null) {
                    throw new IdempotentException("缺少幂等Token");
                }
                return prefix + idempotentToken;
            case CUSTOM:
                return prefix + annotation.key();
            default:
                throw new IllegalArgumentException("未知幂等类型");
        }
    }

    private String buildParams(Object[] args) {
        try {
            return objectMapper.writeValueAsString(args);
        } catch (Exception e) {
            return Arrays.toString(args);
        }
    }
}
