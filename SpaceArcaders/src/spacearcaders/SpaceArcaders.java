package spacearcaders;

import java.util.LinkedList;
import java.util.List;
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
    
    private static final int SCREEN_X = 1024, SCREEN_Y = 768;
    private static final boolean FULLSCREEN_FLAG = false;
    
    private PlayerShip player1, player2;
    
    private List<Actor> actorList;
    
    @Override
    public void init(GameContainer gc) throws SlickException {
        
        player1 = new PlayerShip(SCREEN_X/2,SCREEN_Y/2,"data/proto-ship.PNG");
        player1.setKeys(Input.KEY_UP, Input.KEY_DOWN, Input.KEY_LEFT, Input.KEY_RIGHT);
        
        player2 = new PlayerShip(SCREEN_X/2 + 100,SCREEN_Y/2,"data/proto-ship.PNG");
        player2.setKeys(Input.KEY_W, Input.KEY_S, Input.KEY_A, Input.KEY_D);
        
        actorList = new LinkedList<>();
        
        actorList.add(player1);
        actorList.add(player2);
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        Input input = gc.getInput();
        
        for(Actor curActor : actorList) {
            curActor.takeInput(input, delta);
        }
                
        if(input.isKeyDown(Input.KEY_ESCAPE)) gc.exit();
    }

    @Override
    public void render(GameContainer gc, Graphics grphcs) throws SlickException {
        for (Actor curActor : actorList) {
            curActor.draw();
        }
    }
        
    public static void main(String[] args) {
        try {
            AppGameContainer app = new AppGameContainer(new SpaceArcaders());
            app.setDisplayMode(SCREEN_X, SCREEN_Y, FULLSCREEN_FLAG);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
    
    public SpaceArcaders() {
        super("SpaceArcaders");
    }
}
