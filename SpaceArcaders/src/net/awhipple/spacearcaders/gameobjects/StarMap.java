/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.gameobjects;

import java.util.LinkedList;
import java.util.Queue;
import net.awhipple.spacearcaders.views.GameField;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Aaron
 */
public class StarMap implements Actor{

    private Queue<Integer> starLoc;
    private Image starMap;
    private Graphics starMapGraphics;
    private Color backColor, starColor;
    private int mapWidth, mapHeight;
    private double scrollSpeed, curDelta;
    
    public StarMap() {
        scrollSpeed = (1d/20d);
        curDelta = 0;
        
        starLoc = new LinkedList<>();
        
        backColor = Color.black;
        starColor = Color.white;
    }
    
    @Override
    public void init(GameField gf) throws SlickException{
        init(gf.getScreenWidth(), gf.getScreenHeight());
    }
    
    public void init(int screenWidth, int screenHeight) throws SlickException {
        mapWidth = screenWidth;
        mapHeight = screenHeight;
        
        for(int y = 0; y < mapHeight; y++) {
            starLoc.add(new Integer((int)(Math.random() * mapWidth)));
        }
        
        starMap = new Image(mapWidth, mapHeight);
                
        starMapGraphics = starMap.getGraphics();
        starMapGraphics.setColor(backColor);
        starMapGraphics.fillRect(0, 0, starMap.getWidth(), starMap.getWidth());
        starMapGraphics.flush();
        
        generateStars(starColor);
        starMapGraphics.flush();
    }
    
    @Override
    public void draw() {
        starMap.draw(0,0);
    }

    public void update(double delta) {
        curDelta += delta;
        while (curDelta > scrollSpeed) {
            curDelta -= scrollSpeed;
            
            generateStars(backColor);
            starLoc.poll();
            starLoc.add(new Integer((int) (Math.random() * mapWidth)));
            generateStars(starColor);
            starMapGraphics.flush();
        }
    }
    
    @Override
    public void update(GameField gf) {
        update(gf.getDelta());
    }

    private void generateStars(Color color) {
        starMapGraphics.setColor(color);
        int y = mapHeight;
        for (Integer nextLoc : starLoc) {
            starMapGraphics.fillRect(nextLoc, y, 1, 1);
            y--;
        }
    }
}
