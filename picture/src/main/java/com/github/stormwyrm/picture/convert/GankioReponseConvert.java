package com.github.stormwyrm.picture.convert;

import com.github.stormwyrm.library.exception.ApiException;
import com.github.stormwyrm.picture.bean.BaseRepsonse;

import io.reactivex.functions.Function;


public class GankioReponseConvert<T> implements Function<BaseRepsonse<T>, T> {

    @Override
    public T apply(BaseRepsonse<T> tBaseRepsonse) {
        if (tBaseRepsonse.isError()) {
            throw new ApiException(-1, "服务器返回error");
        }
        return tBaseRepsonse.getResults();
    }
}
