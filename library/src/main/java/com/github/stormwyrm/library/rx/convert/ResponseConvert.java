package com.github.stormwyrm.library.rx.convert;

import com.github.stormwyrm.library.bean.BaseResponse;
import com.github.stormwyrm.library.exception.ApiException;

import io.reactivex.functions.Function;

public class ResponseConvert<T> implements Function<BaseResponse<T>, T> {

    @Override
    public T apply(BaseResponse<T> tBaseResponse) throws Exception {
        if (tBaseResponse.getErrorCode() != 0) {
            throw new ApiException(tBaseResponse.getErrorCode(), tBaseResponse.getErrorMsg());
        }
        return tBaseResponse.getData();
    }
}
