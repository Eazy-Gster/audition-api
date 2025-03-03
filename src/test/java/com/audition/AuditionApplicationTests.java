package com.audition;

import com.audition.web.AuditionController;
import lombok.Getter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuditionApplicationTests {

    @Autowired
    @Getter
    private AuditionController controller;

    @Test
    void contextLoads() {
        Assertions.assertThat(controller).isNotNull();
    }
}
