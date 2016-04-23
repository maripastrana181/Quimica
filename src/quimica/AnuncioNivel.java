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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author Ricky
 */
public class AnuncioNivel extends JFrame implements Runnable, KeyListener {
     private Image dbImage;
     private Graphics dbg;
     private int nivel;
     private ImageIcon imagen;
    
    public AnuncioNivel(int level){
        initUI();
        nivel = level;
        if( level == 2){
            imagen = new ImageIcon("img/realmadrid.jpg");
        }else if(level == 3){
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
            
            dbg.drawImage(imagen.getImage(), 0, 0, this);
            g.drawImage(dbImage, 0, 0, this);
            
    }

    @Override
    public void run() {
        while(true){
            repaint();
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Quimica.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_C:
                System.out.println("salu2");
                new Quimica(nivel);
                dispose();
                break;
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
}
