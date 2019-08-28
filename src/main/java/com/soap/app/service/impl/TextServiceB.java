package com.soap.app.service.impl;

import com.soap.app.service.TestService;
import org.springframework.stereotype.Service;

@Service
public class TextServiceB implements TestService {
    @Override
    public int testMethod(String a) {
        System.out.println(a);
        return 2;
    }
}
