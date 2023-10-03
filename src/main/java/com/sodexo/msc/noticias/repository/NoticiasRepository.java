package com.sodexo.msc.noticias.repository;

import com.sodexo.msc.noticias.entity.NoticiaFavorita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticiasRepository extends JpaRepository<NoticiaFavorita, Long> {

    Page<NoticiaFavorita> findByTituloContainingIgnoreCase(@Param("titulo") String titulo, Pageable pageable);
    int countByTituloContainingIgnoreCase(@Param("titulo") String titulo);
}
