package net.awhipple.spacearcaders.gameobjects;

import net.awhipple.spacearcaders.utils.GameState;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Nate
 */
public class PlayerShip implements Actor {

    private double shipLocationX, shipLocationY;
    private Image playerShipIcon;
    private double shipSpeed;
    private double shipHealth;
    private double shotsPerSecond;
    private double fireSpeed;
    
    private int moveKeyUp;
    private int moveKeyDown;
    private int moveKeyLeft;
    private int moveKeyRight;
    private int laserShootKey;
    
    private void setDefaults() {
        shipSpeed = 400;
    }

    public PlayerShip(double x, double y, Image shipImage) throws SlickException {
        setDefaults();

        shipLocationX = x;
        shipLocationY = y;
        fireSpeed = 0d;
        shotsPerSecond = 3d;
        shipHealth = 100d;
       
        playerShipIcon = shipImage;
    }

    @Override
    public void draw() {
        playerShipIcon.draw((int) (shipLocationX - playerShipIcon.getWidth() / 2),
                (int) (shipLocationY - playerShipIcon.getHeight() / 2));
    }

    private void moveUp(double delta) {
        shipLocationY -= shipSpeed * delta;
    }

    private void moveDown(double delta) {
        shipLocationY += shipSpeed * delta;
    }

    private void moveLeft(double delta) {
        shipLocationX -= shipSpeed * delta;
    }

    private void moveRight(double delta) {
        shipLocationX += shipSpeed * delta;

    }
    
    private void shootLaser(GameState gs) {
        Laser aLaser = new Laser(shipLocationX, shipLocationY, gs.getImage("laser"));
        gs.queueNewActor(aLaser);
        gs.playSound("laser");
        shipHealth -= 1d;
    }

    public void setKeys(int KEY_UP, int KEY_DOWN, int KEY_LEFT, int KEY_RIGHT, int KEY_SHOOT) {
        moveKeyUp = KEY_UP;
        moveKeyDown = KEY_DOWN;
        moveKeyLeft = KEY_LEFT;
        moveKeyRight = KEY_RIGHT;
        laserShootKey = KEY_SHOOT;   
    }
    
    
    @Override
    public void update(GameState gs) {
        double delta = gs.getDelta();
        Input input = gs.getInput();
    
        
        if(!input.isKeyDown(laserShootKey))
            fireSpeed=0;
        
        if(input.isKeyDown(laserShootKey)){
            if(fireSpeed <= 0) {
                shootLaser(gs);
                fireSpeed += (1/shotsPerSecond);
                }
            
            fireSpeed -= delta;
        }
        
        if (input.isKeyDown(moveKeyUp)) {
            moveUp(delta);
        }

        if (input.isKeyDown(moveKeyDown)) {
            moveDown(delta);
        }

        if (input.isKeyDown(moveKeyLeft)) {
            moveLeft(delta);
        }

        if (input.isKeyDown(moveKeyRight)) {
            moveRight(delta);
        }
    }
    
    public double getHealth() { return shipHealth; }
}
