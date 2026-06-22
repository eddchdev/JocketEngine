package jocketengine.audio;

import jocketengine.math.MathUtils;
import jocketengine.utils.Logger;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * Fachada de áudio da engine. Toca {@link Clip}s respeitando um volume mestre e
 * tolera com elegância a ausência de dispositivo de som (em CI, por exemplo):
 * nesse caso apenas não emite som, sem lançar exceções.
 *
 * @author Eddch
 */
public final class Audio {

    private static boolean enabled = true;
    private static float masterVolume = 1f;

    private Audio() {
    }

    /** Liga/desliga todo o áudio. */
    public static void setEnabled(boolean value) {
        enabled = value;
    }

    public static boolean isEnabled() {
        return enabled;
    }

    /** Define o volume mestre (0 = mudo, 1 = máximo). */
    public static void setMasterVolume(float volume) {
        masterVolume = MathUtils.clamp(volume, 0f, 1f);
    }

    public static float getMasterVolume() {
        return masterVolume;
    }

    /**
     * Toca um clipe do início. Seguro de chamar com {@code null} (no-op).
     *
     * @param clip clipe a tocar (pode ser {@code null})
     */
    public static void play(Clip clip) {
        if (!enabled || clip == null) {
            return;
        }
        try {
            applyVolume(clip, masterVolume);
            clip.setFramePosition(0);
            clip.start();
        } catch (RuntimeException e) {
            Logger.warn("Audio", "Falha ao tocar clipe: " + e.getMessage());
        }
    }

    /** Toca um clipe em loop contínuo (útil para música). */
    public static void loop(Clip clip) {
        if (!enabled || clip == null) {
            return;
        }
        applyVolume(clip, masterVolume);
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /** Para um clipe, se estiver tocando. */
    public static void stop(Clip clip) {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    private static void applyVolume(Clip clip, float volume) {
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = volume <= 0 ? gain.getMinimum() : (float) (20.0 * Math.log10(volume));
            gain.setValue(MathUtils.clamp(dB, gain.getMinimum(), gain.getMaximum()));
        }
    }
}
