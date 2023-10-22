package com.api.sistemablogspringboot.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "comments")
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String body;
    /* *EL ID DEL CLUB PASO COMO CLAVE FORÁNEA A LA ENTIDAD COMMENTS
    * PORQUE UTILIZAMOS "mappedBy = "publication"
    * UTILIZAMOS "targetEntity = Publication.class" QUE NO ES NECESARIO
    * SOLO PARA LA LEGIBILIDAD Y COMPRENSIÓN DEL CÓDIGO
    * */
    @ManyToOne(targetEntity = Publication.class)
    @JoinColumn(name = "publication_id",nullable = false)
    private Publication publication;
}
