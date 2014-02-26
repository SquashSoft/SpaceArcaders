/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.views;

import java.util.Collections;
import java.util.List;
import net.awhipple.spacearcaders.gameobjects.StarMap;
import net.awhipple.spacearcaders.utils.ResourceLibrary;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Aaron
 */
public class TitleScreen implements View{

    ResourceLibrary resourceLibrary;
    int screenWidth, screenHeight, targetFps;
    Image title, titleText;
    StarMap starMap;
    
    public TitleScreen(ResourceLibrary resourceLibrary, int screenWidth, int screenHeight, int targetFps) {
        this.resourceLibrary = resourceLibrary;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.targetFps = targetFps;
        title = resourceLibrary.getImage("title");
        titleText = resourceLibrary.getImage("titleText");
        try {
            starMap = new StarMap();
            starMap.init(screenWidth, screenHeight);
        } catch(SlickException ex) {
            System.out.println("Could not initialize starmap on title screen.");
        }
    }
    
    @Override
    public List<ViewInstruction> update(Input input) {
        starMap.update(1f/targetFps);
        int numPlayers = 0;
        if(input.isKeyPressed(Input.KEY_1)) numPlayers = 1;
        if(input.isKeyPressed(Input.KEY_2)) numPlayers = 2;
        if(numPlayers != 0) {
            try {
            return Collections.singletonList(new ViewInstruction(
                    ViewInstruction.Set.SWITCH_VIEW,
                    new GameField(numPlayers, resourceLibrary, screenWidth, screenHeight, targetFps)));
            } catch(SlickException ex) {
                System.out.println("Could not initialize game Field");
            }
        }
        return null;
    }

    @Override
    public void render() {
        starMap.draw();
        title.draw(screenWidth/2-title.getWidth()/2, 120);
        titleText.draw(screenWidth/2-titleText.getWidth()/2, 600);
    }
    
}
