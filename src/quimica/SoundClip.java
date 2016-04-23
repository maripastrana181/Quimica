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
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import java.io.IOException;
import java.net.URL;

public class SoundClip {

    private AudioInputStream sample;
    private Clip clip;
    private boolean looping = false;
    private int repeat = 0;
    private String filename = "";

    /**
     * Metodo constructor de la clase Soundclip
     */
    public SoundClip() {

        try {

            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {

            System.out.println("Error en " + e.toString());
        }
    }

    /**
     * Constructor con parametros
     *
     * @param filename nombre del soundclip
     */
    public SoundClip(String filename) {

        this();
        load(filename);
    }

    /**
     * Metodo para modificar el loop
     *
     * @param looping indica si se prende o se apaga el loop
     */
    public void setLooping(boolean looping) {

        this.looping = looping;
    }

    /**
     * Metodo para modificar la repeticion del soundclip
     *
     * @param repeat es el parametro repeat del objeto soundclip
     */
    public void setRepeat(int repeat) {

        this.repeat = repeat;
    }

    /**
     * Metodo para modificar el nombre del objeto
     *
     * @param filename es el nombre del objeto
     */
    public void setFilename(String filename) {

        this.filename = filename;
    }

    /**
     * Metodo para obtener el atributo clip del objeto
     *
     * @return clip es el atributo clip del objeto
     */
    public Clip getClip() {

        return clip;
    }

    /**
     * Metodo para obtener si el objeto soundclip esta en un loop
     *
     * @return loooping es el parametro de loop del objeto
     */
    public boolean getLooping() {

        return looping;
    }

    /**
     * Metodo para obtener el parametro repeat
     *
     * @return repeat es el parametro repeat
     */
    public int getRepeat() {

        return repeat;
    }

    /**
     * Metodo para obtener el nombre del objeto
     *
     * @return filename es el nombre del objeto
     */
    public String getFilename() {

        return filename;
    }

    /**
     * Metodo para obtener el URL del objeto
     *
     * @param filename es el nombre del objeto
     * @return url es el url del objeto
     */
    private URL getURL(String filename) {

        URL url = null;
        try {

            url = this.getClass().getResource(filename);
        } catch (Exception e) {

            System.out.println("Error en " + e.toString());
        }
        return url;
    }

    /**
     * Metodo para regresar el parametro sample del objeto
     *
     * @return sample == null es el parametro sample del objeto
     */
    public boolean isLoaded() {

        return (boolean) (sample != null);
    }

    /**
     * Metodo para cargar el objeto soundclip
     *
     * @param audiofile es el nombre del audiofile
     * @return true si fue posible, false si no
     */
    public boolean load(String audiofile) {

        try {

            setFilename(audiofile);
            sample = AudioSystem.getAudioInputStream(getURL(filename));
            clip.open(sample);
            return true;
        } catch (IOException e) {

            System.out.println("Error en " + e.toString());
            return false;
        } catch (UnsupportedAudioFileException e) {

            System.out.println("Error en " + e.toString());
            return false;
        } catch (LineUnavailableException e) {

            System.out.println("Error en " + e.toString());
            return false;
        }
    }

    /**
     * Metodo para reproducir el soundclip
     */
    public void play() {

        if (!isLoaded()) {
            return;
        }

        clip.setFramePosition(0);

        if (looping) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            clip.loop(repeat);
        }
    }

    /**
     * Metodo para detener el soundclip
     */
    public void stop() {

        clip.stop();
    }
}