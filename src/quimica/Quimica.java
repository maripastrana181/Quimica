/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package quimica;

import javax.swing.JFrame;

/**
 *
 * @author Maribel
 */
public class Quimica extends JFrame {
    
    private final static int WINDOW_WIDTH = 1000;
    private final static int WINDOW_HEIGHT = 600;
    
    public Quimica() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("Quimica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Quimica game = new Quimica();
        game.setVisible(true);
    }
    
}
