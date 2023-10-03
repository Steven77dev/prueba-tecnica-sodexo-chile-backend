package com.sodexo.msc.noticias.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@Entity
public class NoticiaFavorita {
    @Id
    private Long codigo;

    private String titulo;
    private String url;
    private String urlImagen;
    private String newsSite;
    @Column(length = 1200)
    private String resumen;
    private String actualizado;
    private boolean destacado;
    private String publicado;
    private Date guardado;
}
