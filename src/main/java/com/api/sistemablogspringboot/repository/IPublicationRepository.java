package com.api.sistemablogspringboot.repository;

import com.api.sistemablogspringboot.entities.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPublicationRepository extends JpaRepository<Publication, Long> {
}
