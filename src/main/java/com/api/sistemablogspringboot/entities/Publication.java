package com.api.sistemablogspringboot.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "publications",uniqueConstraints = { @UniqueConstraint(columnNames = { "title" }) })
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title",nullable = false)
    private String title;
    @Column(name = "description",nullable = false)
    private String description;
    @Column(name = "content",nullable = false)
    private String content;
    @JsonBackReference
    @OneToMany(targetEntity = Comments.class,fetch = FetchType.EAGER, mappedBy = "publication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comments> comments;
}
