package com.api.sistemablogspringboot.service;

import com.api.sistemablogspringboot.dto.PublicationDTO;
import com.api.sistemablogspringboot.dto.PublicationResponse;

import java.util.List;

public interface IPublicationService {
    PublicationDTO createPublication(PublicationDTO publicationDTO);
    PublicationResponse findAll(int pageNo, int pageSize, String orderBy,String sortDir);
    PublicationDTO findById(Long id);
    PublicationDTO updatePublication(PublicationDTO publicationDTO, Long id);
    void deletePublication(Long id);
}
