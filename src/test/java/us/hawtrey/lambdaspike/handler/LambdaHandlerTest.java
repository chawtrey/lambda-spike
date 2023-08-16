package us.hawtrey.lambdaspike.handler;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.amazonaws.serverless.proxy.internal.testutils.AwsProxyRequestBuilder;
import com.amazonaws.serverless.proxy.internal.testutils.MockLambdaContext;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;

@SpringBootApplication
class LambdaHandlerTest {

  private LambdaHandler subject;

  private final MockLambdaContext lambdaContext = new MockLambdaContext();

  @org.junit.jupiter.api.BeforeEach
  public void setUp() {
    subject = new LambdaHandler();
  }

  @Test
  void whenTheUsersPathIsInvokedViaLambda_thenShouldReturnAList() throws IOException {
    AwsProxyRequest req = new AwsProxyRequestBuilder("hello", "GET").build();

    AwsProxyResponse resp = subject.handleRequest(req, lambdaContext);

    Assertions.assertNotNull(resp.getBody());
    Assertions.assertEquals(200, resp.getStatusCode());
  }
}
