package net.awhipple.spacearcaders;

import net.awhipple.spacearcaders.views.GameState;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Aaron
 */

public class SpaceArcaders extends BasicGame {
    
    private static final int SCREEN_W = 1600, SCREEN_H = 900;
    private static final boolean FULLSCREEN_FLAG = false;
    private static final int TARGET_FPS = 60;

    private GameState gs;
    
    @Override
    public void init(GameContainer gc) throws SlickException {
        
        try {
            gs = new GameState(SCREEN_W, SCREEN_H, TARGET_FPS);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            gc.exit();
            return;
        }

        gc.setAlwaysRender(true);
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        
        Input input = gc.getInput();
        gs.update(input);
        
        if(input.isKeyDown(Input.KEY_ESCAPE)) gc.exit();
    }

    @Override
    public void render(GameContainer gc, Graphics grphcs) throws SlickException {
        gs.render();
    }
        
    public static void main(String[] args) {
        try {
            AppGameContainer app = new AppGameContainer(new SpaceArcaders());
            app.setDisplayMode(SCREEN_W, SCREEN_H, FULLSCREEN_FLAG);
            app.setTargetFrameRate(TARGET_FPS);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
    
    public SpaceArcaders() {
        super("SpaceArcaders");
    }
}
