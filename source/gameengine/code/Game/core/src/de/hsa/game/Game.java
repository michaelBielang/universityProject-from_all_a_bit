package de.hsa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.kotcrab.vis.ui.VisUI;

public class Game extends com.badlogic.gdx.Game {
	public static boolean DEBUG = false;
	

	@Override
	public void create () {
		VisUI.load();
		Box2D.init();
		
		setScreen(new MainMenu());
	}
	@Override
	public void render() {
		if(Gdx.input.isKeyJustPressed(Keys.F1))
			DEBUG = !DEBUG;
		
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		
		VisUI.dispose();
	}
}
