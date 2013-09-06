package spacearcaders;

import org.newdawn.slick.Input;

/**
 *
 * @author Aaron
 */
public interface Actor {
    public void draw();
    public void update(Input input, int delta);
    }


