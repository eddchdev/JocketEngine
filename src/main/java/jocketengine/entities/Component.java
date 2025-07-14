package jocketengine.entities;

/**
 * Interface base para todos os componentes que podem ser anexados a uma entidade.
 * <p>
 * Ex: física, animação, script, inventário...
 * </p>
 * 
 * @author Eddch
 */
public interface Component {

    /**
     * Atualiza o componente.
     * 
     * @param dt delta time (tempo desde último frame)
     */
    void update(float dt);
}