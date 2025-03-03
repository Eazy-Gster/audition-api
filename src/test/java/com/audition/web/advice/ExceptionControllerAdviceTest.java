package com.audition.web.advice;

import com.audition.common.exception.SystemException;
import lombok.Getter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class ExceptionControllerAdviceTest {

    @Autowired
    @Getter
    private ExceptionControllerAdvice advice;

    @Test
    void testSystemException()
        throws Exception {
        final ResponseEntity<String> entity = advice.handleSystemException(
            new SystemException("Critical error", "API error", new RuntimeException("Failed to get post")));
        Assertions.assertThat(entity.getBody())
            .isEqualTo("API Error occurred. Please contact support or administrator.");
    }

    @Test
    void testMainException()
        throws Exception {
        final ResponseEntity<String> entity = advice.handleMainException(
            new Exception());
        Assertions.assertThat(entity.getBody())
            .isEqualTo("API Error occurred. Please contact support or administrator.");
    }

    @Test
    void testSystemExceptionUnknownStatus()
        throws Exception {
        final ResponseEntity<String> entity = advice.handleSystemException(
            new SystemException("Critical error", 1234, new RuntimeException("Failed to get post")));
        Assertions.assertThat(entity.getBody())
            .isEqualTo("API Error occurred. Please contact support or administrator.");
    }
}
