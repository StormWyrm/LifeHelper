package com.github.StormWyrm.library.mvp;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class IModel {
    private CompositeDisposable compositeDisposable;

    public void addDispose(Disposable disposable){
        if(compositeDisposable == null){
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    public void unDisposable(){
        if(compositeDisposable != null && compositeDisposable.isDisposed()){
            compositeDisposable.clear();
        }
    }
}
