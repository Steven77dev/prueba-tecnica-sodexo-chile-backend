package com.sodexo.msc.noticias.controller;

import com.sodexo.msc.noticias.entity.NoticiaFavorita;
import com.sodexo.msc.noticias.models.response.NoticiasExternas;
import com.sodexo.msc.noticias.models.response.Respuesta;
import com.sodexo.msc.noticias.service.NoticiasService;
import com.sodexo.msc.noticias.utils.Conversor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/noticias")
@CrossOrigin("*")
public class NoticiasController {
    @Value("${external.api.url}")
    private String externalApiUrl;

    private RestTemplate restTemplate;

    private NoticiasService noticiasService;

    public NoticiasController(RestTemplate restTemplate, NoticiasService noticiasService){
        this.restTemplate = restTemplate;
        this.noticiasService = noticiasService;
    }


    @GetMapping("/externas")
    public String listarNoticiasExternas(  @RequestParam(required = false) String search,
                                                @RequestParam(required = false, defaultValue = "0") int offset,
                                                @RequestParam(required = false, defaultValue = "published_at") String ordering) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(externalApiUrl);

        if (search != null && !search.isEmpty()) {
            builder.queryParam("search", search);
        }
        if (offset > 0) {
            builder.queryParam("offset", offset);
        }
        builder.queryParam("ordering", ordering);
        String apiUrl = builder.toUriString();
        String response = restTemplate.getForObject(apiUrl, String.class);

        return response;
    }

    @Operation(description = "Guardar noticia", summary = "Servicio que guarda la noticia como favorita del usuario",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Respuesta.class))})
            })
    @PostMapping("/guardarFavorita")
    public Respuesta guardarFavorita(@RequestBody NoticiasExternas noticia){
        Respuesta respuesta = new Respuesta();
        NoticiaFavorita guardado = noticiasService.guardar(Conversor.noticiaFavorita(noticia));
        if(guardado!=null){
            respuesta.setValido(true);
            respuesta.setMensaje("Noticia guardada con éxito.");
        } else{
            respuesta.setValido(false);
        }
        return respuesta;
    }

    @Operation(description = "Listado de noticias favoritas", summary = "Servicio para listar las noticias favoritas del usuario",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Respuesta.class))})
            })
    @GetMapping("/favoritas")
    public Respuesta listarNoticiasFavoritas(@RequestParam(required = false) String titulo,
                                                         @RequestParam(required = false, defaultValue = "0") int offset,
                                                         @RequestParam(required = false, defaultValue = "asc") String ordering) {

        Respuesta respuesta = new Respuesta();
        List<NoticiaFavorita> listado  = noticiasService.buscarPorTituloOrdenar(offset, titulo,ordering);
        if(listado!=null){
            respuesta.setData(listado);
        } else{
            respuesta.setMensaje("No se encontraron resultados");
            respuesta.setValido(false);
            respuesta.setData(null);
        }
        return respuesta;

    }

    @Operation(description = "Conteo de noticias favoritas", summary = "Se obtiene el total de noticias favoritas del usuario",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Integer.class))})
            })
    @GetMapping("/contarFavoritas")
    public int contarFavoritas(@RequestParam(required = false) String titulo) {
        return noticiasService.conteo(titulo).intValue();
    }

    @Operation(description = "Eliminar noticia favorita", summary = "Se eliminar una noticia favorita del usuario",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Respuesta.class))})
            })
    @DeleteMapping("/{codigo}")
    public Respuesta eliminarFavorito(@PathVariable Long codigo) {
        Respuesta respuesta = new Respuesta();
        boolean eliminado = noticiasService.eliminarFavorita(codigo);
        respuesta.setValido(eliminado);
        return respuesta;

    }
}
