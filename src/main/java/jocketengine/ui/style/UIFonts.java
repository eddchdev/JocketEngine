package jocketengine.ui.style;

import java.awt.Font;

/**
 * Tipografia padrão da interface da JocketEngine.
 * <p>
 * Os tamanhos estão no <b>espaço lógico</b>: a UI é desenhada em resolução nativa
 * com antialiasing, então uma fonte de tamanho 28 aqui aparece grande e suave na
 * tela. Usa famílias sem serifa do próprio JDK, para um visual limpo e portável.
 * </p>
 *
 * @author Eddch
 */
public final class UIFonts {

    private static final String FAMILY = "SansSerif";

    /** Títulos e cabeçalhos. */
    public static final Font TITLE = new Font(FAMILY, Font.BOLD, 28);

    /** Subtítulos e legendas. */
    public static final Font SUBTITLE = new Font(FAMILY, Font.PLAIN, 10);

    /** Texto de botões e rótulos comuns. */
    public static final Font BUTTON = new Font(FAMILY, Font.BOLD, 13);

    /** HUD do jogo (placar, indicadores). */
    public static final Font HUD = new Font(FAMILY, Font.BOLD, 11);

    /** Texto pequeno e dicas. */
    public static final Font SMALL = new Font(FAMILY, Font.PLAIN, 9);

    /** Fonte padrão (igual a {@link #BUTTON}). */
    public static final Font DEFAULT = BUTTON;

    private UIFonts() {
    }
}
