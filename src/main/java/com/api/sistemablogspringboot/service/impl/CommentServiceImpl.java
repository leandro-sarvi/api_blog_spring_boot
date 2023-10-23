package com.api.sistemablogspringboot.service.impl;

import com.api.sistemablogspringboot.dto.CommentDTO;
import com.api.sistemablogspringboot.entities.Comments;
import com.api.sistemablogspringboot.entities.Publication;
import com.api.sistemablogspringboot.repository.ICommentRepository;
import com.api.sistemablogspringboot.repository.IPublicationRepository;
import com.api.sistemablogspringboot.service.ICommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CommentServiceImpl implements ICommentService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ICommentRepository commentRepository;
    @Autowired
    private IPublicationRepository publicationRepository;
    /* * TO SEARCH AND DELETE A COMMENT, WE USE DOUBLE VERIFICATION
     * WE CHECK ID_COMMMENT AND ID_PUBLICATION*/


    @Override
    public CommentDTO findById(Long publicationId, Long commentId) {
        Comments comments = commentRepository.findById(commentId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Non-existent"));
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Non-existent"));
        if(!comments.getPublication().getId().equals(publication.getId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"he comment does not belong to the publication");
        }
        return mapDTO(comments);
    }
    @Override
    public CommentDTO findId(Long commentId) {
        Comments comments = commentRepository.findById(commentId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Non-existent"));
        return mapDTO(comments);
    }
    @Override
    public List<CommentDTO> findAll(Long publicationId) {
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Non-existent"));
        List<CommentDTO> commentDTOList = commentRepository.findAll()
                .stream().map(comments -> mapDTO(comments)).toList();
        return commentDTOList;
    }
    @Override
    public CommentDTO saveComment(Long publicationId, CommentDTO commentDTO) {
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Non-existent publication"));
        if(isBlankComment(commentDTO)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Is blank commentDTO");
        }
        Comments comments = mapEntity(commentDTO);
        comments.setPublication(publication);
        Comments commentSave = commentRepository.save(comments);
        return mapDTO(commentSave);
    }
    @Override
    public CommentDTO updateComment(Long publicationId, Long commentId, CommentDTO commentDTO) {
        Comments comments = commentRepository.findById(commentId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Non-existent comment"));
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Non-existent publication"));
        if(!comments.getPublication().getId().equals(publication.getId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The comment does not belong to the publication");
        }
        if(isBlankComment(commentDTO)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"CommentDTO is null");
        }
        comments.setBody(commentDTO.getBody());
        comments.setName(commentDTO.getName());
        comments.setEmail(commentDTO.getEmail());
        Comments commentUpdate = commentRepository.save(comments);
        return mapDTO(commentUpdate);
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
        CommentDTO commentDTO = modelMapper.map(comments, CommentDTO.class);
        return commentDTO;
    }
    private Comments mapEntity(CommentDTO commentDTO) {
        Comments comments = modelMapper.map(commentDTO, Comments.class);
        return comments;
    }
    private Boolean isBlankComment(CommentDTO commentDTO){
        /* * if(commentDTO == null ||
         *                 commentDTO.getName() == null || commentDTO.getName().isBlank()||
         *                 commentDTO.getBody() == null || commentDTO.getBody().isBlank() ||
         *                 commentDTO.getEmail() == null || commentDTO.getEmail().isBlank()){
         *             return true;
         *         }
         *         return  false; */
        return  commentDTO == null ||
                      commentDTO.getName() == null || commentDTO.getName().isBlank()||
                      commentDTO.getBody() == null || commentDTO.getBody().isBlank() ||
                      commentDTO.getEmail() == null || commentDTO.getEmail().isBlank();
    }
}
