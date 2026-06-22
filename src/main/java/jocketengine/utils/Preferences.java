package jocketengine.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Properties;

/**
 * Persistência simples de preferências chave/valor (volume, recordes, opções),
 * gravada como um arquivo {@code .properties}.
 * <pre>{@code
 * Preferences prefs = new Preferences(new File("save.properties"));
 * prefs.load();
 * int highScore = prefs.getInt("highScore", 0);
 * prefs.putInt("highScore", highScore + 1).save();
 * }</pre>
 *
 * @author Eddch
 */
public final class Preferences {

    private final File file;
    private final Properties properties = new Properties();

    public Preferences(File file) {
        this.file = file;
    }

    /** Carrega os valores do arquivo, se ele existir. Retorna {@code this} para encadear. */
    public Preferences load() {
        if (file.exists()) {
            try (InputStream in = Files.newInputStream(file.toPath())) {
                properties.load(in);
            } catch (IOException e) {
                Logger.warn("Preferences", "Falha ao carregar " + file + ": " + e.getMessage());
            }
        }
        return this;
    }

    /** Grava os valores no arquivo. Retorna {@code this} para encadear. */
    public Preferences save() {
        try {
            File parent = file.getAbsoluteFile().getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }
            try (OutputStream out = Files.newOutputStream(file.toPath())) {
                properties.store(out, "JocketEngine preferences");
            }
        } catch (IOException e) {
            Logger.error("Preferences", "Falha ao salvar " + file + ": " + e.getMessage());
        }
        return this;
    }

    public Preferences putString(String key, String value) {
        properties.setProperty(key, value);
        return this;
    }

    public Preferences putInt(String key, int value) {
        return putString(key, Integer.toString(value));
    }

    public Preferences putFloat(String key, float value) {
        return putString(key, Float.toString(value));
    }

    public Preferences putBoolean(String key, boolean value) {
        return putString(key, Boolean.toString(value));
    }

    public String getString(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        try {
            String v = properties.getProperty(key);
            return v == null ? defaultValue : Integer.parseInt(v);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public float getFloat(String key, float defaultValue) {
        try {
            String v = properties.getProperty(key);
            return v == null ? defaultValue : Float.parseFloat(v);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        String v = properties.getProperty(key);
        return v == null ? defaultValue : Boolean.parseBoolean(v);
    }
}
