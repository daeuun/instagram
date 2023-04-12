package com.clone.instagram.domain.post.api;

import com.clone.instagram.domain.post.dto.CreatePostRequest;
import com.clone.instagram.domain.post.dto.PostDto;
import com.clone.instagram.domain.post.service.PostService;
import com.clone.instagram.domain.result.ResultCode;
import com.clone.instagram.domain.result.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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

    @PutMapping("/posts/{postId}")
    public ResponseEntity<ResultResponse> update(@PathVariable Long postId, @RequestBody PostDto postDto) {
        ResultResponse result = ResultResponse.of(
                ResultCode.POST_UPDATE_SUCCESSFULLY,
                postService.update(postDto)
        );
        return new ResponseEntity(result, HttpStatusCode.valueOf(result.getStatus()));
    }

}
