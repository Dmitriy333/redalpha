package com.redalpha.test.controller.call;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.redalpha.test.controller.PathMapping;
import com.redalpha.test.model.call.Call;
import com.redalpha.test.service.EntityValidationException;
import com.redalpha.test.service.ServiceException;
import com.redalpha.test.service.call.CallService;

@RestController
@RequestMapping("/calls")
public class CallController {

    @Autowired
    private CallService callService;

    @RequestMapping(value = PathMapping.DEFAULT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Call>> getAllCalls() {
        try {
            HttpHeaders responseHeaders = new HttpHeaders();
            return new ResponseEntity<List<Call>>(callService.getAllCalls(), responseHeaders, HttpStatus.OK);
        } catch (ServiceException e) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("message", e.getMessage());
            return new ResponseEntity<List<Call>>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = PathMapping.DEFAULT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Call> addCall(@RequestBody Call call) {
        try {
            HttpHeaders responseHeaders = new HttpHeaders();
            return new ResponseEntity<Call>(callService.createCall(call), responseHeaders, HttpStatus.CREATED);
        } catch (EntityValidationException e) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("message", e.getMessage());
            return new ResponseEntity<Call>(null, responseHeaders, HttpStatus.BAD_REQUEST);
        } catch (ServiceException e) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("message", e.getMessage());
            return new ResponseEntity<Call>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
