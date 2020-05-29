package com.example.lambdaDemo;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;

@Component("second")
public class SecondFunction implements Consumer<APIGatewayProxyRequestEvent> {

    @Value("${aws.sqs.url}")
    private String sqsUrl;
    private final AmazonSQS sqsClient;

    @Autowired
    public SecondFunction(AmazonSQS sqsClient) {
        this.sqsClient = sqsClient;
    }

    @Override
    public void accept(APIGatewayProxyRequestEvent empty) {
        System.out.println("Executing second function........");
        ReceiveMessageRequest messageRequest = new ReceiveMessageRequest(sqsUrl)
                .withWaitTimeSeconds(18)
                .withMaxNumberOfMessages(10);
        List<Message> messageList = sqsClient.receiveMessage(messageRequest).getMessages();
        System.out.println(messageList.toString());
        while (messageList.size() > 0) {
            for (Message message : messageList) {
                System.out.println("hello from second function: " + message.getBody());

                sqsClient.deleteMessage(new DeleteMessageRequest().withQueueUrl(sqsUrl)
                        .withReceiptHandle(message.getReceiptHandle()));
            }
            messageList = sqsClient.receiveMessage(messageRequest).getMessages();
        }
    }
}
