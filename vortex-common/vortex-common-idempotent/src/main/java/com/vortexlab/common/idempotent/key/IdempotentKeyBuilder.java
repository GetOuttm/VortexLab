package com.vortexlab.common.idempotent.key;

import com.vortexlab.common.idempotent.annotation.Idempotent;
import jakarta.servlet.http.HttpServletRequest;

public interface IdempotentKeyBuilder {

    String builder(
            Idempotent annotation,
            HttpServletRequest request,
            Object[] args
    );
}
