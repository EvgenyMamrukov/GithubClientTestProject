package com.example.evgeny.githubclient.viewmodel;

import com.example.evgeny.githubclient.model.ui.UIAction;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Evgeny on 07.04.18.
 */

public class RootViewModel {
    protected PublishSubject<UIAction> processor = PublishSubject.create();
    protected CompositeDisposable disposable;

    public void registerListener(Consumer<UIAction> consumer) {
        if (disposable == null) {
            disposable = new CompositeDisposable();
        }
        Disposable subscribe = processor.subscribe(consumer);
        disposable.add(subscribe);
    }

    public void unregisterListener() {
        if (disposable == null) {
            return;

        }
        disposable.dispose();
        disposable = null;
        return;
    }
}
