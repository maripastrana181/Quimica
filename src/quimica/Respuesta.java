/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package quimica;

import javax.swing.ImageIcon;

/**
 *
 * @author Maribel
 */
public class Respuesta extends GameElement {

    public static int SPEED = 10;
    private String texto; 
    
    public Respuesta(String t){
        super(0, 0, null);
        texto = t;
        
    }
    public Respuesta(int posX, int posY, ImageIcon image, String text){
        super(posX, posY, image);
        texto = text; 
    }
    public void caer() {
        y += SPEED;
    }
    public boolean isCorrect(Pregunta pregunta) {
       return pregunta.getRespuesta().equals(this);
       
    }
    
    public boolean equals(Respuesta r){
        return texto.equals(r.texto);
    }

}