package com.github.stormwyrm.library.mvp;

public interface IListView<P extends IPresenter> extends IView<P> {
    void loadDataError();

    void noData();

    void loadMoreError();

    void noMoreData();
}
