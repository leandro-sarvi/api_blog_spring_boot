package com.api.sistemablogspringboot.controller;

import com.api.sistemablogspringboot.dto.PublicationDTO;
import com.api.sistemablogspringboot.dto.PublicationResponse;
import com.api.sistemablogspringboot.service.IPublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PublicationController {
    @Autowired
    private IPublicationService publicationService;
    @GetMapping("/publications")
    public ResponseEntity<PublicationResponse> findAll(
             @RequestParam(value = "pageNo",defaultValue = "0",required = false) int pageNo,
             @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
             @RequestParam(value = "sortBy",defaultValue = "id",required = false) String orderBy,
             @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir){
        PublicationResponse publicationResponse = publicationService.findAll(pageNo,pageSize,orderBy,sortDir);
        if(publicationResponse == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return  ResponseEntity.status(HttpStatus.OK).body(publicationResponse);
    }
    @GetMapping("/publication/{id}")
    public ResponseEntity<PublicationDTO> findById(@PathVariable Long id){
        PublicationDTO publicationDTO = publicationService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(publicationDTO);
    }
    @PostMapping("/publication")
    public ResponseEntity<PublicationDTO> savePublication(@RequestBody PublicationDTO publicationDTO){
        if(publicationDTO == null || publicationDTO.getContent().isBlank() || publicationDTO.getTitle().isBlank()
        || publicationDTO.getDescription().isBlank()){
            return ResponseEntity.badRequest().build();
        }
        PublicationDTO savePublication = publicationService.createPublication(publicationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savePublication);
    }
    @PutMapping("/publication/{id}")
    public ResponseEntity<PublicationDTO> updatePublication(@RequestBody PublicationDTO publicationDTO,@PathVariable Long id){
        if(publicationDTO == null || publicationDTO.getTitle().isBlank() || publicationDTO.getContent().isBlank() ||
        publicationDTO.getDescription().isBlank()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        PublicationDTO publicationDTOSave = publicationService.updatePublication(publicationDTO, id);
        return  ResponseEntity.status(HttpStatus.OK).body(publicationDTOSave);
    }

    @DeleteMapping("/publication/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
            publicationService.deletePublication(id);
            return ResponseEntity.status(HttpStatus.OK).body("Eliminado correctamente");
    }
    /* *minuto 1:51*/
}
