package com.api.sistemablogspringboot.service.impl;

import com.api.sistemablogspringboot.dto.PublicationDTO;
import com.api.sistemablogspringboot.dto.PublicationResponse;
import com.api.sistemablogspringboot.entities.Publication;
import com.api.sistemablogspringboot.repository.IPublicationRepository;
import com.api.sistemablogspringboot.service.IPublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class PublicationServiceImpl implements IPublicationService {
    @Autowired
    private IPublicationRepository publicationRepository;

    @Override
    public PublicationDTO createPublication(PublicationDTO publicationDTO) {
        Publication publication = mapEntitie(publicationDTO);

        Publication newPublication = publicationRepository.save(publication);

        PublicationDTO responseDTO  = mapDTO(newPublication);

        return responseDTO;
    }
    @Override
    public PublicationResponse findAll(int pageNo, int pageSize,String orderBy,String sortDir){
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(orderBy).ascending():Sort.by(orderBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);
        Page<Publication> publications = publicationRepository.findAll(pageable);

        List<PublicationDTO> contentList = publications.getContent()
                .stream().map(publication -> mapDTO(publication)).toList();

        PublicationResponse publicationResponse = new PublicationResponse();
        publicationResponse.setContent(contentList);
        publicationResponse.setPageNo(pageNo);
        publicationResponse.setPageSize(pageSize);
        publicationResponse.setAllElements(publications.getTotalElements());
        publicationResponse.setAllPages(publications.getTotalPages());
        publicationResponse.setLast(publications.isLast());

        return publicationResponse;
    }

    @Override
    public PublicationDTO findById(Long id) {
        PublicationDTO publicationDTO = mapDTO(publicationRepository.
                findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Non-existent")));
        return publicationDTO;
    }


    @Override
    public PublicationDTO updatePublication(PublicationDTO publicationDTO, Long id) {
        Publication publication = publicationRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Publication non-existent"));
        publication.setTitle(publicationDTO.getTitle());
        publication.setContent(publicationDTO.getContent());
        publication.setDescription(publication.getDescription());
        Publication publicationSave = publicationRepository.save(publication);
        return mapDTO(publicationSave);
    }

    @Override
    public void deletePublication(Long id) {
        publicationRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Id no existe"));
        publicationRepository.deleteById(id);
    }
    //Funcion destinada a mapear un DTO
    private PublicationDTO mapDTO(Publication publication){
        PublicationDTO publicationDTO = new PublicationDTO();
        publicationDTO.setId(publication.getId());
        publicationDTO.setTitle(publication.getTitle());
        publicationDTO.setContent(publication.getContent());
        publicationDTO.setDescription(publication.getDescription());
        return publicationDTO;
    }
    private Publication mapEntitie(PublicationDTO publicationDTO){
        Publication publication = new Publication();

        publication.setTitle(publicationDTO.getTitle());
        publication.setContent(publicationDTO.getContent());
        publication.setDescription(publicationDTO.getDescription());
        return publication;
    }

}
