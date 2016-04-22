package com.redalpha.test.service.call;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.redalpha.test.model.call.Call;
import com.redalpha.test.repository.call.CallRepository;
import com.redalpha.test.repository.call.CallRepositoryException;
import com.redalpha.test.service.EntityValidationException;
import com.redalpha.test.service.ServiceException;
import com.redalpha.test.util.phone.PhoneParser;

@RunWith(MockitoJUnitRunner.class)
public class CallServiceTest {
    @Mock
    private CallRepository callRepository;
    @Spy
    private CallValidationService callValidationService;
    @Mock
    private PhoneParser phoneParser;
    @InjectMocks
    private CallService callService = new CallServiceImpl();

    private static final String FORMATED_PHONE = "00420 123 456 780";
    private Call call;
    private List<Call> callList;

    @Before
    public void init() {
        call = new Call("123456780", "firstname2", "lastname2", new Date());
        callList = new ArrayList<Call>() {
            private static final long serialVersionUID = 1L;
            {
                add(new Call("123456789", "firstname", "lastname", new Date()));
                add(new Call("123456780", "firstname2", "lastname2", new Date()));
            }
        };
    }

    @Test
    public void addCallTest() throws CallRepositoryException, ServiceException, EntityValidationException {
        when(phoneParser.parsePhone(call.getPhone())).thenReturn(FORMATED_PHONE);
        when(callRepository.saveCall(call)).thenReturn(call);

        String initialPhone = call.getPhone();
        callService.addCall(call);

        verify(phoneParser, times(1)).parsePhone(initialPhone);
        assertEquals(call.getPhone(), FORMATED_PHONE);
        verify(callRepository, times(1)).saveCall(call);
    }

    @Test(expected = ServiceException.class)
    public void addCallThrowsServiceExceptionTest()
            throws ServiceException, EntityValidationException, CallRepositoryException {
        when(callRepository.saveCall(call)).thenThrow(new CallRepositoryException());
        callService.addCall(call);
        verify(callRepository, times(1)).saveCall(call);
    }

    @Test
    public void getAllCallsTest() throws CallRepositoryException, ServiceException, EntityValidationException {
        when(callRepository.loadCalls()).thenReturn(callList);

        List<Call> actualCalls = callService.getAllCalls();

        verify(callRepository, times(1)).loadCalls();
        assertEquals(actualCalls.size(), callList.size());
    }

    @Test(expected = ServiceException.class)
    public void getAllCallsTestThrowsServiceException()
            throws CallRepositoryException, ServiceException, EntityValidationException {
        when(callRepository.loadCalls()).thenThrow(new CallRepositoryException());

        callService.getAllCalls();

        verify(callRepository, times(1)).loadCalls();
    }
}
