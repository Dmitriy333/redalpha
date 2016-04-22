package com.redalpha.test.service.call;

import java.util.List;

import com.redalpha.test.model.call.Call;
import com.redalpha.test.service.EntityValidationException;
import com.redalpha.test.service.ServiceException;

/**
 * 
 *  Service for call entity.
 * 
 */
public interface CallService {
    /**
     * Creates new call.
     * @param call call to add.
     * @return added call.
     * @throws ServiceException
     * @throws EntityValidationException if entity is not valid.
     */
    public Call addCall(Call call) throws ServiceException, EntityValidationException;

    /**
     * Retrieves all calls.
     * @return list of calls.
     * @throws ServiceException
     */
    public List<Call> getAllCalls() throws ServiceException;
}
