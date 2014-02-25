/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.views;

/**
 *
 * @author Aaron
 */
public class ViewInstruction {
    public enum Set {
        SWITCH_VIEW
    }
    
    Set instruction;
    Object contents;
    
    public ViewInstruction(Set instruction, Object contents) {
        this.instruction = instruction;
        this.contents = contents;
    }
    
    public Set getInstruction() { return instruction; }
    
    public Object getContents() { return contents; }
}
