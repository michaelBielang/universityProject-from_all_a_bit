package de.hsa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.file.FileChooser;
import com.kotcrab.vis.ui.widget.file.FileChooser.Mode;
import com.kotcrab.vis.ui.widget.file.FileChooser.SelectionMode;
import com.kotcrab.vis.ui.widget.file.FileChooserListener;
import com.kotcrab.vis.ui.widget.file.FileTypeFilter;

public class MainMenu extends ScreenAdapter {
	private Stage stage;
	
	private FileChooser fileChooser;
	
	
	@Override
	public void show() {
		stage = new Stage();
		
		fileChooser = new FileChooser(Mode.OPEN);
		fileChooser.setHeight(300);
		
		VisTable table = new VisTable();{
			table.align(Align.bottomLeft);
			table.setFillParent(true);
			
			VisTextButton start = new VisTextButton("Start", new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					((Game) Gdx.app.getApplicationListener()).setScreen(new InGame());
				}
			});
			start.align(Align.center);
			
			table.add().expand().row();
			table.add(start).row();
			
			VisTextButton load = new VisTextButton("Load", new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					fileChooser.setSelectionMode(SelectionMode.FILES);
					fileChooser.setMultiSelectionEnabled(false);
					fileChooser.setDirectory(Gdx.files.local(""));
					
					FileTypeFilter xmlFilter = new FileTypeFilter(false);
					xmlFilter.addRule("XML", "xml");
					
					fileChooser.setFileTypeFilter(xmlFilter);
					
					fileChooser.setListener(new FileChooserListener() {
						@Override
						public void selected(Array<FileHandle> files) {
							if(files.size < 1)
								return;
							
							((Game) Gdx.app.getApplicationListener()).setScreen(new InGame(files.get(0)));
						}
						
						@Override
						public void canceled() {}
					});
					
					stage.addActor(fileChooser.fadeIn());
				}
			});
			load.align(Align.center);
			
			table.add(load).row();
			table.add().expand();
		}stage.addActor(table);
		
		if(Game.DEBUG)
			stage.setDebugAll(true);
		
		Gdx.input.setInputProcessor(stage);
	}
	
	
	@Override
	public void render(float delta) {
		if(Gdx.input.isKeyJustPressed(Keys.F1))
			stage.setDebugAll(Game.DEBUG);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
	}
	@Override
	public void resize(int width, int height) {
		stage.getViewport().setScreenSize(width, height);
	}
}
