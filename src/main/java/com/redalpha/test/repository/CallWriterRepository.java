package com.redalpha.test.repository;

import java.util.List;

import com.redalpha.test.model.Call;

public interface CallWriterRepository {
    public void writeCall(Call call) throws CallRepositoryException;

    public List<Call> readCalls() throws CallRepositoryException;
}
