/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package quimica;
import java.awt.Image;
import javax.swing.ImageIcon;
/**
 *
 * @author Maribel
 */
public class PersonajePrincipal {
    private final static int SPEED = 1;
    private int posX;
    private int posY;
    private ImageIcon imagen; 
    
    public PersonajePrincipal(int posX, int posY, Image image){
        this.posX = posX;
        this.posY = posY;
        imagen = new ImageIcon(image);
    }
    
    public void setPosX(int posX){
        this.posX = posX;
    }
    
    public void setPosY(int posY){
        this.posY = posY;
    }
    public int getPosX(){
        return posX;
    }
    public int getPosY(){
        return posY;
    }
    public void moverDerecha(){
        posX += SPEED;
    }
    public void moverIzquierda(){
        posX -= SPEED;
    }
    public void moverArriba(){
        posY += SPEED;
    }
    public void moverAbajo(){
        posY -= SPEED;
    }
}
