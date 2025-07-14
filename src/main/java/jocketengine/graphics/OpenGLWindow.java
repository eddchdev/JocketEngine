package jocketengine.graphics;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

/**
 * Janela básica utilizando OpenGL com GLFW + LWJGL 3.
 * Mostra uma tela azul escura com clearing automático.
 *
 * <p>Ideal para testes iniciais de contexto OpenGL e futuro uso em jogos 2D ou 3D.</p>
 *
 * @author Edd
 */
public class OpenGLWindow {

    private long window;

    /**
     * Executa a janela OpenGL.
     */
    public void run() {
        init();
        loop();
        cleanup();
    }

    /**
     * Inicializa o GLFW e cria a janela.
     */
    private void init() {
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Falha ao inicializar GLFW");
        }

        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);

        window = GLFW.glfwCreateWindow(800, 600, "JocketEngine - OpenGL Mode", MemoryUtil.NULL, MemoryUtil.NULL);
        if (window == MemoryUtil.NULL) {
            throw new RuntimeException("Erro ao criar janela GLFW");
        }

        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();
        GLFW.glfwSwapInterval(1); // V-Sync
    }

    /**
     * Loop principal da janela OpenGL.
     */
    private void loop() {
        while (!GLFW.glfwWindowShouldClose(window)) {
            GL11.glClearColor(0.1f, 0.1f, 0.25f, 1.0f);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            // TODO: Renderizações futuras aqui (shaders, texturas, etc)

            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }
    }

    /**
     * Libera recursos da janela GLFW.
     */
    private void cleanup() {
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }
}
