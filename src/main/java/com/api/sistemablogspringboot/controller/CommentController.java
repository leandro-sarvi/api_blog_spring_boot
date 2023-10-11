package com.api.sistemablogspringboot.controller;

import com.api.sistemablogspringboot.dto.CommentDTO;
import com.api.sistemablogspringboot.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
 @RequestMapping("/api/v1")
public class CommentController {
    @Autowired
    private ICommentService commentService;

    @GetMapping("/comment/{id}")
    public ResponseEntity<CommentDTO> save(@RequestBody CommentDTO commentDTO, @PathVariable Long id){
        CommentDTO commentSave = commentService.saveComment(id,commentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentSave);
    }
    @GetMapping("/publication/{publicationId}/comment/{commentId}")
    public ResponseEntity<CommentDTO> findById(@PathVariable  Long publicationId,@PathVariable Long commentId){
        CommentDTO commentDTO = commentService.findById(publicationId,commentId);
        return  ResponseEntity.status(HttpStatus.OK).body(commentDTO);
    }
    @DeleteMapping("/publication/{publicationId}/comment/{commentId}")
    public ResponseEntity<String> deleteById(@PathVariable Long publicationId,@PathVariable Long commentId){
        commentService.DeleteById(publicationId, commentId);
        return ResponseEntity.status(HttpStatus.OK).body("comment successfully deleted");
    }
    /* *minuto 1:56*/
}
