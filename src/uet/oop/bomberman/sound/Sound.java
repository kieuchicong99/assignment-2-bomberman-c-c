package uet.oop.bomberman.sound;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.net.URL;

public class Sound {
    public static void play(String sound) {
        Runnable runnable = () -> {
            try {
                ClassLoader loader = Thread.currentThread().getContextClassLoader();
                URL url = loader.getResource("./sound/" + sound + ".wav");
                String path = url.getPath();
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(new File(path)));
                if (sound.equals("background")) clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
            } catch (Exception exc) {
                exc.printStackTrace(System.out);
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }
}
