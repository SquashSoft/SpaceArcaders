package net.awhipple.spacearcaders;

import com.badlogic.gdx.backends.jglfw.JglfwApplication;

public class DesktopStarter {
	public static void main(String[] args) {
		new JglfwApplication(new SpaceArcaders(), 
							"SpaceArcaders",
							800, 600, false);
	}
}
