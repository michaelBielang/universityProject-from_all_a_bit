package de.hsa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;

import de.hsa.game.utils.GdxUtils;

public class ScoreScreen extends ScreenAdapter {
	private int deaths;
	
	private Stage stage;
	
	
	public ScoreScreen(int deaths) {
		this.deaths = deaths;
	}
	
	
	@Override
	public void show() {
		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);
		
		VisTable table = new VisTable();{
			table.align(Align.bottomLeft);
			table.setFillParent(true);
			
			VisLabel label = new VisLabel("Total Deaths: "+deaths);
			table.add(label).row();
			VisTextButton start = new VisTextButton("back to main menu", new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
				}
			});
			start.align(Align.center);
			
			table.add(start).expand();
		}stage.addActor(table);
		
		if(Game.DEBUG)
			stage.setDebugAll(true);
	}
	@Override
	public void render(float delta) {
		if(Gdx.input.isKeyJustPressed(Keys.F1))
			stage.setDebugAll(Game.DEBUG);
		
		GdxUtils.clearScreen(Color.BLACK);
		
		stage.act(delta);
		stage.draw();
	}
	@Override
	public void resize(int width, int height) {
		stage.getViewport().setScreenSize(width, height);
	}
}
