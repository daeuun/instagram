package com.clone.instagram.domain.post.api;

import com.clone.instagram.domain.post.dto.CreatePostRequest;
import com.clone.instagram.domain.post.service.PostService;
import com.clone.instagram.domain.result.ResultCode;
import com.clone.instagram.domain.result.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/posts")
    public ResultResponse create(@RequestBody CreatePostRequest request) {
        postService.create(request);
        return ResultResponse.of(ResultCode.POST_CREATED_SUCCESSFULLY, true);
    }

}
