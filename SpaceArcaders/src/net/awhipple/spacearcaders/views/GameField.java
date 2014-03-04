/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.views;

import java.util.Collections;
import java.util.HashMap;
import net.awhipple.spacearcaders.gameobjects.Actor;
import net.awhipple.spacearcaders.gameobjects.StarMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import net.awhipple.spacearcaders.gameobjects.Enemy;
import net.awhipple.spacearcaders.gameobjects.PlayerShip;
import net.awhipple.spacearcaders.gameobjects.Target;
import net.awhipple.spacearcaders.ui.UIPlayerHealthBar;
import net.awhipple.spacearcaders.utils.GameGlobals;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Aaron
 */
public final class GameField extends View{
    private Input input;
    private double delta;
    
    private List<Actor> actorList;
    private List<Actor> actorsToBeAdded;
    private List<Actor> actorsToBeRemoved;
    private List<Enemy> enemyList;
    private List<PlayerShip> playerShipList;
    private Map<String, List<Target>> targetMap;
    
    private Image pixel;
    
    int numPlayers;
    int numEnemies = 0;
    double gameOverTimer = 3;
    
    public GameField(int numPlayers) throws SlickException {
        this.numPlayers = numPlayers;
        
        actorList = new LinkedList<>();
        enemyList = new LinkedList<>();
        playerShipList = new LinkedList<>();
        targetMap = new HashMap<>();
        
        actorsToBeAdded = new LinkedList<>();
        actorsToBeRemoved = new LinkedList<>();
        
        pixel = new Image(1, 1);
        Graphics gfx = pixel.getGraphics();
        gfx.setColor(Color.white);
        gfx.drawRect(0, 0, 1, 1);
        gfx.flush();
    }
    
    @Override
    public void setGlobals(GameGlobals globals) {
        super.setGlobals(globals);
        
        delta = globals.getDelta();
        
        queueNewActor(globals.getStarMap());
        
        try {
            int SCREEN_W = globals.getScreenWidth(), SCREEN_H = globals.getScreenHeight();
            if(numPlayers == 1) {
                PlayerShip player1 = new PlayerShip(SCREEN_W/2,3*SCREEN_H/4, globals.getImage("ship") );
                player1.setKeys(Input.KEY_W, Input.KEY_S, Input.KEY_A, Input.KEY_D, Input.KEY_RCONTROL);
                queueNewActor(player1);

                queueNewActor(new UIPlayerHealthBar(player1, 100, SCREEN_H-20));
            } else {
                PlayerShip player1 = new PlayerShip(SCREEN_W/4,3*SCREEN_H/4, globals.getImage("ship") );
                player1.setKeys(Input.KEY_W, Input.KEY_S, Input.KEY_A, Input.KEY_D, Input.KEY_G);
                queueNewActor(player1);

                PlayerShip player2 = new PlayerShip(3*SCREEN_W/4,3*SCREEN_H/4, globals.getImage("ship") );
                player2.setKeys(Input.KEY_UP, Input.KEY_DOWN, Input.KEY_LEFT, Input.KEY_RIGHT, Input.KEY_RCONTROL);
                queueNewActor(player2);

                queueNewActor(new UIPlayerHealthBar(player1, 100, SCREEN_H-20));
                queueNewActor(new UIPlayerHealthBar(player2, SCREEN_W-200, SCREEN_H-20));
            }
        } catch(SlickException ex) {
            System.out.println("Error while initializing the Game Field");
        }
        
        processActorQueues();
    }

    @Override
    public List<ViewInstruction> update(Input input) {
        if(enemyList.isEmpty()) {
            numEnemies++;
            for(int i = 0; i < numEnemies; i++) {
                Enemy enemy = new Enemy((int)(Math.random()*globals.getScreenWidth()), -300, globals.getImage("imp-red", true));
                queueNewActor(enemy);
            }
        }
        
        if(playerShipList.isEmpty()) {
            gameOverTimer -= delta;
            if(gameOverTimer <= 0) {
                try {
                    return Collections.singletonList(new ViewInstruction(
                        ViewInstruction.Set.SWITCH_VIEW,
                        new GameField(numPlayers)));
                } catch(SlickException ex) {
                    System.out.println("Unable to create new game field after player death");
                }
            }
        }

        setInput(input);
        
        updateActors();
        
        if(input.isKeyDown(Input.KEY_P)) {
            return Collections.singletonList(new ViewInstruction(
                    ViewInstruction.Set.SWITCH_VIEW,
                    new Pause(this)));
        }
        return null;
    }
    
    public void updateActors() {
        for(Actor curActor : actorList) {
            curActor.update(this);
        }
        processActorQueues();
    }
    
    @Override
    public void render() {
        for (Actor curActor : actorList) {
            curActor.draw();
        }
    }
    
    public void queueNewActor(Actor actor) {
        actorsToBeAdded.add(actor);
    }
    
    public void queueRemoveActor(Actor actor) {
        actorsToBeRemoved.add(actor);
    }
    
    public final void processActorQueues() {
        while(actorsToBeAdded.size() > 0) {
            Actor actor = actorsToBeAdded.remove(0);
            try {
                actor.init(this);
            } catch (SlickException ex) {
                System.out.println("WARNING! Unable to init actor!!! " + actor);
                System.out.println(ex.getMessage());
            }
            actorList.add(actor);
            if(actor instanceof Enemy){
                enemyList.add((Enemy) actor);
                addToTargetList((Target)actor, "enemy");
            }
            if(actor instanceof PlayerShip){ 
                playerShipList.add((PlayerShip) actor);
                addToTargetList((Target)actor, "player");
            }
        }
        while(actorsToBeRemoved.size() > 0) {
            Actor actor = actorsToBeRemoved.remove(0);
            actorList.remove(actor);
            if(actor instanceof Enemy) {
                removeFromTargetMap((Target) actor);
                enemyList.remove((Enemy) actor);
            }
            
            if(actor instanceof PlayerShip) {
                removeFromTargetMap((Target) actor);
                playerShipList.remove((PlayerShip) actor);
            }
        }
    }

    public void setInput(Input input) { this.input = input; }
    public Input getInput() { return input; }

    public double getDelta() { return delta; }
    
    public Image getPixel() { return pixel; }
    
    public List<Enemy> getEnemyList() { return enemyList; }
    public List<PlayerShip> getPlayerShipList() { return playerShipList; }
    
    public List<Target> getTargetList(String targetType) {
        if(targetMap.containsKey(targetType))
            return targetMap.get(targetType);
        else
            return addTargetList(targetType); 
    }
    
    public void addToTargetList(Target targetToAdd, String targetType) {
            if(!targetMap.containsKey(targetType))
                addTargetList(targetType);
            targetMap.get(targetType).add(targetToAdd);
    }
    private void removeFromTargetMap(Target target) {
         for(String key: targetMap.keySet())
             targetMap.get(key).remove(target);
    }

    private List<Target> addTargetList(String targetType) {
        List<Target> targetList = new LinkedList<>();
        targetMap.put(targetType, targetList);
        return targetList;
    }
    
    public GameGlobals getGlobals() { return globals; }
}
