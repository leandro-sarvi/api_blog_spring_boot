package com.api.sistemablogspringboot.dto;

import lombok.Data;

import java.util.List;

@Data
public class PublicationResponse {
    private List<PublicationDTO> content;
    private int pageNo;
    private int pageSize;
    private long allElements;
    private int allPages;
    private boolean last;
}
