package com.clone.instagram.domain.post.api;

import com.clone.instagram.domain.post.dto.*;
import com.clone.instagram.domain.post.service.PostService;
import com.clone.instagram.domain.result.ResultCode;
import com.clone.instagram.domain.result.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PostController {
    private static final int DEFAULT_SIZE = 12;
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

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponse>> getPosts(@RequestParam Long userId,
                                                       @RequestBody(required = false) PostCursor cursor,
                                                       @RequestParam(required = false) Integer limit,
                                                       @RequestParam(required = false) String sortBy,
                                                       @RequestParam(required = false) String sortOrder) {
        if (cursor == null) {
            cursor = new PostCursor(Long.MAX_VALUE, LocalDateTime.now());
        }
        int pageSize = (limit != null) ? limit : DEFAULT_SIZE;
        PostSearchCondition condition = new PostSearchCondition(cursor, pageSize, sortBy, sortOrder);
        ResultResponse result = ResultResponse.of(
                ResultCode.GET_POSTS_SUCCESSFULLY,
                postService.getPosts(userId, condition)
        );
        return new ResponseEntity(result, HttpStatusCode.valueOf(result.getStatus()));
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ResultResponse> delete(@PathVariable Long postId) {
        ResultResponse result = ResultResponse.of(
                ResultCode.DELETE_POST_SUCCESSFULLY,
                postService.delete(postId)
        );
        return new ResponseEntity(result, HttpStatusCode.valueOf(result.getStatus()));
    }
}
