package com.clone.instagram.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostCursor {
    private Long id;
    private LocalDateTime createdAt;
}