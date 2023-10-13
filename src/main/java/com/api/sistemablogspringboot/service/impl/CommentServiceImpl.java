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

import java.util.List;

@Service
public class CommentServiceImpl implements ICommentService {
    @Autowired
    private ICommentRepository commentRepository;
    @Autowired
    private IPublicationRepository publicationRepository;
    /* * TO SEARCH AND DELETE A COMMENT, WE USE DOUBLE VERIFICATION
     * WE CHECK ID_COMMMENT AND ID_PUBLICATION*/
    @Override
    public CommentDTO findById(Long commentId, Long publicationId) {
        Comments comments = commentRepository.findById(commentId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Non-existent"));
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Non-existent"));
        if(!comments.getPublication().getId().equals(publication.getId())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"The comment id does not match the publication id");
        }
        return mapDTO(comments);
    }
    @Override
    public List<CommentDTO> findAll(Long publicationId) {
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Non-existent"));
        List<CommentDTO> commentDTOList = publication.getComments().stream()
                .map(comments -> mapDTO(comments)).toList();
        return commentDTOList;
    }
    @Override
    public CommentDTO saveComment(Long publicationId, CommentDTO commentDTO) {
        Comments comments = mapEntity(commentDTO);
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Non-existent"));
        comments.setPublication(publication);
        Comments commentSave = commentRepository.save(comments);
        return  mapDTO(commentSave);
    }

    @Override
    public CommentDTO updateComment(Long publicationId, Long commentId, CommentDTO commentDTO) {
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Non-existent"));
        Comments comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Non-existent"));
        if(!comment.getPublication().getId().equals(publication.getId())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"The comment id does not match the publication id");
        }
        comment.setPublication(publication);
        comment.setBody(commentDTO.getBody());
        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        commentRepository.save(comment);
        return mapDTO(comment);
    }
    @Override
    public void DeleteById(Long commentId, Long publicationId) {
        Comments comments = commentRepository.findById(commentId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Non-existent"));
        Publication publication = publicationRepository.findById(publicationId)
                        .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Non-existent"));
        if(!comments.getPublication().getId().equals(publication.getId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The comment does not belong to the publication");
        }
        commentRepository.deleteById(commentId);
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
