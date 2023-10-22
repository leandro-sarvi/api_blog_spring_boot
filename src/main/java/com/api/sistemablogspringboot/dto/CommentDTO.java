package com.api.sistemablogspringboot.dto;

import com.api.sistemablogspringboot.entities.Publication;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {
    private Long id;
    private String name;
    private String email;
    private String body;
    private Publication publication;
}
