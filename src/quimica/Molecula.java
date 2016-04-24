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
public class Molecula {
    
    private int index;
    private ImageIcon imageIcon;
    
    public Molecula(int i, ImageIcon image) {
        index = i;
        imageIcon = image;
    }
    
    public boolean equals(Molecula m){
        return index == m.index;
    }
    
    public Image getImage() {
        return imageIcon.getImage();
    }
    
    public int getWidth() {
        return imageIcon.getIconWidth();
    }
	
    public int getHeight() {
	return imageIcon.getIconHeight();
    }
    
    public int getIndex() {
        return index;
    }
}