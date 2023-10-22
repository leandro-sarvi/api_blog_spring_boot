package com.api.sistemablogspringboot.controller;

import com.api.sistemablogspringboot.dto.CommentDTO;
import com.api.sistemablogspringboot.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
 @RequestMapping("/api/v1")
public class CommentController {
    @Autowired
    private ICommentService commentService;
    @GetMapping("/publication/{publicationId}/comments")
    public ResponseEntity<List<CommentDTO>> findAll(@PathVariable Long publicationId){
        List<CommentDTO> commentDTOList = commentService.findAll(publicationId);
        return ResponseEntity.status(HttpStatus.OK).body(commentDTOList);
    }
    @GetMapping("/publication/{publicationId}/comment/{commentId}")
    public ResponseEntity<CommentDTO> findById(@PathVariable  Long publicationId,@PathVariable Long commentId){
        CommentDTO commentDTO = commentService.findById(publicationId,commentId);
        return  ResponseEntity.status(HttpStatus.OK).body(commentDTO);
    }
    @GetMapping("/comment/{commentId}")
    public ResponseEntity<CommentDTO> findId(@PathVariable Long commentId){
        CommentDTO commentDTO = commentService.findId(commentId);
        return ResponseEntity.status(HttpStatus.OK).body(commentDTO);
    }
    @PostMapping("/publication/{publicationId}/comment")
    public ResponseEntity<CommentDTO> save(@RequestBody CommentDTO commentDTO, @PathVariable Long publicationId){
        CommentDTO commentSave = commentService.saveComment(publicationId,commentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentSave);
    }
    @PutMapping("/publication/{publicationId}/comment/{commentId}")
    public  ResponseEntity<CommentDTO> updateComment(
            @PathVariable Long commentId,
            @PathVariable Long publicationId,
            @RequestBody CommentDTO commentDTO){
        CommentDTO commentDTOUpdate = commentService.updateComment(commentId,publicationId,commentDTO);
        return ResponseEntity.status(HttpStatus.OK).body(commentDTOUpdate);
    }
    @DeleteMapping("/publication/{publicationId}/comment/{commentId}")
    public ResponseEntity<String> deleteById(@PathVariable Long publicationId,@PathVariable Long commentId){
        commentService.DeleteById(publicationId, commentId);
        return ResponseEntity.status(HttpStatus.OK).body("comment successfully deleted");
    }
    /* *minuto 2:40*/
}
