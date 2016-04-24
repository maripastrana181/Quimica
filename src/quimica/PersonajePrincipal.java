/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package quimica;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
/**
 *
 * @author Maribel
 */
public class PersonajePrincipal extends GameElement {
    
    private final static int SPEED = 20;
    private ImageIcon imageIcon;
    
    public PersonajePrincipal(int posX, int posY, ImageIcon image){
        super(posX, posY);
        imageIcon = image;
    }
    
    public void moverDerecha(){
        x += SPEED;
    }
    
    public void moverIzquierda(){
        x -= SPEED;
    }
    
    public void moverArriba(){
        y -= SPEED;
    }
    
    public void moverAbajo(){
        y += SPEED;
    }
    
    @Override
    public Image getImage() {
        return imageIcon.getImage();
    }
    
    @Override
    public int getWidth() {
        return imageIcon.getIconWidth();
    }
	
    @Override
    public int getHeight() {
	return imageIcon.getIconHeight();
    }
    
    @Override
    protected Rectangle getRectangle(){
	return new Rectangle(x, y, imageIcon.getIconWidth(), imageIcon.getIconHeight());
    }
    
    @Override
    public boolean collidesWith(GameElement obj){
	return getRectangle().intersects(obj.getRectangle());
    }
}