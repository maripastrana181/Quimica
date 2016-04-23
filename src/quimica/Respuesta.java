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

    private final static int SPEED = 10;
    
    public Respuesta(int posX, int posY, ImageIcon image){
        super(posX, posY, image);
    }
    public void caer() {
        y += SPEED;
    }
    public boolean isCorrect(Pregunta pregunta) {
        return true;
    }

}