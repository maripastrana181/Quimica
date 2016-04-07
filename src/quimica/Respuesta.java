/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package quimica;

import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Rectangle;

/**
 *
 * @author Maribel
 */
public class Respuesta {
    private final static int SPEED = 1;
    private int posX;
    private int posY;
    private ImageIcon imagen; 
    
    public Respuesta(int posX, int posY, Image image){
        this.posX = posX;
        this.posY = posY;
        imagen = new ImageIcon(image);
    }
    public int getPosX(){
        return posX;
    }
    public int getPosY(){
        return posY;
    }
    public void setPosX(int posX){
        this.posX = posX;
    }
    public void setPosY(int posY){
        this.posY = posY;
    }
    public void caer(){
        if(!checaPosicion()){
             posY -= SPEED;
        }else{
            posY = - imagen.getIconHeight();
        }
    }
    public boolean checaPosicion(){
        return (posY > Quimica.WINDOW_WIDTH);
    }
    public int getAncho() {
	return imagen.getIconWidth();
    }
    public int getAlto() {
	return imagen.getIconHeight();
    }
     public Rectangle getPerimetro(){
	return new Rectangle(getPosX(),getPosY(),getAncho(),getAlto());
     }
    public boolean intersecta(PersonajePrincipal obj){
		return getPerimetro().intersects(obj.getPerimetro());
    }
}
