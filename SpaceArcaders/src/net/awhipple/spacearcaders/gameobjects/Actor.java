package net.awhipple.spacearcaders.gameobjects;

import net.awhipple.spacearcaders.views.GameField;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Aaron
 */
public interface Actor{
    public void init(GameField gf) throws SlickException;
    public void draw();
    public void update(GameField gf);
}


