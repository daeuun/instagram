package com.clone.instagram.domain.comment.api;

import com.clone.instagram.domain.comment.dto.CommentDto;
import com.clone.instagram.domain.comment.dto.CreateCommentRequest;
import com.clone.instagram.domain.comment.service.CommentService;
import com.clone.instagram.domain.result.ResultCode;
import com.clone.instagram.domain.result.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<ResultResponse> create(@RequestBody CreateCommentRequest request) {
        ResultResponse result = ResultResponse.of(
                ResultCode.CREATE_COMMENT_SUCCESSFULLY,
                commentService.create(request)
        );
        return new ResponseEntity(result, HttpStatusCode.valueOf(result.getStatus()));
    }

    @GetMapping("/comment/{postId}")
    public ResponseEntity<ResultResponse> getComments(@PathVariable Long postId) {
        ResultResponse result = ResultResponse.of(
                ResultCode.GET_COMMENTS_SUCCESSFULLY,
                commentService.getComments(postId)
        );
        return new ResponseEntity(result, HttpStatusCode.valueOf(result.getStatus()));
    }

    @PutMapping("/comment/{commentId}")
    public ResponseEntity<ResultResponse> update(@PathVariable Long commentId, @RequestBody CommentDto request) {
        ResultResponse result = ResultResponse.of(
                ResultCode.UPDATE_COMMENT_SUCCESSFULLY,
                commentService.update(commentId, request)
        );
        return new ResponseEntity(result, HttpStatusCode.valueOf(result.getStatus()));
    }

}