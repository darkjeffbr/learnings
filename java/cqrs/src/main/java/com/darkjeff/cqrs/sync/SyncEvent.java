package com.darkjeff.cqrs.sync;

import org.springframework.context.ApplicationEvent;

public class SyncEvent extends ApplicationEvent {

    private static final Object EMPTY_OBJECT = new Object();
    public SyncEvent(Object source) {
        super(source);
    }

    public SyncEvent() {
        super(EMPTY_OBJECT);
    }
}
