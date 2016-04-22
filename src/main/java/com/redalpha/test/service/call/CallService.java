package com.redalpha.test.service.call;

import java.util.List;

import com.redalpha.test.model.call.Call;
import com.redalpha.test.service.EntityValidationException;
import com.redalpha.test.service.ServiceException;

public interface CallService {
    public Call createCall(Call call) throws ServiceException, EntityValidationException;

    public List<Call> getAllCalls() throws ServiceException;
}
