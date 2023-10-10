package com.api.sistemablogspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicationDTO {
    private Long id;
    private String title;
    private String content;
    private String description;
}
