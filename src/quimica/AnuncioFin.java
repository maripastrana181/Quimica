/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quimica;

import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

/**
 *
 * @author Ricky
 */
public class AnuncioFin extends Anuncio {
    public AnuncioFin() {
        super(new ImageIcon("img/gameover.png"));
    }
    
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
