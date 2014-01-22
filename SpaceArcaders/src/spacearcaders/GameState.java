/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spacearcaders;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
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
    private List<Actor> actorsToBeAdded;
    
    private Image laserImage;
    
    public GameState() {
        playerList = new LinkedList<>();
        actorList = new LinkedList<>();
        actorsToBeAdded = new LinkedList<>();
        
        Color transColor = new Color(255, 0, 255, 255);
        try {
            laserImage = new Image("data/proto-laser.PNG", transColor);
        } catch (SlickException ex) {
            Logger.getLogger(GameState.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void createStarMap(int screenX, int screenY) throws SlickException {
        starMap = new StarMap(screenX, screenY);
        addActor(starMap);
    }
    
    public void addPlayer(PlayerShip ps) {
        playerList.add(ps);
        addActor(ps);
    }   

    void addLaser(Laser aLaser) {
       addActor(aLaser);
    }
    
    void addActor(Actor actor) {
        actorsToBeAdded.add(actor);
    }
    
    void addAllActors() {
        while(actorsToBeAdded.size() > 0)
            actorList.add(actorsToBeAdded.remove(0));
    }
    
    public void setInput(Input input) { this.input = input; }
    public Input getInput() { return input; }
    
    public List<Actor> getActorList() { return actorList; }
    
    public Image getLaserImage() {
        return laserImage;
    }

   
}
