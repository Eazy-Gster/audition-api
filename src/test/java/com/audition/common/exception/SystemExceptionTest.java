package com.audition.common.exception;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SystemExceptionTest {

    @Test
    void testSystemException() {
        final SystemException exception = new SystemException("Unable to get resource", "API error", 500,
            new Throwable());
        Assertions.assertThat(exception.getDetail()).isEqualTo("Unable to get resource");
        Assertions.assertThat(exception.getTitle()).isEqualTo("API error");
        Assertions.assertThat(exception.getStatusCode()).isEqualTo(500);
    }
}
