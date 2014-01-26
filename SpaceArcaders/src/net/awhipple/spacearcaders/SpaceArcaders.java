package net.awhipple.spacearcaders;

import net.awhipple.spacearcaders.utils.GameState;
import net.awhipple.spacearcaders.gameobjects.Actor;
import net.awhipple.spacearcaders.gameobjects.PlayerShip;
import java.util.List;
import net.awhipple.spacearcaders.ui.UIBar;
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

    GameState gs;
    
    boolean gameIsPaused = false;
    
    @Override
    public void init(GameContainer gc) throws SlickException {
        
        try {
            gs = new GameState(SCREEN_W, SCREEN_H, TARGET_FPS);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            gc.exit();
            return;
        }
        
        gs.createStarMap();
        
        PlayerShip player1 = new PlayerShip(SCREEN_W/4,3*SCREEN_H/4, gs.getLibraryImage("ship") );
        player1.setKeys(Input.KEY_W, Input.KEY_S, Input.KEY_A, Input.KEY_D, Input.KEY_J);
        gs.addPlayer(player1);
        
        PlayerShip player2 = new PlayerShip(3*SCREEN_W/4,3*SCREEN_H/4, gs.getLibraryImage("ship") );
        player2.setKeys(Input.KEY_UP, Input.KEY_DOWN, Input.KEY_LEFT, Input.KEY_RIGHT, Input.KEY_RSHIFT);
        gs.addPlayer(player2);
        
        UIBar uiBar1 = new UIBar(player1.getHealth(), 180, 50);
        gs.queueNewActor(uiBar1);
        
        gs.updateActorList();
        
        gc.setAlwaysRender(true);
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        
        //Handles pausing the game when the window loses focus
        if(!gc.hasFocus()) { 
            gs.pause(gs.getLibraryImage("pause"));
            gameIsPaused = true;
            return;
        } else if(gameIsPaused) { 
            gs.unpause(); 
            gameIsPaused = false;
        }
        
        Input input = gc.getInput();
        gs.setInput(input);
        
        List<Actor> actorList = gs.getActorList();
        for(Actor curActor : actorList) {
            curActor.update(gs);
        }
        gs.updateActorList();
        
        if(input.isKeyDown(Input.KEY_ESCAPE)) gc.exit();
    }

    @Override
    public void render(GameContainer gc, Graphics grphcs) throws SlickException {
        List<Actor> actorList = gs.getActorList();
        for (Actor curActor : actorList) {
            curActor.draw();
        }
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
