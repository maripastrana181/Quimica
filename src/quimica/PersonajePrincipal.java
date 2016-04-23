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
public class PersonajePrincipal extends GameElement {
    
    private final static int SPEED = 20;
    
    public PersonajePrincipal(int posX, int posY, ImageIcon image){
        super(posX, posY, image);
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
}