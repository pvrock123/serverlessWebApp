package com.example.lambdaDemo;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component("hello")
public class Hello implements Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final AmazonSNS snsClient;

    @Autowired
    public Hello(AmazonSNS snsClient) {
        this.snsClient = snsClient;
    }

    @Override
    public APIGatewayProxyResponseEvent apply(APIGatewayProxyRequestEvent input) {
        System.out.println("executing hello function.............");
        PublishResult result = snsClient.publish("arn:aws:sns:us-east-2:250722166795:demo", "Your order has been placed");
        System.out.println(result);
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        responseEvent.setStatusCode(200);
        responseEvent.setBody("your order has been placed");
        return responseEvent;
    }
}
