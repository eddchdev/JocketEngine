package jocketengine.ui.style;

import java.awt.Font;

/**
 * Fonte padrão usada em toda a interface da JocketEngine.
 * <p>
 * Fontes podem ser trocadas por .ttf carregadas dinamicamente no futuro.
 * </p>
 * 
 * @author Eddch
 */
public class UIFonts {

    public static final Font DEFAULT = new Font("Arial", Font.PLAIN, 16);
    public static final Font TITLE = new Font("Arial", Font.BOLD, 24);
    public static final Font MONO = new Font("Courier New", Font.PLAIN, 14);

    private UIFonts() {}
}