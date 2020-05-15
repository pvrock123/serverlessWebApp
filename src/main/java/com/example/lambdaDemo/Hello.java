package com.example.lambdaDemo;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class Hello implements Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    @Override
    public APIGatewayProxyResponseEvent apply(APIGatewayProxyRequestEvent input) {
        APIGatewayProxyResponseEvent responseEvent=new APIGatewayProxyResponseEvent();
        responseEvent.setStatusCode(200);
        responseEvent.setBody("hello pvrock "+input.getBody());
        return responseEvent;
    }
}
