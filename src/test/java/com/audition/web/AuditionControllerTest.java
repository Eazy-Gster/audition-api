package com.audition.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.audition.model.AuditionPost;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.Getter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class AuditionControllerTest {

    @Autowired
    @Getter
    private MockMvc mvc;

    @Test
    void testGetPosts()
        throws Exception {
        final MvcResult result = mvc.perform(get("/posts")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
        final ObjectMapper mapper = new ObjectMapper();
        final List<AuditionPost> posts = mapper.readValue(result.getResponse().getContentAsString(),
            new TypeReference<List<AuditionPost>>() {
            });
        Assertions.assertThat(posts).hasSizeGreaterThan(0);
        Assertions.assertThat(posts.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(posts.get(0).getUserId()).isEqualTo(1);
        Assertions.assertThat(posts.get(0).getTitle())
            .isEqualTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit");

    }

    @Test
    void testGetPost()
        throws Exception {
        final MvcResult result = mvc.perform(get("/posts/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
        final ObjectMapper mapper = new ObjectMapper();
        final AuditionPost post = mapper.readValue(result.getResponse().getContentAsString(), AuditionPost.class);
        Assertions.assertThat(post.getId()).isEqualTo(1);
        Assertions.assertThat(post.getUserId()).isEqualTo(1);
        Assertions.assertThat(post.getTitle())
            .isEqualTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit");
        Assertions.assertThat(post.getBody())
            .isEqualTo(
                "quia et suscipit\n"
                    + "suscipit recusandae consequuntur expedita et cum\n"
                    + "reprehenderit molestiae ut ut quas totam\n"
                    + "nostrum rerum est autem sunt rem eveniet architecto");
        Assertions.assertThat(post.getComments()).isNull();
    }

    @Test
    void testGetPostByUser()
        throws Exception {
        final MvcResult result = mvc.perform(get("/posts?userId=4")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
        final ObjectMapper mapper = new ObjectMapper();
        final List<AuditionPost> posts = mapper.readValue(result.getResponse().getContentAsString(),
            new TypeReference<List<AuditionPost>>() {
            });
        Assertions.assertThat(posts).hasSize(10);
        Assertions.assertThat(posts.get(0).getUserId()).isEqualTo(4);
    }

    @Test
    void testGetPostWithComments()
        throws Exception {
        final MvcResult result = mvc.perform(get("/posts/2?comments=true")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
        final ObjectMapper mapper = new ObjectMapper();
        final AuditionPost post = mapper.readValue(result.getResponse().getContentAsString(), AuditionPost.class);
        Assertions.assertThat(post.getTitle()).isEqualTo("qui est esse");
        Assertions.assertThat(post.getComments()).hasSizeGreaterThan(0);
    }

    @Test
    void testGetPostNotFound()
        throws Exception {

        final MvcResult result = mvc.perform(get("/posts/8888")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn();

        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(404);
    }
}
