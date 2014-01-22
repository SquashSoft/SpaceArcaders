/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spacearcaders;

import java.util.LinkedList;
import java.util.List;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Aaron
 */
public class GameState {
    Input input;
    
    private StarMap starMap;
    
    private List<PlayerShip> playerList;
    private List<Actor> actorList;
    
    public GameState() {
        playerList = new LinkedList<>();
        actorList = new LinkedList<>();
    }
    
    public void createStarMap(int screenX, int screenY) throws SlickException {
        starMap = new StarMap(screenX, screenY);
        actorList.add(starMap);
    }
    
    public void addPlayer(PlayerShip ps) {
        playerList.add(ps);
        actorList.add(ps);
    }   

    public void setInput(Input input) { this.input = input; }
    public Input getInput() { return input; }
    
    public List<Actor> getActorList() { return actorList; }
}
