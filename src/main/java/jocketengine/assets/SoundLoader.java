package jocketengine.assets;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Gerenciador de carregamento e reprodução de sons na JocketEngine.
 * <p>
 * Suporta sons curtos (efeitos) e músicas.
 * </p>
 * 
 * @author Eddch
 */
public class SoundLoader {

    private static final Map<String, Clip> clips = new HashMap<>();

    /**
     * Carrega um som do caminho especificado e armazena em cache.
     * 
     * @param path caminho do arquivo de áudio (ex: "/sounds/jump.wav")
     * @return Clip carregado para reprodução
     * @throws IOException caso o arquivo não seja encontrado ou não possa ser lido
     * @throws UnsupportedAudioFileException formato não suportado
     * @throws LineUnavailableException erro ao abrir linha de áudio
     */
    public static Clip loadSound(String path) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        if (clips.containsKey(path)) {
            return clips.get(path);
        }

        try (var audioStream = SoundLoader.class.getResourceAsStream(path)) {
            if (audioStream == null) {
                throw new IOException("Som não encontrado: " + path);
            }
            AudioInputStream ais = AudioSystem.getAudioInputStream(audioStream);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clips.put(path, clip);
            return clip;
        }
    }

    /**
     * Reproduz o som armazenado em cache.
     * 
     * @param path caminho do arquivo carregado previamente
     */
    public static void playSound(String path) {
        Clip clip = clips.get(path);
        if (clip != null) {
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.setFramePosition(0);
            clip.start();
        }
    }

    /**
     * Libera todos os recursos de áudio carregados.
     */
    public static void clear() {
        for (Clip clip : clips.values()) {
            clip.close();
        }
        clips.clear();
    }
}