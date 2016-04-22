package com.redalpha.test.service;

import java.util.List;

import com.redalpha.test.model.Call;

public interface CallService {
    public void writeCall(Call call);

    public List<Call> getAllCalls();
}
