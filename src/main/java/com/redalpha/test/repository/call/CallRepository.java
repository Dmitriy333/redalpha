package com.redalpha.test.repository.call;

import java.util.List;

import com.redalpha.test.model.call.Call;

/**
 * Call repository interface.
 *
 */
public interface CallRepository {
    /**
     * Saves call object.
     * 
     * @param call call object.
     * @return saved call object.
     * @throws CallRepositoryException
     */
    public Call saveCall(Call call) throws CallRepositoryException;

    /**
     * Loads calls.
     * 
     * @return list of loaded calls.
     * @throws CallRepositoryException
     */
    public List<Call> loadCalls() throws CallRepositoryException;
}
