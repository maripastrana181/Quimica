/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quimica;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author Ricky
 */
public class AnuncioNivel extends Anuncio {
    
    private int level;
    
    public AnuncioNivel(ImageIcon image, int nivel) {
        super(image); 
        level = nivel;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_C:
                new Menu();
                dispose();
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
        }
        
    }

}
