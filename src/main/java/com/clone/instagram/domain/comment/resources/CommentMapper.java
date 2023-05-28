package com.clone.instagram.domain.comment.resources;

import com.clone.instagram.domain.comment.dto.CommentCreateRequest;
import com.clone.instagram.domain.comment.models.Comment;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CommentMapper {

    @Mapping(target = "replies", ignore = true)
    Comment from(CommentCreateRequest request);

}
