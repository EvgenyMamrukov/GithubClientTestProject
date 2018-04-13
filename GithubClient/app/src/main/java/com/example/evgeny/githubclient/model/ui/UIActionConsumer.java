package com.example.evgeny.githubclient.model.ui;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by Evgeny on 07.04.18.
 */

public class UIActionConsumer implements Consumer<UIAction> {

    private Map<String, Consumer<?>> handlerRegistry = new HashMap<>();

    public <T> UIActionConsumer register(String key, Consumer<T> handler) {
        handlerRegistry.put(key, handler);
        return this;
    }

    public UIActionConsumer register(String key, final Action action) {
        return register(key, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                action.run();
            }
        });
    }

    @Override
    public void accept(final UIAction uiAction) throws Exception {
        Consumer consumer = handlerRegistry.get(uiAction.getActionId());
        if (consumer != null) consumer.accept(uiAction.getData());
    }

}
