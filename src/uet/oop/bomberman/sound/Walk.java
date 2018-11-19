package uet.oop.bomberman.sound;

import javax.sound.sampled.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Walk extends Thread{
    private AudioClip sound;
    private boolean isLoop;
    public Walk() {
    }

    @Override
    public void run() {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            URL url = loader.getResource("./sound/walk.wav");
            String path = url.getPath();
            AudioInputStream ais = AudioSystem.getAudioInputStream(url);
            AudioFormat format = ais.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Applet.newAudioClip(url).play();
            Thread.sleep(500);

        } catch (UnsupportedAudioFileException e) {

        } catch (IOException e){

        } catch (InterruptedException e) {

        }
    }
}
