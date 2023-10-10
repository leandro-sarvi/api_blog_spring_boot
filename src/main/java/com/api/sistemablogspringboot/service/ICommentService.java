package com.api.sistemablogspringboot.service;

import com.api.sistemablogspringboot.dto.CommentDTO;

public interface ICommentService {
    CommentDTO saveComment(Long publicationId,CommentDTO commentDTO);
    CommentDTO findById(Long id);
    void DeleteById(Long id);
}
