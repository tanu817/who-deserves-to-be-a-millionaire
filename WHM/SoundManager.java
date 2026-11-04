package WHM;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * WAV sound player.
 */
public class SoundManager {
    private static final Map<String, Clip> SOUND_MAP = new ConcurrentHashMap<>();

    private static Clip loadClip(String resourcePath) {
        try {
            if (SOUND_MAP.containsKey(resourcePath)) {
                return SOUND_MAP.get(resourcePath);
            }
            InputStream is = SoundManager.class.getResourceAsStream(resourcePath);
            if (is == null) {
                System.err.println("Sound resource not found: " + resourcePath);
                return null;
            }
            AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            SOUND_MAP.put(resourcePath, clip);
            return clip;
        } catch (Exception e) {
            System.err.println("Sound load error: " + resourcePath + " -> " + e.getMessage());
            return null;
        }
    }

    // public preload so UI can load SOUND_MAP at startup to avoid initial delay
    public static void preload(String resourcePath) {
        loadClip(resourcePath);
    }

    /**
     * Plays a sound once from the given resource path.
     */

    public static void playOnce(String resourcePath) {
        Clip clip = loadClip(resourcePath);
        if (clip == null) {
            return;
        }
        if (clip.isRunning()) {
            clip.stop();
        }
        clip.setFramePosition(0);
        clip.start();
    }

    /**
     * Loops a sound continuously from the given resource path.
     */

    public static void loop(String resourcePath) {
        Clip clip = loadClip(resourcePath);
        if (clip == null) {
            return;
        }
        if (clip.isRunning()) {
            clip.stop();
        }
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Stops playback of the sound from the given resource path.
     */
    public static void stop(String resourcePath) {
        Clip clip = SOUND_MAP.get(resourcePath);
        if (clip == null) {
            return;
        }
        if (clip.isRunning()) {
            clip.stop();
        }
        clip.setFramePosition(0);
    }

    /**
     * Stops playback of all sounds.
     */
    public static void stopAll() {
        for (Clip clip : SOUND_MAP.values()) {
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.setFramePosition(0);

        }
    }
}
