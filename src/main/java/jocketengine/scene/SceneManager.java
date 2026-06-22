package jocketengine.scene;

import java.awt.Graphics;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Controla as cenas ativas do jogo usando uma pilha.
 * <p>
 * Apenas a cena do topo é <b>atualizada</b>, mas todas são <b>renderizadas</b>,
 * de baixo para cima — o que permite sobreposições translúcidas (ex.: um menu de
 * pausa por cima do jogo congelado).
 * </p>
 *
 * @author Eddch
 */
public final class SceneManager {

    private static final Deque<Scene> sceneStack = new ArrayDeque<>();

    private SceneManager() {
    }

    /**
     * Substitui toda a pilha por uma nova cena.
     *
     * @param newScene nova cena
     */
    public static void changeScene(Scene newScene) {
        while (!sceneStack.isEmpty()) {
            sceneStack.pop().onExit();
        }
        sceneStack.push(newScene);
        newScene.onLoad();
    }

    /**
     * Empilha uma cena sobre a atual (ex.: menu de pausa).
     *
     * @param scene cena a empilhar
     */
    public static void pushScene(Scene scene) {
        sceneStack.push(scene);
        scene.onLoad();
    }

    /** Remove a cena do topo e retorna à anterior. */
    public static void popScene() {
        if (!sceneStack.isEmpty()) {
            sceneStack.pop().onExit();
        }
    }

    /**
     * Atualiza apenas a cena do topo.
     *
     * @param dt delta time em segundos
     */
    public static void update(float dt) {
        Scene current = sceneStack.peek();
        if (current != null) {
            current.update(dt);
        }
    }

    /**
     * Renderiza toda a pilha, da base ao topo.
     *
     * @param g contexto gráfico
     */
    public static void render(Graphics g) {
        // ArrayDeque como pilha: iterator vai do topo para a base, então percorremos ao contrário.
        Scene[] scenes = sceneStack.toArray(new Scene[0]);
        for (int i = scenes.length - 1; i >= 0; i--) {
            scenes[i].render(g);
        }
    }

    /** @return cena do topo, ou {@code null} se a pilha estiver vazia. */
    public static Scene getCurrent() {
        return sceneStack.peek();
    }

    /** Remove todas as cenas. */
    public static void clear() {
        while (!sceneStack.isEmpty()) {
            sceneStack.pop().onExit();
        }
    }
}
