package com.sodexo.msc.noticias.utils;

import com.sodexo.msc.noticias.entity.NoticiaFavorita;
import com.sodexo.msc.noticias.models.response.NoticiasExternas;

import java.util.Date;

public class Conversor {


    public static NoticiaFavorita noticiaFavorita(NoticiasExternas noticia){
        NoticiaFavorita noticiaFavorita = new NoticiaFavorita();
        noticiaFavorita.setCodigo(Long.valueOf(noticia.getId()));
        noticiaFavorita.setResumen(noticia.getSummary());
        noticiaFavorita.setTitulo(noticia.getTitle());
        noticiaFavorita.setDestacado(noticia.isFeatured());
        noticiaFavorita.setUrl(noticia.getUrl());
        noticiaFavorita.setUrlImagen(noticia.getImage_url());
        noticiaFavorita.setNewsSite(noticia.getNews_site());
        noticiaFavorita.setPublicado(noticia.getPublished_at());
        noticiaFavorita.setActualizado(noticia.getUpdated_at());
        noticiaFavorita.setGuardado(new Date());
        return noticiaFavorita;
    }
}
