package net.awhipple.spacearcaders;

import net.awhipple.spacearcaders.gameobjects.Enemy;
import net.awhipple.spacearcaders.utils.GameState;
import net.awhipple.spacearcaders.gameobjects.PlayerShip;
import net.awhipple.spacearcaders.ui.UIPlayerHealthBar;
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
    
    private static final boolean DEBUG = false;
    
    private static final int SCREEN_W = 1600, SCREEN_H = 900;
    private static final boolean FULLSCREEN_FLAG = false;
    private static final int TARGET_FPS = 60;

    private GameState gs;
    
    private boolean gameIsPaused = false;
    
    private int numEnemies = 0;
    
    @Override
    public void init(GameContainer gc) throws SlickException {
        
        try {
            gs = new GameState(SCREEN_W, SCREEN_H, TARGET_FPS);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            gc.exit();
            return;
        }

        PlayerShip player1 = new PlayerShip(SCREEN_W/4,3*SCREEN_H/4, gs.getImage("ship") );
        player1.setKeys(Input.KEY_W, Input.KEY_S, Input.KEY_A, Input.KEY_D, Input.KEY_G);
        gs.queueNewActor(player1);
        
        PlayerShip player2 = new PlayerShip(3*SCREEN_W/4,3*SCREEN_H/4, gs.getImage("ship") );
        player2.setKeys(Input.KEY_UP, Input.KEY_DOWN, Input.KEY_LEFT, Input.KEY_RIGHT, Input.KEY_RCONTROL);
        gs.queueNewActor(player2);
        
        gs.queueNewActor(new UIPlayerHealthBar(player1, 100, SCREEN_H-20));
        gs.queueNewActor(new UIPlayerHealthBar(player2, SCREEN_W-200, SCREEN_H-20));
        
        gs.processActorQueues();
        
        gc.setAlwaysRender(true);
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        
        //Handles pausing the game when the window loses focus
        if(!DEBUG && !gc.hasFocus()) { 
            gs.pause(gs.getImage("pause"));
            gameIsPaused = true;
            return;
        } else if(gameIsPaused) { 
            gs.unpause(); 
            gameIsPaused = false;
        }
        
        if(gs.getEnemyList().isEmpty()) {
            if(numEnemies == 0) numEnemies = 1;
            else numEnemies *= 2;
            for(int i = 0; i < numEnemies; i++) {
                Enemy enemy = new Enemy((int)(Math.random()*SCREEN_W), -300, gs.getImage("imp-red"));
                gs.queueNewActor(enemy);
            }
        }
        
        Input input = gc.getInput();
        gs.setInput(input);
        
        if(!DEBUG) gs.setDeltaFromFps(gc.getFPS());
        else gs.setDeltaFromFps(TARGET_FPS);
        
        gs.updateActors();
        
        if(input.isKeyDown(Input.KEY_ESCAPE)) gc.exit();
    }

    @Override
    public void render(GameContainer gc, Graphics grphcs) throws SlickException {
        gs.renderActors();
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
