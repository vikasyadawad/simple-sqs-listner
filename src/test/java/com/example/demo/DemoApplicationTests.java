package com.example.demo;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.testcontainers.containers.localstack.LocalStackContainer.Service.SQS;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests extends AbstractContainerBaseTest {

	private static String myQueueUrl;
	private static AmazonSQS sqs;

	static {
		setUpSqs();
	}

	@Test
	public void shouldListenToQueueMessages() {
		sendQueueMessages("SomeGroup", "TestMessage");
		// TODO figure out way to test if sqs listener was called
	}

	private String sendQueueMessages(String group, String message) {
		final SendMessageRequest sendMessageRequest = new SendMessageRequest(myQueueUrl, message);
		sendMessageRequest.setMessageGroupId(group);
		final SendMessageResult sendMessageResult = sqs.sendMessage(sendMessageRequest);
		return sendMessageResult.getMessageId();
	}

	private static void setUpSqs() {
		sqs = AmazonSQSClientBuilder.standard()
				.withCredentials(LOCAL_STACK.getDefaultCredentialsProvider())
				.withEndpointConfiguration(LOCAL_STACK.getEndpointConfiguration(SQS))
				.build();

		final Map<String, String> attributes = new HashMap<>();
		attributes.put("FifoQueue", "true");
		attributes.put("ContentBasedDeduplication", "true");
		final CreateQueueRequest createQueueRequest = new CreateQueueRequest(queueName)
				.withAttributes(attributes);
		myQueueUrl = sqs.createQueue(createQueueRequest).getQueueUrl();
	}

}
