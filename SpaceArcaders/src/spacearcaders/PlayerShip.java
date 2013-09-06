package spacearcaders;

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

    private void setDefaults() {
        shipSpeed = 0.5f;
    }

    PlayerShip() throws SlickException {
        setDefaults();

        shipLocationX = 612f;
        shipLocationY = 550f;

        Color transColor = new Color(255, 0, 255, 255);

        playerShipIcon = new Image("data/proto-ship.PNG", transColor);
    }

    PlayerShip(float x, float y, String fileName) throws SlickException {
        setDefaults();

        shipLocationX = x;
        shipLocationY = y;

        Color transColor = new Color(255, 0, 255, 255);

        playerShipIcon = new Image(fileName, transColor);
    }

    @Override
    public void draw() {
        playerShipIcon.draw((int) (shipLocationX - playerShipIcon.getWidth() / 2),
                (int) (shipLocationY - playerShipIcon.getHeight() / 2));
    }

    @Override
    public void update(float delta) {
    }

    public void moveUp(int delta) {
        shipLocationY -= shipSpeed * delta;
    }

    public void moveDown(int delta) {
        shipLocationY += shipSpeed * delta;
    }

    public void moveLeft(int delta) {
        shipLocationX -= shipSpeed * delta;
    }

    public void moveRight(int delta) {
        shipLocationX += shipSpeed * delta;

    }

    public void setKeys(int KEY_UP, int KEY_DOWN, int KEY_LEFT, int KEY_RIGHT) {
        moveKeyUp = KEY_UP;
        moveKeyDown = KEY_DOWN;
        moveKeyLeft = KEY_LEFT;
        moveKeyRight = KEY_RIGHT;
    }

    public void takeInput(Input input, int delta) {
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
