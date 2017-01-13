package org.xuxiaoxiao.myyora.services;

import org.xuxiaoxiao.myyora.infrastructure.YoraApplication;

public class Module {
    public static void register(YoraApplication application) {
        // It won't be garbage collected because Bus holds a reference to it
        new org.xuxiaoxiao.myyora.services.InMemoryAccountService(application);
        new org.xuxiaoxiao.myyora.services.InMemoryContactService(application);
    }
}