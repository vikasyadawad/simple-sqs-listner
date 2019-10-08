package com.example.demo;

import org.testcontainers.containers.localstack.LocalStackContainer;

import static org.testcontainers.containers.localstack.LocalStackContainer.Service.SQS;

abstract class AbstractContainerBaseTest {

    static final LocalStackContainer LOCAL_STACK;
    static final String queueName = "test-queue.fifo";

    static {
        LOCAL_STACK = new LocalStackContainer()
                .withServices(SQS)
                .withExposedPorts(4576);

        LOCAL_STACK.start();
        System.setProperty("sqs.endpoint", "http://" + LOCAL_STACK.getContainerIpAddress() + ":" + LOCAL_STACK.getMappedPort(4576));
        System.setProperty("queueName", queueName);
    }
}
