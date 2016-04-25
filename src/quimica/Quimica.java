/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quimica;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
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

    private final static int INITIAL_NUMBER_OF_LIVES = 3;
    private final static int SLEEP_TIME = 60;
    private final static int ANSWER_EXPECTATION = 10;
    private final static int NUMBER_OF_QUESTIONS = 1;

    private final static int SCORE_INCREASE = 10;
    private final static int SCORE_NIVEL2 = 100;
    private final static int SCORE_NIVEL3 = 200;
    private boolean sameLevel;
    private final boolean[] pressedKeys;

    private int score;
    private int lives;
    private boolean pausa;
    private boolean swapQuestion;
    private final Thread thread;
    
    //private SoundClip musicaFondo1;
    //private SoundClip musicaFondo2;
    //private SoundClip musicaFondo3;
    
    private final SoundClip musicaColisionCorrecta;
    private final SoundClip musicaColisionIncorrecta;
    private final SoundClip musicaFondo;

    private final PersonajePrincipal personaje;
    
    // arraylist predefinido de posibles moleculas resultantes
    private final ArrayList<Molecula> moleculasResultantes;
    
    // arraylist predefinido de posibles preguntas
    private final ArrayList<Pregunta> preguntas;
    
    // arraylist de resultantes que van cayendo en pantalla
    private final ArrayList<Resultante> resultantesEnPantalla;
    
    private int preguntaActual;

    private final Rectangle panel;

    private Image dbImage;
    private Graphics dbg;

    private static int randomInteger(int min, int max) {
        return min + (int) (Math.random() * (max - min));
    }

    public Quimica(int nivel) {
        initUI();
        switch (nivel) {
            case 1:
                Resultante.SPEED = 10;
                score = 0;
                break;
            case 2:
                Resultante.SPEED = 20;
                score = SCORE_NIVEL2;
                break;
            default:
                Resultante.SPEED = 30;
                score = SCORE_NIVEL3;
                break;
        }
        
        sameLevel = true;
        lives = INITIAL_NUMBER_OF_LIVES;
        pausa = false;
        swapQuestion = false;

        pressedKeys = new boolean[] {false, false, false, false};
        
        // opciones predefinidas de moleculas resultantes
        moleculasResultantes = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_QUESTIONS; ++i) {
            moleculasResultantes.add(new Molecula(i, new ImageIcon("img/rres_" + i + ".png")));
        }
        
        preguntas = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_QUESTIONS; ++i) {
            Molecula r1 = new Molecula(i, new ImageIcon("img/rr1_" + i + ".png"));
            Molecula r2 = new Molecula(i, new ImageIcon("img/rr2_" + i + ".png"));
            preguntas.add(new Pregunta(r1, r2, moleculasResultantes.get(i)));
        }
        
        preguntaActual = randomInteger(0, preguntas.size());

        resultantesEnPantalla = new ArrayList<>();
        
        ImageIcon mainCharacter = new ImageIcon("img/main.gif");
        personaje = new PersonajePrincipal((getWidth() - mainCharacter.getIconWidth()) / 2, (getHeight() - mainCharacter.getIconHeight()) / 2, mainCharacter);
        panel = new Rectangle(0, (int)(3 * getHeight() / 4.0), getWidth(), getHeight() / 4);
        
        musicaColisionCorrecta = new SoundClip("sounds/respuestaCorrecta.wav");
        musicaColisionIncorrecta = new SoundClip("sounds/clic.wav");
        musicaFondo = new SoundClip("sounds/musica.wav");
        musicaFondo.play();
        /*
        musicaFondo1 = new SoundClip("sounds/musica1.mid");
        musicaFondo2 = new SoundClip("sounds/musica2.mid");
        musicaFondo3 = new SoundClip("sounds/musica3.mid");
        */
        addKeyListener(this);
        thread = new Thread(this);
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
    }
    
    public boolean rechazarNuevoResultante(Resultante res) {
        for (Resultante r : resultantesEnPantalla) {
            if (r.collidesWith(res)) {
                return true;
            }
        }
        return false;
    }
    
    public void run() {
        while (sameLevel) {
            if (!pausa) {
                // Move main character
                if (pressedKeys[UP] && personaje.getY() > 0) {
                    personaje.moverArriba();
                }

                if (pressedKeys[DOWN] && personaje.getY() < getHeight() - panel.getHeight() - personaje.getHeight()) {
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
                    // ------------Select random molecule------------------------------------------------------------
                    Molecula nuevaMolecula = moleculasResultantes.get(randomInteger(0, moleculasResultantes.size()));
                    // ----------------------------------------------------------------------------------------------

                    if (resultantesEnPantalla.size() > 0) {
                        Resultante ultimoResultante = resultantesEnPantalla.get(resultantesEnPantalla.size() - 1);
                        Resultante nuevoResultante;
                        do {
                            nuevoResultante = new Resultante(randomInteger(0, 1 + getWidth() - nuevaMolecula.getWidth()), -nuevaMolecula.getHeight(), nuevaMolecula);
                        } while (rechazarNuevoResultante(nuevoResultante));
                        resultantesEnPantalla.add(nuevoResultante);
                    } else {
                        resultantesEnPantalla.add(new Resultante(randomInteger(0, 1 + getWidth() - nuevaMolecula.getWidth()), -nuevaMolecula.getHeight(), nuevaMolecula));
                    }
                }
                // revisar la colision de resultantesEnPantalla
                int i = 0;
                
                while (i < resultantesEnPantalla.size()) {
                    Resultante resultante = resultantesEnPantalla.get(i);
                    if (personaje.collidesWith(resultante)) {
                        if (resultante.isCorrect(preguntas.get(preguntaActual))) {
                            score += SCORE_INCREASE;
                            musicaColisionCorrecta.play();
                            if (score == SCORE_NIVEL2) {
                                pasarNivel(2);
                            } else if (score == SCORE_NIVEL3) {
                                pasarNivel(3);
                            }
                        } else {
                            musicaColisionIncorrecta.play();
                            --lives;
                        }
                        preguntaActual = randomInteger(0, preguntas.size());
                        resultantesEnPantalla.remove(i);
                        swapQuestion = randomInteger(0, 2) == 0;
                    } else if (resultante.getY() > getHeight() - panel.getHeight()) {
                        resultantesEnPantalla.remove(i);
                    } else {
                        resultante.caer();
                        i++;
                    }
                }
                repaint();

                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Quimica.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.print("");
            }
        }
    }

    public void pasarNivel(int level) {
        sameLevel = false;
        musicaFondo.stop();
        new AnuncioNivel(level);
        dispose();
    }

    @Override
    public void paint(Graphics g) {
        // Inicializan el DoubleBuffer
        if (dbImage == null) {
            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics();
        }
        dbg.setColor(Color.WHITE);
        dbg.fillRect(0, 0, getWidth(), getHeight());
        dbg.setColor(getForeground());

        // dibujar el personaje principal
        
        if (personaje != null) {
            dbg.drawImage(personaje.getImage(), personaje.getX(), personaje.getY(), this);
        }

        // dibujar las moleculasResultantes
        
        if (resultantesEnPantalla != null) {
            for (Resultante r : resultantesEnPantalla) {
                dbg.drawImage(r.getImage(), r.getX(), r.getY(), this);
            }
        }
        
        if (panel != null) {
            dbg.setColor(Color.WHITE);
            dbg.fillRect((int) panel.getX(), (int) panel.getY(), (int) panel.getWidth(), (int) panel.getHeight());
            dbg.setFont(new Font("Baskerville Old Face", Font.BOLD, 20));
            dbg.setColor(Color.BLACK);
            dbg.drawString("Score: " + score, 7 * (int) panel.getWidth() / 8, getHeight() - 3 * (int) panel.getHeight() / 4);
            dbg.drawString("Vidas:" + lives, 7 * (int) panel.getWidth() / 8, getHeight() - (int) panel.getHeight() / 4);
            dbg.setColor(Color.BLACK);
            dbg.drawLine(0, getHeight() - (int)panel.getHeight(), getWidth(), getHeight() - (int)panel.getHeight());
        }
        
        if (preguntas != null && preguntas.size() > 0) {
            Molecula r1 = null;
            Molecula r2 = null;

            if (swapQuestion) {
                r1 = preguntas.get(preguntaActual).getReactivo1();
                r2 = preguntas.get(preguntaActual).getReactivo2();
            } else {
                r2 = preguntas.get(preguntaActual).getReactivo1();
                r1 = preguntas.get(preguntaActual).getReactivo2();
            }
            
            dbg.setFont(new Font("Baskerville Old Face", Font.BOLD, 80));
            
            int halfWidth = getWidth() / 2;
            int yHalfPanel = getHeight() - (int)panel.getHeight() / 2;
            int r1Width = r1.getWidth();
            int r1HalfHeight = r1.getHeight() / 2;
            int r2Width = r2.getWidth();
            int r2HalfHeight = r2.getHeight() / 2;
            int plusWidth = dbg.getFontMetrics().stringWidth("+");
            
            dbg.drawImage(r1.getImage(), halfWidth - r1Width, yHalfPanel - r1HalfHeight, this);
            dbg.drawImage(r2.getImage(), halfWidth + plusWidth, yHalfPanel - r1HalfHeight , this);
            
            dbg.drawString("+", halfWidth, yHalfPanel);
        }
        
        g.drawImage(dbImage, 0, 0, this);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Quimica(1);
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
            case KeyEvent.VK_P:
                pausa = !pausa;
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