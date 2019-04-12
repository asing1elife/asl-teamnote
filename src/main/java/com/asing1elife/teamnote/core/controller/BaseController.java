package com.asing1elife.teamnote.core.controller;

import com.asing1elife.teamnote.core.bean.ResponseData;
import com.asing1elife.teamnote.core.handle.ControllerHandler;
import com.asing1elife.teamnote.core.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

public class BaseController<T, Service extends BaseService> {

    @Autowired
    protected Service service;

    @GetMapping("")
    public ResponseData list() {
        return new ControllerHandler() {
            @Override
            public void doHandler(ResponseData responseData) {
                responseData.setData(service.findAll());
            }
        }.handle();
    }

    @GetMapping("/{id}")
    public ResponseData get(@PathVariable Long id) {
        return new ControllerHandler() {
            @Override
            public void doHandler(ResponseData responseData) {
                responseData.setData(service.getOne(id));
            }
        }.handle();
    }

    @PutMapping("")
    public ResponseData save(@RequestBody T t) {
        return new ControllerHandler() {
            @Override
            public void doHandler(ResponseData responseData) {
                responseData.setData(service.save(t));
            }
        }.handle();
    }

    @DeleteMapping("/{id}")
    public ResponseData delete(@PathVariable Long id) {
        return new ControllerHandler() {
            @Override
            public void doHandler(ResponseData responseData) {
                service.delete(id);
            }
        }.handle();
    }

}
