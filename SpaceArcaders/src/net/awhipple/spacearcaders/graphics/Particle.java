/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.graphics;

import net.awhipple.spacearcaders.gameobjects.Actor;
import net.awhipple.spacearcaders.utils.GameMath;
import net.awhipple.spacearcaders.utils.GameState;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

/**
 *
 * @author Aaron
 */
public class Particle implements Actor {

    private Image image;
    private double x, y, w, h, r, g, b, a,
                   xv, yv, wv, hv, rv, gv, bv, av, 
                   time;
    
    public Particle(double x1, double y1, double w1, double h1, double r1, double g1, double b1, double a1,
                    double x2, double y2, double w2, double h2, double r2, double g2, double b2, double a2,
                    double time, Image particleImage) {
        this.x = x1;
        this.y = y1;
        this.w = w1;
        this.h = h1;
        this.r = r1;
        this.g = g1;
        this.b = b1;
        this.a = a1;
        
        if(time == 0) time = 0.0001d;
        
        this.xv = GameMath.transitionSpeed(x1, x2, time);
        this.yv = GameMath.transitionSpeed(y1, y2, time);
        this.wv = GameMath.transitionSpeed(w1, w2, time);
        this.hv = GameMath.transitionSpeed(h1, h2, time);
        this.rv = GameMath.transitionSpeed(r1, r2, time);
        this.gv = GameMath.transitionSpeed(g1, g2, time);
        this.bv = GameMath.transitionSpeed(b1, b2, time);
        this.av = GameMath.transitionSpeed(a1, a2, time);

        this.time = time;
        
        image = particleImage;
    }
    
    @Override
    public void draw() {
        image.draw((int)(x-w/2),(int)(y-h/2),(int)w,(int)h, new Color((int)r,(int)g,(int)b,(int)a));
    }

    @Override
    public void update(GameState gs) {
        double delta = gs.getDelta();
        
        x += xv * delta;
        y += yv * delta;
        w += wv * delta;
        h += hv * delta;
        r += rv * delta;
        g += gv * delta;
        b += bv * delta;
        a += av * delta;
        
        time -= delta;
        if(time <= 0) gs.queueRemoveActor(this);
    }
    
    public static void createExplosion(GameState gs, double x, double y, int numParticles, double explosionSize) {
        for(int a = 0; a < numParticles; a++) {
            double rad = Math.random()*2*Math.PI,
                   distance = Math.random()*explosionSize,
                   w = 20,
                   h = 20;
            Particle p = new Particle(x, y, w, h, 163, 163, 220, 255, 
                             x+Math.cos(rad)*distance, y+Math.sin(rad)*distance, w, h, 240, 0, 0, 0,
                             .3, gs.getImage("particle"));
            gs.queueNewActor(p);
        }
    }
}
