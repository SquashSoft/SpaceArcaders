/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.views;

import java.util.Collections;
import java.util.List;
import net.awhipple.spacearcaders.gameobjects.StarMap;
import net.awhipple.spacearcaders.utils.GameGlobals;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Aaron
 */
public class TitleScreen extends View{
    Image title, titleText;
    StarMap starMap;
    
    public TitleScreen() {}
    
    @Override
    public void setGlobals(GameGlobals globals) {
        super.setGlobals(globals);
        title = globals.getImage("title");
        titleText = globals.getImage("titleText");
        
        starMap = globals.getStarMap();
    }
    
    @Override
    public List<ViewInstruction> update(Input input) {
        double delta = globals.getDelta();
        starMap.update(delta);
        int numPlayers = 0;
        if(input.isKeyPressed(Input.KEY_1)) numPlayers = 1;
        if(input.isKeyPressed(Input.KEY_2)) numPlayers = 2;
        if(numPlayers != 0) {
            try {
            return Collections.singletonList(new ViewInstruction(
                    ViewInstruction.Set.SWITCH_VIEW,
                    new GameField(numPlayers)));
            } catch(SlickException ex) {
                System.out.println("Could not initialize game Field");
            }
        }
        return null;
    }

    @Override
    public void render() {
        starMap.draw();
        title.draw(globals.getScreenWidth()/2-title.getWidth()/2, 120);
        titleText.draw(globals.getScreenWidth()/2-titleText.getWidth()/2, 600);
    }
    
}
