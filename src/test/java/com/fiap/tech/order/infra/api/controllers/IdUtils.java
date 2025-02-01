package com.fiap.tech.order.infra.api.controllers;

import java.util.UUID;

public class IdUtils {
    public static String uuid() {
        return UUID.randomUUID().toString();
    }
}
