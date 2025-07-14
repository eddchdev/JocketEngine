package jocketengine.scene;

import java.awt.Graphics;
import java.util.Stack;

/**
 * Classe responsável por controlar as cenas ativas do jogo.
 * <p>
 * Suporta empilhamento de cenas — ideal para menus de pausa sobre o jogo,
 * cutscenes ou janelas temporárias. A cena do topo da pilha é a ativa.
 * </p>
 *
 * @author Eddch
 */
public class SceneManager {

    /** Pilha de cenas ativas (a do topo é a atual) */
    private static final Stack<Scene> sceneStack = new Stack<>();

    /**
     * Carrega uma nova cena, limpando todas as anteriores.
     *
     * @param newScene nova cena a ser carregada
     */
    public static void changeScene(Scene newScene) {
        // Finaliza todas as cenas anteriores
        while (!sceneStack.isEmpty()) {
            sceneStack.pop().onExit();
        }

        sceneStack.push(newScene);
        newScene.onLoad();
    }

    /**
     * Empilha uma nova cena sobre a atual (ex: menu pausa sobre jogo).
     *
     * @param scene nova cena empilhada
     */
    public static void pushScene(Scene scene) {
        sceneStack.push(scene);
        scene.onLoad();
    }

    /**
     * Remove a cena atual do topo e volta para a anterior.
     */
    public static void popScene() {
        if (!sceneStack.isEmpty()) {
            sceneStack.pop().onExit();
        }
    }

    /**
     * Atualiza apenas a cena atual (topo da pilha).
     *
     * @param dt delta time (tempo em segundos desde o último frame)
     */
    public static void update(float dt) {
        if (!sceneStack.isEmpty()) {
            sceneStack.peek().update(dt);
        }
    }

    /**
     * Renderiza a cena atual.
     *
     * @param g objeto Graphics usado para desenhar
     */
    public static void render(Graphics g) {
        if (!sceneStack.isEmpty()) {
            sceneStack.peek().render(g);
        }
    }

    /**
     * Retorna a cena atual (topo da pilha).
     *
     * @return cena atual
     */
    public static Scene getCurrent() {
        return sceneStack.isEmpty() ? null : sceneStack.peek();
    }

    /**
     * Limpa todas as cenas carregadas.
     */
    public static void clear() {
        while (!sceneStack.isEmpty()) {
            sceneStack.pop().onExit();
        }
    }
}