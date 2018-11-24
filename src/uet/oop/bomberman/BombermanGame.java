package uet.oop.bomberman;

import uet.oop.bomberman.gui.Frame;
import uet.oop.bomberman.sound.Sound;

import javax.sound.sampled.*;
import java.io.File;
import java.net.URL;

public class BombermanGame {
	
	public static void main(String[] args) {

		Sound.play("background");
		new Frame();
	}
}
