/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.graphics;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import net.awhipple.spacearcaders.gameobjects.Actor;
import net.awhipple.spacearcaders.utils.GameMath;
import net.awhipple.spacearcaders.utils.GameState;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Aaron
 */
public class Spark implements Actor{

    private static List<Color> gradient;
    
    private List<SparkPixel> pixelList;
    private Image pixel;
    private double x, y, rad, xv, yv, speed, time;
    private int xOld, yOld;
    private int numPixels;
    
    public Spark(double x, double y, double rad, double speed, Image pixel) {
        if(gradient == null) populateGradient();
        
        this.pixelList = new LinkedList<>();
        this.pixel = pixel;
        this.x = x;
        this.y = y;
        this.xOld = (int) x;
        this.yOld = (int) y;
        this.rad = rad;
        this.xv = Math.cos(rad);
        this.yv = Math.sin(rad);
        this.speed = speed;
        this.time = .2;
        this.numPixels = 20;
    }
    
    @Override
    public void init(GameState gs) throws SlickException {}
    
    @Override
    public void update(GameState gs) {
        double delta = gs.getDelta();
        
        time -= delta;
        for (double speedCount = speed * delta; speedCount >= 0; speedCount--) {
            x += xv;
            y += yv;
            if ((int) x != xOld || (int) y != yOld) {
                if(time > 0) pixelList.add(new SparkPixel(xOld, yOld));
                else numPixels--;
                xOld = (int) x;
                yOld = (int) y;
            }
        }

        if(numPixels < 0) numPixels = 0;
        for(int i = 0; i < pixelList.size() - numPixels; i++) {
            pixelList.remove(0);
        }
        
        if(pixelList.isEmpty()) gs.queueRemoveActor(this);
    }
    
    @Override
    public void draw() {
        double i = pixelList.size()-1;
        for(SparkPixel pix : pixelList) {
            int gradientNum = pixelList.size() != 1 ? 
                    (int)((i/(pixelList.size()-1))*(gradient.size()-1)) : 0;
            pixel.draw(pix.x, pix.y, gradient.get(gradientNum));
            i--;
        }
    }

    private class SparkPixel {
        private int x, y;        
        public SparkPixel(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    public static void createPixelShower(GameState gs, double x, double y, int numSparks) {
        createPixelShower(gs, x, y, numSparks, 700d);
    }
    public static void createPixelShower(GameState gs, double x, double y, int numSparks, double sparkSpeed){
     
        for(int i = 0; i < numSparks; i++) {
            gs.queueNewActor(new Spark(x, y, Math.random()*2*Math.PI, Math.random()*sparkSpeed+(sparkSpeed/2), gs.getPixel()));
        }
        
    }
    private static void populateGradient() {
        gradient = new ArrayList();
        for(double i = 0; i <= 1; i+=.01) {
            gradient.add(new Color((int)GameMath.transitionPercent(183, 244, i),
                                   (int)GameMath.transitionPercent(183, 250, i),
                                   (int)GameMath.transitionPercent(255, 88, i),
                                   (int)GameMath.transitionPercent(200, 175, i)));
        }
        for(double i = 0; i <= 1; i+=.01) {
            gradient.add(new Color((int)GameMath.transitionPercent(244, 255, i),
                                   (int)GameMath.transitionPercent(250, 0, i),
                                   (int)GameMath.transitionPercent(88, 0, i),
                                   (int)GameMath.transitionPercent(175, 128, i)));
        }
    }
}
