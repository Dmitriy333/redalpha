package com.redalpha.test.service.call;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redalpha.test.model.call.Call;
import com.redalpha.test.repository.call.CallRepository;
import com.redalpha.test.repository.call.CallRepositoryException;
import com.redalpha.test.service.EntityValidationException;
import com.redalpha.test.service.ServiceException;
import com.redalpha.test.util.phone.PhoneParser;

@Service
public class CallServiceImpl implements CallService {

    @Autowired
    private CallRepository callWriterRepository;
    @Autowired
    private CallValidationService callValidationService;
    @Autowired
    private PhoneParser phoneParser;

    @Override
    public Call addCall(Call call) throws ServiceException, EntityValidationException {
        try {
            callValidationService.validateCall(call);
            call.setPhone(phoneParser.parsePhone(call.getPhone()));
            return callWriterRepository.saveCall(call);
        } catch (CallRepositoryException e) {
            throw new ServiceException("Can not write call.", e);
        }
    }

    @Override
    public List<Call> getAllCalls() throws ServiceException {
        try {
            return callWriterRepository.loadCalls();
        } catch (CallRepositoryException e) {
            throw new ServiceException("Can not get calls.", e);
        }
    }
}
