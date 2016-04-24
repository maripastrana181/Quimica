/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package quimica;

import java.awt.Image;
import java.awt.Rectangle;

/**
 *
 * @author Maribel
 */
public class Resultante extends GameElement {

    public static int SPEED = 10;
    private Molecula molecula;
    
    public Resultante(int posX, int posY, Molecula m) {
        super(posX, posY);
        molecula = m;
    }
    
    public void caer() {
        y += SPEED;
    }
    
    public boolean isAnswer(Pregunta pregunta) {
       return molecula.equals(pregunta.getMoleculaResultante());
    }

    @Override
    public Image getImage() {
        return molecula.getImage();
    }


    @Override
    public int getWidth() {
        return molecula.getWidth();
    }

    @Override
    public int getHeight() {
        return molecula.getHeight();
    }

    @Override
    protected Rectangle getRectangle() {
        return new Rectangle(x, y, molecula.getWidth(), molecula.getHeight());
    }

    @Override
    public boolean collidesWith(GameElement obj) {
        return getRectangle().intersects(obj.getRectangle());
    }
    
    public boolean isCorrect(Pregunta pregunta) {
        return pregunta.getIndex() == molecula.getIndex();
    }
}