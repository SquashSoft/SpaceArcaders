package net.awhipple.spacearcaders.gameobjects;

import net.awhipple.spacearcaders.utils.GameState;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Nate
 */
public class PlayerShip implements Actor {

    private float shipLocationX, shipLocationY;
    private Image playerShipIcon;
    private float shipSpeed;
    
    private int moveKeyUp;
    private int moveKeyDown;
    private int moveKeyLeft;
    private int moveKeyRight;
    private int laserShootKey;
    private int fireSpeed;
    
    private void setDefaults() {
        shipSpeed = 400;
    }

    public PlayerShip(Image shipImage) throws SlickException {
        setDefaults();

        shipLocationX = 612f;
        shipLocationY = 550f;

        playerShipIcon = shipImage;
    }

    public PlayerShip(float x, float y, Image shipImage) throws SlickException {
        setDefaults();

        shipLocationX = x;
        shipLocationY = y;

        Color transColor = new Color(255, 0, 255, 255);

        playerShipIcon = shipImage;
    }

    @Override
    public void draw() {
        playerShipIcon.draw((int) (shipLocationX - playerShipIcon.getWidth() / 2),
                (int) (shipLocationY - playerShipIcon.getHeight() / 2));
    }

    private void moveUp(float delta) {
        shipLocationY -= shipSpeed * delta;
    }

    private void moveDown(float delta) {
        shipLocationY += shipSpeed * delta;
    }

    private void moveLeft(float delta) {
        shipLocationX -= shipSpeed * delta;
    }

    private void moveRight(float delta) {
        shipLocationX += shipSpeed * delta;

    }
    
    private void shootLaser(GameState gs) {
        Laser aLaser = new Laser(shipLocationX, shipLocationY, gs.getLibraryImage("laser"));
        gs.addLaser(aLaser);
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
        float delta = (1f/60f);
        Input input = gs.getInput();
    
        if(!input.isKeyDown(laserShootKey))
            fireSpeed=0;
        
        if(input.isKeyDown(laserShootKey)){
            if(fireSpeed == 0) {
                shootLaser(gs);
                ++fireSpeed;
                }
            
            if(fireSpeed == 25)
                fireSpeed = 0;
         
            else {
                ++fireSpeed;
            }
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
}
