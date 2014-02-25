/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.views;

import net.awhipple.spacearcaders.utils.ResourceLibrary;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Aaron
 */
public class GameState {

    private int screenWidth, screenHeight, targetFps;
    
    private ResourceLibrary resourceLibrary;
    
    private View curView, gf1, gf2;
    boolean tabPressed = false;
    
    public GameState(int SCREEN_W, int SCREEN_H, int TARGET_FPS) throws SlickException {
        this.screenWidth = SCREEN_W;
        this.screenHeight = SCREEN_H;
        this.targetFps = TARGET_FPS;
        
        resourceLibrary = new ResourceLibrary();
        
        gf1 = new GameField(resourceLibrary, screenWidth, screenHeight, targetFps);
        gf2 = new GameField(resourceLibrary, screenWidth, screenHeight, targetFps);
        curView = gf1;
    }

    public void update(Input input) {
        if(true||!input.isKeyDown(Input.KEY_TAB)) tabPressed = false;
        if(input.isKeyDown(Input.KEY_TAB) && !tabPressed) {
            tabPressed = true;
            if(curView == gf1) curView = gf2;
            else curView = gf1;
        }
        
        curView.update(input);
    }
    
    public void render() {
        curView.render();
    }
}
