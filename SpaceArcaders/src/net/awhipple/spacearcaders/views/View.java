/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.views;

import java.util.List;
import net.awhipple.spacearcaders.utils.GameGlobals;
import org.newdawn.slick.Input;

/**
 *
 * @author Aaron
 */
public abstract class View {
    protected GameGlobals globals;
    
    public abstract List<ViewInstruction> update(Input input);
    public abstract void render();
    
    public void setGlobals(GameGlobals globals) {
        this.globals = globals;
    }
}
