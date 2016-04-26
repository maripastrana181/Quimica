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
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author Ricky
 */
public abstract class Anuncio extends JFrame implements KeyListener {
     private Image dbImage;
     private Graphics dbg;
     private int nivel;
     protected ImageIcon imagen;
    
    public Anuncio(ImageIcon image){
        imagen = image;
        initUI();
        addKeyListener(this);
    }
    
     public void initUI() {
        setTitle("Quimica");
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setUndecorated(true);
        setVisible(true);
    }
    
    public void paint(Graphics g) {
        // Inicializan el DoubleBuffer
        if (dbImage == null){
            dbImage = createImage (this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics();
        }
        
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, getWidth(), getHeight());
        dbg.setColor(getForeground());
        if (imagen != null) {
            dbg.drawImage(imagen.getImage(), 0, 0, this);
        }
        g.drawImage(dbImage, 0, 0, this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public abstract void keyPressed(KeyEvent e);

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
}
