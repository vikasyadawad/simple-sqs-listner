package com.example.demo;

import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy.ON_SUCCESS;

@Component
public class MyListener {

    @SqsListener(value = "${queueName}", deletionPolicy = ON_SUCCESS)
    public void listen(String message) {
        System.out.println(message);
    }
}
