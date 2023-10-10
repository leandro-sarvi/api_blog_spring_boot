package com.api.sistemablogspringboot.service.impl;

import com.api.sistemablogspringboot.dto.CommentDTO;
import com.api.sistemablogspringboot.entities.Comments;
import com.api.sistemablogspringboot.entities.Publication;
import com.api.sistemablogspringboot.repository.ICommentRepository;
import com.api.sistemablogspringboot.repository.IPublicationRepository;
import com.api.sistemablogspringboot.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CommentServiceImpl implements ICommentService {
    @Autowired
    private ICommentRepository commentRepository;
    @Autowired
    private IPublicationRepository publicationRepository;

    @Override
    public CommentDTO saveComment(Long publicationId, CommentDTO commentDTO) {
        Comments comments = mapEntity(commentDTO);
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Non-existent"));
        comments.setPublication(publication);
        Comments newComment = commentRepository.save(comments);
        return mapDTO(newComment);
    }

    @Override
    public CommentDTO findById(Long id) {
        Comments comments = commentRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Non-existent"));
        CommentDTO commentDTO = mapDTO(comments);
        return commentDTO;
    }
    @Override
    public void DeleteById(Long id) {
        Comments comments = commentRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Non-existent"));
        commentRepository.deleteById(id);
    }
    private CommentDTO mapDTO(Comments comments) {
        CommentDTO commentDTO = CommentDTO.builder()
                .id(comments.getId())
                .name(comments.getName())
                .email(comments.getEmail())
                .body(comments.getBody())
                .build();
        return commentDTO;
    }
    private Comments mapEntity(CommentDTO commentDTO) {
        Comments comments = Comments.builder()
                .id(commentDTO.getId())
                .name(commentDTO.getName())
                .email(commentDTO.getEmail())
                .body(commentDTO.getBody())
                .build();
        return comments;
    }
}
