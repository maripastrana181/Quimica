/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package quimica;

/**
 *
 * @author Maribel
 */
public class Pregunta {
    
    private Molecula reactivo1;
    private Molecula reactivo2;
    private Molecula resultante;

    
    public Pregunta(Molecula r1, Molecula r2, Molecula resp){
        reactivo1 = r1;
        reactivo2 = r2;
        resultante = resp;
    }
    
    public Molecula getMoleculaResultante(){
        return resultante;
    }
    
    public int getIndex() {
        return resultante.getIndex();
    }
}
