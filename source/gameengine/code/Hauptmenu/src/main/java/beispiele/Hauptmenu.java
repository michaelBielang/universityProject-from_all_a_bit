package beispiele;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisImageButton;
import com.kotcrab.vis.ui.widget.VisTextButton;

public class Hauptmenu extends ApplicationAdapter {
	private Stage stage;
	
	
	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new Hauptmenu(), config);
	}
	
	
	@Override
	public void create() {
		VisUI.load();
		
		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);
		
		stage.setDebugAll(true);
		
		createMenu();
	}
	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act();
		stage.draw();
	}
	
	
	private void createMenu() {
		Table table = new Table();
		table.setFillParent(true);
		
		table.add().expand().row();
		table.add(new VisTextButton("New Game", new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("new game");
			}
		})).row();
		table.add(new VisTextButton("Load", new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("load");
			}
		})).row();
		table.add(new VisTextButton("Exit", new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("exit");
			}
		})).row();
		
		VisImageButton options = new VisImageButton(new TextureRegionDrawable(new Texture(Gdx.files.classpath("beispiele/Options.png"))));
		options.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("options");
			}
		});
		
		table.add(options).expand().align(Align.bottomRight);
		
		stage.addActor(table);
	}
}
