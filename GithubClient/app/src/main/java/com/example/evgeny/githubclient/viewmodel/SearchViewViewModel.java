package com.example.evgeny.githubclient.viewmodel;

import android.support.v7.widget.SearchView;

import com.example.evgeny.githubclient.model.ui.UIAction;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Evgeny on 13.04.18.
 */

public class SearchViewViewModel extends RootViewModel {
    public static final int SEARCH_DELAY = 300;
    public static final String ACTION_SEARCH = "ACTION_SEARCH";

    @Override
    public void registerListener(Consumer<UIAction> consumer) {
        if (disposable == null) {
            disposable = new CompositeDisposable();
        }

        Observable<UIAction> debounced = processor.debounce(SEARCH_DELAY, TimeUnit.MILLISECONDS);
        Disposable subscribe = debounced.subscribe(consumer);

        disposable.add(subscribe);
    }

    public SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            UIAction uiAction = new UIAction(ACTION_SEARCH, s);
            processor.onNext(uiAction);
            return false;
        }
    };

}
