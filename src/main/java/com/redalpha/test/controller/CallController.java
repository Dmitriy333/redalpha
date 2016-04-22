package com.redalpha.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.redalpha.test.model.Call;
import com.redalpha.test.service.CallService;

@RestController
@RequestMapping("/calls")
public class CallController {

    @Autowired
    private CallService callService;

    @RequestMapping(value = PathMapping.DEFAULT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Call> getAllCalls() {
        return callService.getAllCalls();
    }

    @RequestMapping(value = PathMapping.DEFAULT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Call writeCall(@RequestBody Call call) {
        callService.writeCall(call);
        return call;
    }
}
