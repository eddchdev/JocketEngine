package jocketengine.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PreferencesTest {

    @Test
    void savesAndLoadsValues(@TempDir Path dir) {
        File file = dir.resolve("save.properties").toFile();

        new Preferences(file)
                .putInt("highScore", 42)
                .putString("name", "edd")
                .putBoolean("muted", true)
                .putFloat("volume", 0.5f)
                .save();

        Preferences loaded = new Preferences(file).load();
        assertEquals(42, loaded.getInt("highScore", 0));
        assertEquals("edd", loaded.getString("name", ""));
        assertTrue(loaded.getBoolean("muted", false));
        assertEquals(0.5f, loaded.getFloat("volume", 0f), 1e-4);
    }

    @Test
    void returnsDefaultsForMissingKeys(@TempDir Path dir) {
        Preferences prefs = new Preferences(dir.resolve("empty.properties").toFile()).load();
        assertEquals(7, prefs.getInt("missing", 7));
        assertEquals("x", prefs.getString("missing", "x"));
    }
}
