package jocketengine.graphics;

import jocketengine.math.MathUtils;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 * Câmera 2D ortográfica: define qual parte do mundo aparece na tela, com
 * suporte a zoom, seguir um alvo suavemente e limites de mundo.
 * <p>
 * Uso típico no {@code render} de uma cena:
 * </p>
 * <pre>{@code
 * camera.begin(g);
 * // ... desenha o mundo em coordenadas de mundo ...
 * camera.end(g);
 * }</pre>
 *
 * @author Eddch
 */
public class Camera2D {

    private final int viewWidth;
    private final int viewHeight;

    private float x;
    private float y;
    private float zoom = 1f;

    private float minX = Float.NEGATIVE_INFINITY;
    private float minY = Float.NEGATIVE_INFINITY;
    private float maxX = Float.POSITIVE_INFINITY;
    private float maxY = Float.POSITIVE_INFINITY;

    private AffineTransform saved;

    /**
     * @param viewWidth  largura da área visível (resolução lógica)
     * @param viewHeight altura da área visível (resolução lógica)
     */
    public Camera2D(int viewWidth, int viewHeight) {
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
    }

    /** Posiciona o centro da câmera (depois aplica os limites). */
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        clampToBounds();
    }

    /** Define o zoom (1 = normal, &gt;1 aproxima). */
    public void setZoom(float zoom) {
        this.zoom = Math.max(0.01f, zoom);
        clampToBounds();
    }

    public float getZoom() {
        return zoom;
    }

    /**
     * Limita o centro da câmera para que a vista não saia da área do mundo.
     *
     * @param minX borda esquerda do mundo
     * @param minY borda superior do mundo
     * @param maxX borda direita do mundo
     * @param maxY borda inferior do mundo
     */
    public void setBounds(float minX, float minY, float maxX, float maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
        clampToBounds();
    }

    /**
     * Move o centro da câmera suavemente em direção a um ponto.
     *
     * @param targetX   X alvo (mundo)
     * @param targetY   Y alvo (mundo)
     * @param smoothing fração da distância percorrida por chamada (0..1; 1 = instantâneo)
     */
    public void follow(float targetX, float targetY, float smoothing) {
        smoothing = MathUtils.clamp(smoothing, 0f, 1f);
        x += (targetX - x) * smoothing;
        y += (targetY - y) * smoothing;
        clampToBounds();
    }

    private void clampToBounds() {
        float halfW = viewWidth / (2f * zoom);
        float halfH = viewHeight / (2f * zoom);

        if (maxX - minX <= 2 * halfW) {
            x = (minX + maxX) / 2f;
        } else {
            x = MathUtils.clamp(x, minX + halfW, maxX - halfW);
        }

        if (maxY - minY <= 2 * halfH) {
            y = (minY + maxY) / 2f;
        } else {
            y = MathUtils.clamp(y, minY + halfH, maxY - halfH);
        }
    }

    /** @return X do canto superior esquerdo da vista, em coordenadas de mundo. */
    public float getOffsetX() {
        return x - viewWidth / (2f * zoom);
    }

    /** @return Y do canto superior esquerdo da vista, em coordenadas de mundo. */
    public float getOffsetY() {
        return y - viewHeight / (2f * zoom);
    }

    /**
     * Aplica a transformação da câmera, salvando a transformação atual.
     * Deve ser seguido por {@link #end(Graphics2D)}.
     */
    public void begin(Graphics2D g) {
        saved = g.getTransform();
        g.scale(zoom, zoom);
        g.translate(-getOffsetX(), -getOffsetY());
    }

    /** Restaura a transformação salva por {@link #begin(Graphics2D)}. */
    public void end(Graphics2D g) {
        if (saved != null) {
            g.setTransform(saved);
            saved = null;
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
