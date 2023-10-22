package com.api.sistemablogspringboot.service;

import com.api.sistemablogspringboot.dto.CommentDTO;

import javax.xml.stream.events.Comment;
import java.util.List;

public interface ICommentService {
    CommentDTO findById(Long publicationId, Long commentId);
    CommentDTO findId(Long commentId);
    List<CommentDTO> findAll(Long publicationId);
    CommentDTO saveComment(Long publicationId,CommentDTO commentDTO);
    CommentDTO updateComment(Long publicationId, Long commentId,CommentDTO commentDTO);
    void DeleteById(Long publicationId, Long commentId);
}
