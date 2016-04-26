/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quimica;

import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author Ricky
 */
public class Menu extends Anuncio {
    
    ImageIcon imagenMenu;
    ImageIcon imagenInstrucciones;
    
    public Menu() {
        super(new ImageIcon("img/menuuMari.png"));
        imagenMenu = imagen;
        imagenInstrucciones = new ImageIcon("img/instruccionesMari.png");
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_C:
                new Quimica(1);
                dispose();
                break;
            case KeyEvent.VK_I:
                imagen = imagenInstrucciones;
                repaint();
                break;
            case KeyEvent.VK_R:
                imagen = imagenMenu;
                repaint();
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
        }
        
    }
    
}
