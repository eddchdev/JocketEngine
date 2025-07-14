package jocketengine.entities;

import jocketengine.graphics.SpriteAnimation;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Gerencia e renderiza animações de uma entidade.
 * 
 * @author Eddch
 */
public class AnimationPlayer implements Component {

    private final Map<String, SpriteAnimation> animations = new HashMap<>();
    private String current = null;

    public void addAnimation(String name, SpriteAnimation animation) {
        animations.put(name, animation);
        if (current == null) current = name;
    }

    public void play(String name) {
        if (!name.equals(current)) {
            current = name;
            animations.get(current).reset();
        }
    }

    public void render(Graphics g, int x, int y) {
        if (current != null && animations.containsKey(current)) {
            BufferedImage frame = animations.get(current).getCurrentFrame();
            g.drawImage(frame, x, y, null);
        }
    }

    @Override
    public void update(float dt) {
        if (current != null && animations.containsKey(current)) {
            animations.get(current).update(dt);
        }
    }
}