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
 * @author Ricky
 */
public abstract class GameElement {
    protected int x;
    protected int y;
    protected ImageIcon imageIcon;
    
    public GameElement() {
        x = 0;
        y = 0;
        imageIcon = null;
    }
    
    public GameElement (int posX, int posY, ImageIcon image){
        this.x = posX;
        this.y = posY;
        imageIcon = image;
    }
    
    public Image getImage() {
        return imageIcon.getImage();
    }
    
    public void setImage(ImageIcon image) {
        this.imageIcon = image;
    }
    
    public void setX(int x){
        this.x = x;
    }
    
    public void setY(int y){
        this.y = y;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public int getWidth() {
        return imageIcon.getIconWidth();
    }
	
    public int getHeight() {
	return imageIcon.getIconHeight();
    }
    
    private Rectangle getRectangle(){
	return new Rectangle(x, y, imageIcon.getIconWidth(), imageIcon.getIconHeight());
    }
    
    public boolean collidesWith(GameElement obj){
	return getRectangle().intersects(obj.getRectangle());
    }
}
