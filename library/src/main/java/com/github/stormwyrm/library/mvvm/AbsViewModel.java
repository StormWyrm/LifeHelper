package com.github.stormwyrm.library.mvvm;

import androidx.lifecycle.ViewModel;

import com.github.stormwyrm.library.util.TUtil;

public class AbsViewModel<T extends AbsRepository> extends ViewModel {
    protected T mRepository;

    public AbsViewModel() {
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
