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
import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author Maribel
 */
public class Quimica extends JFrame implements Runnable, KeyListener {
    
    public final static int WINDOW_WIDTH = 1000;
    public final static int WINDOW_HEIGHT = 600;
    
    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;
    
    private final static int LIVES = 3;
    private boolean[] pressedKeys;
    
    private int score;
    private int lives;
    
    private PersonajePrincipal personaje;
    private ArrayList<Respuesta> respuestas;
    
    private Image dbImage;
    private Graphics dbg;
    
    public Quimica() {
        initUI();
        
        score = 0;
        lives = 3;
        ImageIcon mainImage = new ImageIcon("img/main.gif");
        personaje = new PersonajePrincipal((getWidth() - mainImage.getIconWidth()) / 2, (getHeight() - mainImage.getIconHeight()) / 2, mainImage);
        
        pressedKeys = new boolean[] {false, false, false, false};
        
        addKeyListener(this);
        
        Thread thread = new Thread(this);
        thread.start();
    }
    
    public void initUI() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("Quimica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        
    }
    
    public void run() {
        while (true) {
            if (pressedKeys[UP] && personaje.getPosY() > 0) {
                personaje.moverArriba();
            }
            
            if (pressedKeys[DOWN] && personaje.getPosY() < getHeight() - personaje.getAlto()) {
                personaje.moverAbajo();
            }
            if (pressedKeys[LEFT] && personaje.getPosX() > 0) {
                personaje.moverIzquierda();
            }
            if (pressedKeys[RIGHT] && personaje.getPosX() < getWidth() - personaje.getAncho()) {
                personaje.moverDerecha();
            }
            repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Quimica.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public void paint(Graphics g) {
        // Inicializan el DoubleBuffer
        if (dbImage == null){
            dbImage = createImage (this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics();
        }
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, getWidth(), getHeight());
        dbg.setColor(getForeground());
        
        dbg.drawImage(personaje.getImage(), personaje.getPosX(), personaje.getPosY(), this);
        
        g.drawImage(dbImage, 0, 0, this);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Quimica game = new Quimica();
        game.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        keyPressed(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                pressedKeys[UP] = true;
                break;
            case KeyEvent.VK_LEFT:
                pressedKeys[LEFT] = true;
                break;
            case KeyEvent.VK_RIGHT:
                pressedKeys[RIGHT] = true;
                break;
            case KeyEvent.VK_DOWN:
                pressedKeys[DOWN] = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                pressedKeys[UP] = false;
                break;
            case KeyEvent.VK_LEFT:
                pressedKeys[LEFT] = false;
                break;
            case KeyEvent.VK_RIGHT:
                pressedKeys[RIGHT] = false;
                break;
            case KeyEvent.VK_DOWN:
                pressedKeys[DOWN] = false;
                break;
        }
    }   
}