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

    private static Call call;
    @SuppressWarnings("unused")
    private static List<Call> callList;
    private static final String FORMATED_PHONE = "00420 123 456 780";

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

    private static final List<Call> CALL_LIST = new ArrayList<Call>() {
        private static final long serialVersionUID = 1L;

    };

    @Test
    public void createCallTest() throws CallRepositoryException, ServiceException, EntityValidationException {
        when(phoneParser.parsePhone(call.getPhone())).thenReturn(FORMATED_PHONE);
        when(callRepository.writeCall(call)).thenReturn(call);

        String initialPhone = call.getPhone();
        callService.createCall(call);

        verify(phoneParser, times(1)).parsePhone(initialPhone);
        assertEquals(call.getPhone(), FORMATED_PHONE);
        verify(callRepository, times(1)).writeCall(call);
    }

    @Test(expected = ServiceException.class)
    public void createCallThrowsServiceExceptionTest() throws ServiceException, EntityValidationException,
            CallRepositoryException {
        when(callRepository.writeCall(call)).thenThrow(new CallRepositoryException());
        callService.createCall(call);
        verify(callRepository, times(1)).writeCall(call);
    }

    @Test
    public void getAllCallsTest() throws CallRepositoryException, ServiceException, EntityValidationException {
        when(callRepository.readCalls()).thenReturn(CALL_LIST);

        List<Call> actualCalls = callService.getAllCalls();

        verify(callRepository, times(1)).readCalls();
        assertEquals(actualCalls.size(), CALL_LIST.size());
    }

    @Test(expected = ServiceException.class)
    public void getAllCallsTestThrowsServiceException() throws CallRepositoryException, ServiceException,
            EntityValidationException {
        when(callRepository.readCalls()).thenThrow(new CallRepositoryException());

        callService.getAllCalls();

        verify(callRepository, times(1)).readCalls();
    }
}
