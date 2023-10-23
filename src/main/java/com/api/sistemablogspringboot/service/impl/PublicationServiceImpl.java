package com.api.sistemablogspringboot.service.impl;

import com.api.sistemablogspringboot.dto.PublicationDTO;
import com.api.sistemablogspringboot.dto.PublicationResponse;
import com.api.sistemablogspringboot.entities.Publication;
import com.api.sistemablogspringboot.repository.IPublicationRepository;
import com.api.sistemablogspringboot.service.IPublicationService;
import org.modelmapper.ModelMapper;
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
    private ModelMapper modelMapper;
    @Autowired
    private IPublicationRepository publicationRepository;


    @Override
    public PublicationDTO createPublication(PublicationDTO publicationDTO) {
        if(isBlankPublicDTO(publicationDTO)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Is blank publicationDTO");
        }
        Publication publication = mapEntity(publicationDTO);
        Publication publicationSave = publicationRepository.save(publication);
        return mapDTO(publicationSave);
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
    public PublicationDTO updatePublication(PublicationDTO publicationDTO, Long publicationId){
    Publication publication = publicationRepository.findById(publicationId)
            .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Non-existent"));
    if(isBlankPublicDTO(publicationDTO)){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"is blank publicationDTO");
    }
    publication.setTitle(publicationDTO.getTitle());
    publication.setDescription(publicationDTO.getDescription());
    publication.setContent(publicationDTO.getContent());
    return mapDTO(publication);
    }
    @Override
    public void deletePublication(Long id) {
        publicationRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Id no existe"));
        publicationRepository.deleteById(id);
    }
    /* *Funcion destinada a mapear un DTO*/
    private PublicationDTO mapDTO(Publication publication){
        PublicationDTO publicationDTO = modelMapper.map(publication,PublicationDTO.class);
        return publicationDTO;
    }
    private Publication mapEntity(PublicationDTO publicationDTO){
        Publication publication = modelMapper.map(publicationDTO, Publication.class);
        return publication;
    }
    private boolean isBlankPublicDTO(PublicationDTO publicationDTO){
        return publicationDTO == null ||
                publicationDTO.getDescription() == null || publicationDTO.getDescription().isBlank() ||
                publicationDTO.getTitle() == null || publicationDTO.getTitle().isBlank() ||
                publicationDTO.getContent() == null || publicationDTO.getContent().isBlank();
    }
}
