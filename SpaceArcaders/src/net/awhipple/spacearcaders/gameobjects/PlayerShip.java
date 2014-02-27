package net.awhipple.spacearcaders.gameobjects;

import net.awhipple.spacearcaders.graphics.Particle;
import net.awhipple.spacearcaders.graphics.Spark;
import net.awhipple.spacearcaders.views.GameField;
import net.awhipple.spacearcaders.utils.HitBox;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Nate
 */
public class PlayerShip implements Actor, Target {

    private double shipLocationX, shipLocationY;
    private Image playerShipIcon;
    private HitBox hitBox;
    private double jetParticleTimer;
    private double shipSpeed;
    private double shipHealth;
    private double shotsPerSecond;
    private double fireSpeed;
    
    private int moveKeyUp;
    private int moveKeyDown;
    private int moveKeyLeft;
    private int moveKeyRight;
    private int laserShootKey;
    
    private double maxUp;
    private double maxDown;
    private double maxLeft;
    private double maxRight;
    
    private boolean altFire;
    private boolean dead;
    private double explodeTime;
    private double nextExplosion;
    
    private void setDefaults() {
        shipSpeed = 400;
    }

    public PlayerShip(double x, double y, Image shipImage) throws SlickException {
        setDefaults();
        
        jetParticleTimer = .03;

        shipLocationX = x;
        shipLocationY = y;
        fireSpeed = 0d;
        shotsPerSecond = 3d;
        altFire = false;
        shipHealth = 100d;
       
        playerShipIcon = shipImage;
        hitBox = new HitBox(shipImage.getWidth()/4);
        
        dead = false;
    }

    @Override
    public void init(GameField gf) throws SlickException {
    
       maxUp = (playerShipIcon.getHeight()/2);
       maxDown = (gf.getGlobals().getScreenHeight() - (playerShipIcon.getHeight()/2));
       maxLeft = (playerShipIcon.getWidth()/2);
       maxRight = (gf.getGlobals().getScreenWidth()- (playerShipIcon.getWidth()/2));
        
    }
    
    @Override
    public void draw() {
        playerShipIcon.draw((int) (shipLocationX - playerShipIcon.getWidth() / 2),
                (int) (shipLocationY - playerShipIcon.getHeight() / 2));
    }

    private void moveUp(double delta) {
        if (shipLocationY < maxUp)
            return;
        shipLocationY -= shipSpeed * delta;
    }

    private void moveDown(double delta) {
        if (shipLocationY > maxDown)
            return;
        shipLocationY += shipSpeed * delta;
    }

    private void moveLeft(double delta) {
        if (shipLocationX < maxLeft)
            return;
        shipLocationX -= shipSpeed * delta;
    }

    private void moveRight(double delta) {
        if (shipLocationX > maxRight)
            return;
        shipLocationX += shipSpeed * delta;

    }
    
    private void shootLaser(GameField gf) {
        double laserSpeed = 800d;
        if(!altFire) gf.queueNewActor(new Laser(shipLocationX-20, shipLocationY-50, laserSpeed, "enemy", gf.getGlobals().getImage("laser")));
        else         gf.queueNewActor(new Laser(shipLocationX+20, shipLocationY-50, laserSpeed, "enemy", gf.getGlobals().getImage("laser")));
        altFire = !altFire;
        gf.getGlobals().playSound("laser");
    }

    public void setKeys(int KEY_UP, int KEY_DOWN, int KEY_LEFT, int KEY_RIGHT, int KEY_SHOOT) {
        moveKeyUp = KEY_UP;
        moveKeyDown = KEY_DOWN;
        moveKeyLeft = KEY_LEFT;
        moveKeyRight = KEY_RIGHT;
        laserShootKey = KEY_SHOOT;   
    }
    
    
    @Override
    public void update(GameField gf) {
        double delta = gf.getDelta();
        
        if(dead) {
            explodeTime -= delta;
            nextExplosion -= delta;
            if(nextExplosion <= 0) {
                double xLoc = shipLocationX + Math.random()*playerShipIcon.getWidth()-playerShipIcon.getWidth()/2;
                double yLoc = shipLocationY + Math.random()*playerShipIcon.getHeight()-playerShipIcon.getHeight()/2;
                Spark.createPixelShower(gf, xLoc, yLoc, 100);
                gf.getGlobals().playSound("explode");
                nextExplosion += 0.1;
            }
            if(explodeTime <= 0) {
                gf.queueRemoveActor(this);
                Spark.createPixelShower(gf, shipLocationX, shipLocationY, 200);
            }
            return;
        }
        
        Input input = gf.getInput();
        
        if(!input.isKeyDown(laserShootKey))
            fireSpeed=0;
        
        if(input.isKeyDown(laserShootKey)){
            if(fireSpeed <= 0) {
                shootLaser(gf);
                fireSpeed += (1/shotsPerSecond);
                }
            
            fireSpeed -= delta;
        }
        
        jetParticleTimer -= delta;
        if(jetParticleTimer <= 0) {
            jetParticleTimer += .03;
            gf.queueNewActor(new Particle(shipLocationX-38, shipLocationY+55, 30, 30, 255, 128, 0, 128,
                                          shipLocationX-38, shipLocationY+75,  5,  5, 255,   0, 0, 75,
                                          .5,gf.getGlobals().getImage("particle")));
            gf.queueNewActor(new Particle(shipLocationX+38, shipLocationY+55, 30, 30, 255, 128, 0, 128,
                                          shipLocationX+38, shipLocationY+75,  5,  5, 255,   0, 0, 75,
                                          .5,gf.getGlobals().getImage("particle")));
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
    
    @Override
    public void dealDamage(double dmg) {
        shipHealth -= dmg;
        if(shipHealth <= 0) {
            dead = true;
            hitBox = new HitBox(-1);
            explodeTime = 1;
            nextExplosion = .1d;
        }
    }
    
    public double getHealth() { return shipHealth; }
    @Override
    public HitBox getHitBox() { return hitBox; }
    @Override
    public double getX() { return shipLocationX; }
    @Override
    public double getY() { return shipLocationY; }
}
