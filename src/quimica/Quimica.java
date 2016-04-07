/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package quimica;

import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maribel
 */
public class Quimica extends JFrame implements Runnable {
    
    public final static int WINDOW_WIDTH = 1000;
    public final static int WINDOW_HEIGHT = 600;
    
    private int score;
    private int lives;
    
    private PersonajePrincipal personaje;
    private ArrayList<Respuesta> respuestas;
    
    public Quimica() {
        initUI();
        
        score = 0;
        lives = 3;
        
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
            System.out.println("Hello World");
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Quimica.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Quimica game = new Quimica();
        game.setVisible(true);
    }
    
}
