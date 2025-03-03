package com.audition.service;

import com.audition.integration.AuditionIntegrationClient;
import com.audition.model.AuditionComment;
import com.audition.model.AuditionPost;
import java.util.List;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditionService {

    @Autowired
    @Getter
    private AuditionIntegrationClient auditionIntegrationClient;

    public List<AuditionPost> getPosts(final Integer userId) {
        return auditionIntegrationClient.getPosts(userId);
    }

    public AuditionPost getPostById(final Integer postId, final boolean comments) {
        if (comments) {
            return auditionIntegrationClient.getPostWithCommentsById(postId);
        } else {
            return auditionIntegrationClient.getPostById(postId);
        }
    }

    public List<AuditionComment> getCommentsByPostId(final Integer postId) {
        return auditionIntegrationClient.getCommentsByPostId(postId);
    }

}
