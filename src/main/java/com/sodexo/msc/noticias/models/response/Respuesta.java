package com.sodexo.msc.noticias.models.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Respuesta implements Serializable {

    private static final long serialVersionID=1L;
    private Object data;
    private String mensaje;
    private boolean valido=true;

    public Respuesta(){

    }
    public Respuesta(boolean valido, String mensaje){
        this.valido = valido;
        this.mensaje = mensaje;
    }

    public Respuesta(boolean valido, String mensaje, Object data){
        this.valido = valido;
        this.mensaje = mensaje;
        this.data = data;
    }
}
