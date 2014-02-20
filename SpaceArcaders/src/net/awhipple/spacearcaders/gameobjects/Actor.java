package net.awhipple.spacearcaders.gameobjects;

import net.awhipple.spacearcaders.utils.GameState;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Aaron
 */
public interface Actor{
    public void init(GameState gs) throws SlickException;
    public void draw();
    public void update(GameState gs);
}


