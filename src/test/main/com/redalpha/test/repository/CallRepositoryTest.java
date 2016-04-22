package com.redalpha.test.repository;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import com.redalpha.test.model.call.Call;
import com.redalpha.test.repository.call.CallRepository;
import com.redalpha.test.repository.call.CallRepositoryException;
import com.redalpha.test.repository.call.FileRepository;

public class CallRepositoryTest {

    private CallRepository callRepository = new FileRepository();
    private Call call;

    @Before
    public void init() throws IOException {
        Whitebox.setInternalState(callRepository, "readWriteLock", new ReentrantReadWriteLock());
        call = new Call("00420 111 222 333", "Firstname", "Lastname", new Date());
    }

    @Test
    public void loadCalls() throws CallRepositoryException {
        Whitebox.setInternalState(callRepository, "callStorageLocation", "src/test/resources/calls");
        List<Call> calls = callRepository.loadCalls();
        assertEquals(calls.size(), 2);
    }

    @Test
    public void saveCall() throws CallRepositoryException, IOException {
        FileUtils.cleanDirectory(new File("src/test/resources/createcalls"));
        Whitebox.setInternalState(callRepository, "callStorageLocation", "src/test/resources/createcalls");
        Call actual = callRepository.saveCall(call);
        assertEquals(call, actual);
    }
}
