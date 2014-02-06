/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.utils;

/**
 *
 * @author Aaron
 */
public class GameMath {
    public static double pointsToRad(double x1, double y1, double x2, double y2) {
        double pi = Math.PI;
        if(x1==x2) {
            if(y1>y2) return (3*pi)/2d;
            else return pi/2d;
        }
        double angle = Math.atan((y2-y1)/(x2-x1));
        if(x2<x1) { return pi+angle; }
        else if(y2<y1) return 2*pi+angle;
        else return angle;
    }
    
    //returns -1 if p1 < p2, 0 if p1==p2 and 1 if p1 > p2
    public static int whichSide(double p1, double p2) {
        if(p1 < p2) return -1;
        else if (p1 > p2) return 1;
        else return 0;
    }
}
