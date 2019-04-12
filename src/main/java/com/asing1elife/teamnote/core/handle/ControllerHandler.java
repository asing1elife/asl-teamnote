package com.asing1elife.teamnote.core.handle;

import com.asing1elife.teamnote.core.bean.ResponseData;

public class ControllerHandler {

    public void doHandler(ResponseData responseData) {
        throw new RuntimeException("方法必须被重写！");
    }

    public ResponseData handle() {
        ResponseData responseData = new ResponseData();

        doHandler(responseData);

        return responseData;
    }

}
