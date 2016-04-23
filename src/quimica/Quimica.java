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
    private final static int SLEEP_TIME = 100;
    private final static int ANSWER_EXPECTATION = 10;

    private final static int NUMBER_OF_ANSWERS = 5;
    private final static int SCORE_INCREASE = 10;
    private final static int SCORE_NIVEL2 = 10;
    private final static int SCORE_NIVEL3 = 20;
    private boolean sameLevel;
    private boolean[] pressedKeys;

    private int score;
    private int lives;
    private boolean pausa;
    private Thread thread;
    private int nivel;
    private SoundClip musicaFondo1;
    private SoundClip musicaFondo2;
    private SoundClip musicaFondo3;
    
    private SoundClip musicaColisionCorrecta;
    private SoundClip musicaColisionIncorrecta;
    private SoundClip musicaFondo;
    private boolean answerVisible;

    private PersonajePrincipal personaje;
    private ArrayList<Respuesta> respuestas;
    private ArrayList<Pregunta> preguntas;
    private int preguntaActual;

    private ImageIcon[] answerImages;
    private ImageIcon mainCharacter;
    
    private Rectangle panel;

    private Image dbImage;
    private Graphics dbg;

    private static int randomInteger(int min, int max) {
        return min + (int) (Math.random() * (max - min));
    }

    public Quimica(int level) {
        initUI();
        score = 0;
        nivel = level;

        if (nivel == 1) {
            Respuesta.SPEED = 10;
            score = 0;
        } else if (nivel == 2) {
            Respuesta.SPEED = 20;
            score = SCORE_NIVEL2;
        } else {
            Respuesta.SPEED = 30;
            score = SCORE_NIVEL3;
        }
        
        sameLevel = true;
        lives = INITIAL_NUMBER_OF_LIVES;
        pausa = false;
        answerVisible = true;

        pressedKeys = new boolean[]{false, false, false, false};

        respuestas = new ArrayList<>();
        preguntas = new ArrayList<>();

        preguntas.add(new Pregunta("2+2"));
        preguntas.get(0).setRespuesta(new Respuesta("4"));
        preguntas.add(new Pregunta("3+2"));
        preguntas.get(1).setRespuesta(new Respuesta("5"));
        preguntaActual = 0;
       
        musicaColisionCorrecta = new SoundClip("sounds/respuestaCorrecta.wav");
        musicaColisionIncorrecta = new SoundClip("sounds/clic.wav");
        musicaFondo = new SoundClip("sounds/musica.wav");
         /*
        musicaFondo1 = new SoundClip("sounds/musica1.mid");
        musicaFondo2 = new SoundClip("sounds/musica2.mid");
        musicaFondo3 = new SoundClip("sounds/musica3.mid");*/

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

        mainCharacter = new ImageIcon("img/main.gif");
        personaje = new PersonajePrincipal((getWidth() - mainCharacter.getIconWidth()) / 2, (getHeight() - mainCharacter.getIconHeight()) / 2, mainCharacter);

        answerImages = new ImageIcon[NUMBER_OF_ANSWERS];
        answerImages[0] = new ImageIcon("img/respuesta.png");
        answerImages[1] = new ImageIcon("img/respuesta.png");
        answerImages[2] = new ImageIcon("img/respuesta.png");
        answerImages[3] = new ImageIcon("img/respuesta.png");
        answerImages[4] = new ImageIcon("img/respuesta.png");
        
        panel = new Rectangle(0, 3 * getHeight() / 4, getWidth(), getHeight() / 4);
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
                    // ------------Select random image-------------------------
                    ImageIcon answerImage = answerImages[randomInteger(0, NUMBER_OF_ANSWERS)];
                    // --------------------------------------------------------

                    if (respuestas.size() > 0) {
                        Respuesta ultimaRespuesta = respuestas.get(respuestas.size() - 1);
                        Respuesta nuevaRespuesta;
                        do {
                            nuevaRespuesta = new Respuesta(randomInteger(0, 1 + getWidth() - answerImage.getIconWidth()), -answerImage.getIconHeight(), answerImage, "4");
                        } while (ultimaRespuesta.collidesWith(nuevaRespuesta));
                        respuestas.add(nuevaRespuesta);
                    } else {
                        respuestas.add(new Respuesta(randomInteger(0, 1 + getWidth() - answerImage.getIconWidth()), -answerImage.getIconHeight(), answerImage, "4"));
                    }
                }

                // revisar la colision de respuestas
                int i = 0;
                while (i < respuestas.size()) {
                    Respuesta respuesta = respuestas.get(i);
                    //if (personaje.collidesWith(respuesta) || respuesta.getY() > getHeight())
                    if (personaje.collidesWith(respuesta)) {
                        if (respuesta.isCorrect(preguntas.get(preguntaActual))) {
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
                        preguntaActual = (preguntaActual + 1) % 2;
                        respuestas.remove(i);
                    } else if (respuesta.getY() > getHeight() - panel.getHeight()) {
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
            } else {
                System.out.print("");
            }
        }
    }

    public void pasarNivel(int level) {
        sameLevel = false;
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
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, getWidth(), getHeight());
        dbg.setColor(getForeground());

        // dibujar el personaje principal
        dbg.drawImage(personaje.getImage(), personaje.getX(), personaje.getY(), this);

        // dibujar las respuestas
        for (Respuesta respuesta : respuestas) {
            dbg.drawImage(respuesta.getImage(), respuesta.getX(), respuesta.getY(), this);
        }

        dbg.setColor(Color.WHITE);

        
        dbg.fillRect((int) panel.getX(), (int) panel.getY(), (int) panel.getWidth(), (int) panel.getHeight());

        dbg.setFont(new Font("Baskerville Old Face", Font.BOLD, 20));
        dbg.setColor(Color.BLACK);
        dbg.drawString("Score: " + score, 7 * (int) panel.getWidth() / 8, getHeight() - 3 * (int) panel.getHeight() / 4);
        dbg.drawString("Vidas:" + lives, 7 * (int) panel.getWidth() / 8, getHeight() - (int) panel.getHeight() / 4);

        dbg.setColor(Color.RED);
        dbg.drawString(preguntas.get(preguntaActual).toString(), (int) panel.getWidth() / 2, getHeight() - (int) panel.getHeight() / 2);

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
