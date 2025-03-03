package com.audition.integration;

import com.audition.model.AuditionComment;
import com.audition.model.AuditionPost;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuditionIntegrationClient {

    @Autowired
    @Getter
    private RestTemplate restTemplate;

    static final String URL = "https://jsonplaceholder.typicode.com/posts/";

    public List<AuditionPost> getPosts(final Integer userId) {
        final ResponseEntity<AuditionPost[]> response
            = restTemplate.getForEntity(URL, AuditionPost[].class);

        List<AuditionPost> posts =
            response.getBody() != null ? Arrays.asList(response.getBody()) : new ArrayList<>();
        if (userId != null) {
            posts = posts.stream()
                .filter(p -> p.getUserId() == userId)
                .collect(Collectors.toList());
        }
        return posts;
    }

    public AuditionPost getPostById(final Integer id) {
        final ResponseEntity<AuditionPost> response
            = restTemplate.getForEntity(URL + id, AuditionPost.class);

        return response.getBody();
    }

    public AuditionPost getPostWithCommentsById(final Integer id) {
        final AuditionPost post = getPostById(id);
        post.setComments(getCommentsByPostId(id));
        return post;
    }

    public List<AuditionComment> getCommentsByPostId(final Integer id) {
        final ResponseEntity<AuditionComment[]> response
            = restTemplate.getForEntity(URL + id + "/comments", AuditionComment[].class);

        return response.getBody() != null ? Arrays.asList(response.getBody()) : new ArrayList<>();
    }

}
