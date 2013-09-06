package spacearcaders;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

/**
 *
 * @author Aaron
 */
public interface Actor {
    public void draw();
    public void update(float delta);
    public void takeInput(Input input, int delta);
}


