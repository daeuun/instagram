package com.clone.instagram.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostSearchCondition {
    private PostCursor cursor;
    private Integer pageSize;
    private String sortBy;
    private String sortOrder;
}