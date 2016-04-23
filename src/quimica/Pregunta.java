/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package quimica;

/**
 *
 * @author Maribel
 */
public class Pregunta {
    
    private String sPregunta; 
    private Respuesta rRespuestaCorrecta;
    
    public Pregunta(){
        sPregunta = "";
    }
    
    public Pregunta(String preg){
        sPregunta = preg; 
    }
    public Respuesta getRespuesta(){
        return rRespuestaCorrecta;
    }
    public void setRespuesta(Respuesta resp){
        rRespuestaCorrecta = resp; 
    }
    public String toString(){
        return sPregunta; 
    }
    
}
