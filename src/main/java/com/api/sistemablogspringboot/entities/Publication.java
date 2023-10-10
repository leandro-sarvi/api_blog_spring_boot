package com.api.sistemablogspringboot.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "publications")
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
    @OneToMany(mappedBy = "publication",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comments> comments = new HashSet<>();
}
