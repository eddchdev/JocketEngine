package jocketengine.audio;

import jocketengine.utils.Logger;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Gerador de efeitos sonoros <b>procedurais</b>: sintetiza ondas direto em
 * memória e devolve {@link Clip}s prontos para o {@link Audio}. Não depende de
 * nenhum arquivo de áudio.
 * <p>
 * Se o ambiente não tiver dispositivo de som, os métodos retornam {@code null}
 * (e {@link Audio#play} simplesmente ignora).
 * </p>
 *
 * @author Eddch
 */
public final class Sfx {

    private static final float SAMPLE_RATE = 44100f;

    private Sfx() {
    }

    /**
     * Sintetiza um clipe a partir de uma sequência de notas (frequência + duração).
     *
     * @param volume       volume base (0..1)
     * @param freqAndMs    pares {@code frequênciaHz, duraçãoMs} em sequência
     * @return o clipe, ou {@code null} se o áudio não estiver disponível
     */
    public static Clip sequence(float volume, float... freqAndMs) {
        int totalMs = 0;
        for (int i = 1; i < freqAndMs.length; i += 2) {
            totalMs += (int) freqAndMs[i];
        }
        int totalSamples = (int) (SAMPLE_RATE * totalMs / 1000f);
        byte[] data = new byte[totalSamples * 2];

        int offset = 0;
        for (int i = 0; i + 1 < freqAndMs.length; i += 2) {
            float freq = freqAndMs[i];
            int ms = (int) freqAndMs[i + 1];
            int samples = (int) (SAMPLE_RATE * ms / 1000f);
            for (int s = 0; s < samples && offset < totalSamples; s++, offset++) {
                float t = s / SAMPLE_RATE;
                float envelope = envelope(s, samples);
                double value = Math.sin(2 * Math.PI * freq * t) * volume * envelope;
                short sample = (short) (value * Short.MAX_VALUE);
                data[offset * 2] = (byte) (sample & 0xFF);
                data[offset * 2 + 1] = (byte) ((sample >> 8) & 0xFF);
            }
        }
        return toClip(data);
    }

    /** Som de moeda/pickup: dois tons curtos e ascendentes. */
    public static Clip coin() {
        return sequence(0.5f, 988f, 60, 1319f, 110);
    }

    /** Som de pulo: um tom curto subindo. */
    public static Clip jump() {
        return sequence(0.4f, 392f, 50, 587f, 70);
    }

    /** Tom simples. */
    public static Clip tone(float freq, int ms) {
        return sequence(0.5f, freq, ms);
    }

    /** Envelope ataque/decaimento para evitar estalos no início e no fim. */
    private static float envelope(int sample, int total) {
        int attack = (int) (SAMPLE_RATE * 0.005f); // 5 ms
        if (sample < attack) {
            return sample / (float) attack;
        }
        float remaining = (total - sample) / (float) total;
        return Math.min(1f, remaining * 4f);
    }

    private static Clip toClip(byte[] data) {
        try {
            AudioFormat format = new AudioFormat(SAMPLE_RATE, 16, 1, true, false);
            Clip clip = AudioSystem.getClip();
            clip.open(format, data, 0, data.length);
            return clip;
        } catch (Exception e) {
            Logger.debug("Sfx", "Áudio indisponível: " + e.getMessage());
            return null;
        }
    }
}
