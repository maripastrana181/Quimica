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
    
    public GameElement() {
        x = 0;
        y = 0;
    }
    
    public GameElement (int posX, int posY){
        this.x = posX;
        this.y = posY;
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
    
    public abstract Image getImage();
    
    public abstract int getWidth();
	
    public abstract int getHeight();
    
    protected abstract Rectangle getRectangle();
    
    public abstract boolean collidesWith(GameElement obj);
}
