/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.awhipple.spacearcaders.views;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author Aaron
 */
public class ViewInstruction {
    public enum Set {
        SWITCH_VIEW
    }
    
    Set instruction;
    List<Object> contents;
    
    public ViewInstruction(Set instruction, Object content) {
        this(instruction, Collections.singletonList(content));
    }
    
    public ViewInstruction(Set instruction, List<Object> contents) {
        this.instruction = instruction;
        this.contents = contents;
    }
    
    public Set getInstruction() { return instruction; }
    
    public Object getContents() { 
        if(contents.isEmpty()) return null;
        return contents.get(0);
    }
    
    public List<Object> getContentList() { return contents; }
}
