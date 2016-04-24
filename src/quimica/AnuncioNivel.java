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
public class AnuncioNivel extends JFrame implements KeyListener {
    
     private Image dbImage;
     private Graphics dbg;
     private int nivel;
     private ImageIcon imagen;
    
    public AnuncioNivel(int level){
        initUI();
        nivel = level;
        if (level == 2) {
            imagen = new ImageIcon("img/realmadrid.jpg");
        } else {
            imagen = new ImageIcon("img/jamesrodriguez.jpg");   
        }
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
        /*  
        BufferedImage resizedImage = new BufferedImage(getWidth(), getHeight(), type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g.dispose();*/	
        if (imagen != null) {
            dbg.drawImage(imagen.getImage(), 0, 0, this);
        }
        g.drawImage(dbImage, 0, 0, this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_C:
                new Quimica(nivel);
                dispose();
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
}
