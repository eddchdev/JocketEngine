package jocketengine.scene.scenes;

import jocketengine.Game;
import jocketengine.scene.Scene;
import jocketengine.ui.UIManager;
import jocketengine.ui.elements.Button;
import jocketengine.ui.elements.Label;

import java.awt.*;

/**
 * Cena inicial do jogo, responsável por exibir o menu principal.
 * <p>
 * Contém o título e botões "Iniciar Jogo" e "Sair".
 * Usa o sistema de UI da JocketEngine.
 * </p>
 *
 * @author Eddch
 */
public class MainMenuScene extends Scene {

    private Font titleFont;

    /**
     * Carrega os elementos da cena (título e botões).
     */
    @Override
    public void onLoad() {
        UIManager.clear(); // Garante que a interface comece limpa

        int centerX = Game.getGameWidth() * 3 / 2; // WIDTH * SCALE
        int spacing = 60;

        // Carrega uma fonte bonita (opcional)
        titleFont = new Font("Arial", Font.BOLD, 36);

        // Título
        UIManager.add(new Label(centerX - 100, 80, "JocketEngine", titleFont, Color.WHITE));

        // Botão: Iniciar Jogo (ainda sem ação real)
        Button startButton = new Button(centerX - 100, 160, 200, 40, "Iniciar Jogo");
        startButton.setOnClick(() -> {
            System.out.println("Jogo iniciado (futuro: carregar GameScene)");
        });

        // Botão: Sair
        Button exitButton = new Button(centerX - 100, 220, 200, 40, "Sair");
        exitButton.setOnClick(Game::exit);

        // Adiciona os botões à UI
        UIManager.add(startButton);
        UIManager.add(exitButton);
    }

    /**
     * Atualiza a lógica da cena (nada específico aqui).
     *
     * @param dt delta time (tempo em segundos desde o último frame)
     */
    @Override
    public void update(float dt) {
        // Lógica do menu pode ser adicionada aqui (animações, efeitos)
    }

    /**
     * Renderiza o fundo do menu e deixa o restante para o UIManager.
     *
     * @param g objeto Graphics para desenhar
     */
    @Override
    public void render(Graphics g) {
        g.setColor(new Color(20, 20, 30));
        g.fillRect(0, 0, Game.getGameWidth() * 3, Game.getGameHeight() * 3);
    }

    /**
     * Limpa elementos da UI ao sair do menu.
     */
    @Override
    public void onExit() {
        UIManager.clear();
    }
}