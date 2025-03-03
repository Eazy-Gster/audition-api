package com.audition.configuration;

import com.audition.common.logging.AuditionLogger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

@Component
public class LoggingInterceptor implements ClientHttpRequestInterceptor {

    @Autowired
    @Getter
    private AuditionLogger auditionLogger;

    private static final Logger LOG = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public ClientHttpResponse intercept(
        final HttpRequest req, final byte[] reqBody, final ClientHttpRequestExecution ex) throws IOException {
        getAuditionLogger().debug(LOG, "Request body: " + new String(reqBody, StandardCharsets.UTF_8));
        final ClientHttpResponse response = ex.execute(req, reqBody);
        try (InputStreamReader isr = new InputStreamReader(
            response.getBody(), StandardCharsets.UTF_8)) {
            final String body = new BufferedReader(isr).lines()
                .collect(Collectors.joining("\n"));
            getAuditionLogger().debug(LOG, "Response body: " + body);
        }

        return response;
    }
}