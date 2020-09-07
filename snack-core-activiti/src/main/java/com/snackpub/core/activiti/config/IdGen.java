package com.snackpub.core.activiti.config;

import org.activiti.engine.impl.cfg.IdGenerator;

import java.util.UUID;

/**
 * @author snackpub
 * @date 2020/9/7
 */
public class IdGen implements IdGenerator {
    @Override
    public String getNextId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }
}
