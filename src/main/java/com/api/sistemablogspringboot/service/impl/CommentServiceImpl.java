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
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Non-existent"));
        comments.setPublication(publication);
        Comments commentSave = commentRepository.save(comments);
        return  mapDTO(commentSave);
    }

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
    /* * PARA LA BUSQUEDA Y ELIMINACION DE UN COMENTARIO, UTILIZAMOS LA DOBLE VERIFICACION
    * VERIFICAMOS ID_COMMMENT Y ID_PUBLICATION*/

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
