package com.vortexlab.message.util;

import java.util.UUID;

public class MessageIdGenerator {

    public static String generate() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
