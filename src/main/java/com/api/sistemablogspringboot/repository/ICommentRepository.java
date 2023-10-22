package com.api.sistemablogspringboot.repository;

import com.api.sistemablogspringboot.entities.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentRepository extends JpaRepository<Comments,Long> {
}
