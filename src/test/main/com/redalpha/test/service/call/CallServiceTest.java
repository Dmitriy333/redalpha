package com.redalpha.test.service.call;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.redalpha.test.model.call.Call;
import com.redalpha.test.repository.call.CallRepository;
import com.redalpha.test.repository.call.CallRepositoryException;
import com.redalpha.test.util.phone.PhoneParser;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("deprecation")
public class CallServiceTest {
    @Mock
    private CallRepository callRepository;
    @Spy
    private CallValidationService callValidationService;
    @Mock
    private PhoneParser phoneParser;
    @InjectMocks
    private CallService callService = new CallServiceImpl();

    private static final Call CREATE_CALL = new Call("123456780", "firstname2", "lastname2", new Date());
    private static final List<Call> CALL_LIST = new ArrayList<Call>() {
        private static final long serialVersionUID = 1L;

        {
            add(new Call("123456789", "firstname", "lastname", new Date()));
            add(new Call("123456780", "firstname2", "lastname2", new Date()));
        }
    };

    @Test
    public void createCallTest() throws CallRepositoryException {
        when(callRepository.writeCall(CREATE_CALL)).thenReturn(CREATE_CALL);
        when(callRepository.writeCall(CREATE_CALL)).thenReturn(CREATE_CALL);
        /*(callValidationService.validateCall(CREATE_CALL)).thenReturn();*/
    }

}
