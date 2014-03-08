/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.views;

import java.util.List;
import net.awhipple.spacearcaders.utils.GameGlobals;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Aaron
 */
public class GameState {

    private GameGlobals globals;
    
    private View curView;
    
    public GameState(int SCREEN_W, int SCREEN_H, int TARGET_FPS) throws SlickException {
        globals = new GameGlobals(SCREEN_W, SCREEN_H, TARGET_FPS);
        

        curView = new TitleScreen();
        curView.setGlobals(globals.copy());

        globals.playMusic("theme");
    }

    public void update(Input input) {
        List<ViewInstruction> instructions = curView.update(input);
        if(instructions != null) {
            for(ViewInstruction instruction : instructions) {
                switch(instruction.getInstruction()) {
                    case SWITCH_VIEW:
                        curView = (View)instruction.getContents();
                        curView.setGlobals(globals.copy());
                        break;
                }
            }
        }
    }
    
    public void render() {
        curView.render();
    }
}
