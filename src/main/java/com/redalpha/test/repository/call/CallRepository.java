package com.redalpha.test.repository.call;

import java.util.List;

import com.redalpha.test.model.call.Call;

public interface CallRepository {
    public Call writeCall(Call call) throws CallRepositoryException;

    public List<Call> readCalls() throws CallRepositoryException;
}
