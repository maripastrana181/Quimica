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
    
    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;
    
    private final static int LIVES = 3;
    private final static int SLEEP_TIME = 100;
    private final static int ANSWER_EXPECTATION = 10;
    private final static int NUMBER_OF_ANSWERS = 5;
    private final static int SCORE_INCREASE = 10;
    private boolean[] pressedKeys;  
    
    private int score;
    private int lives;
    
    private boolean answerVisible;
    
    private PersonajePrincipal personaje;
    private ArrayList<Respuesta> respuestas;
    
    private ImageIcon[] answerImages;
    private ImageIcon mainCharacter;
    
    private Image dbImage;
    private Graphics dbg;
    
    private static int randomInteger(int min, int max) {
        return min + (int)(Math.random() * (max - min));   
    }
    
    public Quimica() {
        initUI();
        
        score = 0;
        lives = 3;
        answerVisible = true;
        
        pressedKeys = new boolean[] {false, false, false, false};
        
        respuestas = new ArrayList<>();

        addKeyListener(this);
        
        Thread thread = new Thread(this);
        thread.start();
    }
    
    public void initUI() {
        setTitle("Quimica");
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setUndecorated(true);
        setVisible(true);
        
        mainCharacter = new ImageIcon("img/main.gif");
        personaje = new PersonajePrincipal((getWidth() - mainCharacter.getIconWidth()) / 2, (getHeight() - mainCharacter.getIconHeight()) / 2, mainCharacter);
        
        answerImages = new ImageIcon[NUMBER_OF_ANSWERS];
        answerImages[0] = new ImageIcon("img/respuesta.png");
        answerImages[1] = new ImageIcon("img/respuesta.png");
        answerImages[2] = new ImageIcon("img/respuesta.png");
        answerImages[3] = new ImageIcon("img/respuesta.png");
        answerImages[4] = new ImageIcon("img/respuesta.png");     
    }
    
    public void run() {
        while (true) {
            
            // Move main character
            if (pressedKeys[UP] && personaje.getY() > 0) {
                personaje.moverArriba();
            }
            
            if (pressedKeys[DOWN] && personaje.getY() < getHeight() - personaje.getHeight()) {
                personaje.moverAbajo();
            }
            
            if (pressedKeys[LEFT] && personaje.getX() > 0) {
                personaje.moverIzquierda();
            }
            
            if (pressedKeys[RIGHT] && personaje.getX() < getWidth() - personaje.getWidth()) {
                personaje.moverDerecha();
            }
            
            // Probably create a new enemy
            if (randomInteger(0, ANSWER_EXPECTATION) == 0) {
                // ------------Select random image-------------------------
                ImageIcon answerImage = answerImages[randomInteger(0, NUMBER_OF_ANSWERS)];
                // --------------------------------------------------------
                
                if (respuestas.size() > 0) {
                    Respuesta ultimaRespuesta = respuestas.get(respuestas.size() - 1);
                    Respuesta nuevaRespuesta;
                    do {
                        nuevaRespuesta = new Respuesta(randomInteger(0, 1 + getWidth() - answerImage.getIconWidth()), -answerImage.getIconHeight(), answerImage);
                    } while (ultimaRespuesta.collidesWith(nuevaRespuesta));
                    respuestas.add(nuevaRespuesta);
                } else {
                    respuestas.add(new Respuesta(randomInteger(0, 1 + getWidth() - answerImage.getIconWidth()), -answerImage.getIconHeight(), answerImage));
                }
            }
            
            // revisar la colision de respuestas
            int i = 0;
            while (i < respuestas.size()) {
                Respuesta respuesta = respuestas.get(i);
                if (personaje.collidesWith(respuesta) || respuesta.getY() > getHeight()) {
                    if (respuesta.isCorrect()) {
                        --lives; 
                    } else {
                        score += SCORE_INCREASE;
                    }
                    respuestas.remove(i);
                } else {
                    respuesta.caer();
                    i++;
                }
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
        
        // dibujar el personaje principal
        dbg.drawImage(personaje.getImage(), personaje.getX(), personaje.getY(), this);
        
        // dibujar las respuestas
        for (Respuesta respuesta : respuestas) {
            dbg.drawImage(respuesta.getImage(), respuesta.getX(), respuesta.getY(), this);
        }
        
        g.drawImage(dbImage, 0, 0, this);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Quimica();
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
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
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