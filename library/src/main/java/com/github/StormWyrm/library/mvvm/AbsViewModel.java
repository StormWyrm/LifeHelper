package com.github.StormWyrm.library.mvvm;

import android.app.Application;

import com.github.StormWyrm.library.util.TUtil;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class AbsViewModel<T extends AbsRepository> extends AndroidViewModel {
    protected T mRepository;

    public AbsViewModel(@NonNull Application application) {
        super(application);
        mRepository = TUtil.getNewInstance(this, 0);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mRepository != null) {
            mRepository.unDisposable();
        }
    }
}
