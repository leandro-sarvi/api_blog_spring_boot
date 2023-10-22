package com.api.sistemablogspringboot.dto;

import com.api.sistemablogspringboot.entities.Comments;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicationDTO {
    private Long id;
    private String title;
    private String content;
    private String description;
    private List<Comments> comments;
}
