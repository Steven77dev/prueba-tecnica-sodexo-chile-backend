package com.sodexo.msc.noticias.service;


import com.sodexo.msc.noticias.entity.NoticiaFavorita;
import com.sodexo.msc.noticias.repository.NoticiasRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoticiasService  {

    private NoticiasRepository noticiasRepository;

    public NoticiasService(NoticiasRepository repository){
        this.noticiasRepository = repository;
    }

    public List<NoticiaFavorita> buscarPorTituloOrdenar(int offset, String titulo,String order) {

        Sort sort = Sort.by("guardado");
        if (order.equalsIgnoreCase("desc")) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }
        Pageable pageable = PageRequest.of(offset / 10, 10, sort);
        Page<NoticiaFavorita> listado = noticiasRepository.findByTituloContainingIgnoreCase(titulo,  pageable);
        return listado.getContent();
    }

    public NoticiaFavorita guardar(NoticiaFavorita noticiaFavorita){
        return noticiasRepository.save(noticiaFavorita);
    }

    public Long conteo(String titulo){
        return Long.valueOf(noticiasRepository.countByTituloContainingIgnoreCase(titulo));
    }

    public boolean eliminarFavorita(Long codigo){
        Optional<NoticiaFavorita> optionalEntidad = noticiasRepository.findById(codigo);
        if (optionalEntidad.isPresent()) {
            noticiasRepository.deleteById(codigo);
            return true;
        } else {
           return false;
        }
    }
}
