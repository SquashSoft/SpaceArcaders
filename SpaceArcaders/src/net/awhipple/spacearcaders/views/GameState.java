/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.views;

import java.util.List;
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
    
    private View curView, gf;
    
    public GameState(int SCREEN_W, int SCREEN_H, int TARGET_FPS) throws SlickException {
        this.screenWidth = SCREEN_W;
        this.screenHeight = SCREEN_H;
        this.targetFps = TARGET_FPS;
        
        resourceLibrary = new ResourceLibrary();
        
        gf = new GameField(resourceLibrary, screenWidth, screenHeight, targetFps);
        curView = gf;
    }

    public void update(Input input) {
        List<ViewInstruction> instructions = curView.update(input);
        if(instructions != null) {
            for(ViewInstruction instruction : instructions) {
                switch(instruction.getInstruction()) {
                    case SWITCH_VIEW:
                        curView = (View)instruction.getContents();
                        break;
                }
            }
        }
    }
    
    public void render() {
        curView.render();
    }
}
